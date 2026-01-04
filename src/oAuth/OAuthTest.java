package oAuth;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OAuthTest {
	String access_token;

	@Test
	public void getAccessToken() {

		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		
//		Course code Type
		
//		String response = given()
//				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
//				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
//				.formParams("scope", "trust").when()
//				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		access_token = jsonPath.getString("access_token");
		System.out.println("Access token : " + access_token);

	}

	@Test(dependsOnMethods = "getAccessToken")
	public void getCourseDetails() {

		RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/getCourseDetails";
		given().queryParams("access_token", access_token).when().get().then().log().all().assertThat().statusCode(401);
	}

}
