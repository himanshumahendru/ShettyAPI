package testcases;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;
import pojo.WebAutomation;

public class Deserialisation {
	
	
	public static void main(String[] args) {
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g4sd6jHw9xRpvu3jF2iaDSxvChAIQVire5Cd3OIPM1OuuwfBdt07l2iHOebGF-YUg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none#";
		
String code = url.split("code=")[1].split("&scope")[0];
		
		String getTokenResponse = given().urlEncodingEnabled(false).queryParams("code",code).
		queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code").log().all()
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().extract().response().asString();

		
		JsonPath js = new JsonPath(getTokenResponse);
		String access_token = js.getString("access_token");
		System.out.println(access_token);
		
		
		
		GetCourse gc  = given().queryParam("access_token", access_token).log().all()
		.expect().defaultParser(Parser.JSON)		
		.when().post("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gc.getInstructor());
		System.out.println(gc.getLinkedIn());
		
		System.out.println(gc.getCourses().getApi().get(0).getCourseTitle());
		
		
		// Storing Course titles of Web Automation in a List 
		
		List<String> titles = new ArrayList<String>();
		List<WebAutomation> w = gc.getCourses().getWebAutomation();
		
		for(int i =0;i<w.size();i++) {
			titles.add(w.get(i).getCourseTitle());
		}
		
		for(String list :titles)
			System.out.println(list);
			
	}

}
