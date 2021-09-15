package testcases;

import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.GetPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

public class Serialisation {
	
public static void main(String[] args) {
	
	Location l = new Location(-38.383494, 33.427362);
	ArrayList<String> types = new ArrayList<String>();
	types.add("shoe park");
	types.add("shop");
	
	GetPlace p = new GetPlace(50,"Frontline house", "(+91) 983 893 3937" , "29, side layout, cohen 09", "https://rahulshettyacademy.com", "French-IN", l , types);
	
	
	System.out.println(p);
	RequestSpecification res = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).addQueryParam("key", "qaclick123").build();
	
	ResponseSpecification req = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();
	
	
  RequestSpecification res1 = given().log().all().spec(res).body(p);
  
  Response response = res1.when().post("/maps/api/place/add/json").then().spec(req).extract().response();
  
  System.out.println(response.asString());
}	
	
	
}
