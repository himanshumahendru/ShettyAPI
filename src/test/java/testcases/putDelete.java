package testcases;

import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class putDelete {
	
	public static void main(String[] args) {
		
		
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		
		//Assigning value to session filter 
		String response = given().contentType(ContentType.JSON).body("{ \"username\": \"him.mahendru\", \"password\": \"himanshu@123\" }").log()
		.all().filter(session).when().post("/rest/auth/1/session").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		
		
		
		String addedComment = "Hey! its a custom comment 2 updated one";
		// Add comment 
		
		
		String addcommentResponse = given().pathParam("key", "10101").header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+addedComment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then()
		.assertThat().log().all().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addcommentResponse);
		String commentId = js.getString("id");
		
		
		
		// Add Attachment 
		
		given().relaxedHTTPSValidation().header("X-Atlassian-Token","nocheck").pathParam("key", "10101").log().all()
		.filter(session).multiPart("file", new File("text.txt")).header("Content-Type", "multipart/form-data")
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		// Get Issue Details
		
		String getDetailsResponse = given().filter(session).pathParam("key", "10101").queryParam("fields","comment").log().all()
		.when().get("/rest/api/2/issue/{key}").then()
		.assertThat().statusCode(200).log().all().extract().response().asString();
		
		System.out.println(getDetailsResponse);
		
		
		JsonPath js1 = new JsonPath(getDetailsResponse);
		
		int commentCount = js1.getInt("fields.comment.comments.size()");
		
		for(int i =0 ;i<commentCount;i++)
		{
			String cId = js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentId.equalsIgnoreCase(cId)) {
				
				String message = js1.getString("fields.comment.comments["+i+"].body");
				System.out.println(message);
				
			}
			
		}
	}

}
