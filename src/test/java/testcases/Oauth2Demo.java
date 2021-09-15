package testcases;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class Oauth2Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g6hDrXMX8N5JYff-XGoTawgWvTsMsghaNV34j5f9nym3NiSKuku2824xoa8LBrLUQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=1&prompt=none#";
		
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
		
		
		
		String r2 = given().queryParam("access_token", access_token).log().all()
		.when().post("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(r2);
		
	}

}
