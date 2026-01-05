package oAuth;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTestWithPojo {
	String access_token;

	ArrayList<String> courseTitles = new ArrayList<>(Arrays.asList("Selenium Webdriver Java", "Cypress", "Protractorm"));

	@Test
	public void getAccessToken() {

		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		access_token = jsonPath.getString("access_token");
		System.out.println("Access token : " + access_token);

	}

	@Test(dependsOnMethods = "getAccessToken")
	public void getCourseDetails() {

		GetCourse getCourse = given().queryParams("access_token", access_token).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

		System.out.println("Instructor Name : " + getCourse.getInstructor());

		// Get Api course name
		System.out.println("API Course name : " + getCourse.getCourses().getApi().get(1).getCourseTitle());

		List<Api> apiCousres = getCourse.getCourses().getApi();
		for (int i = 0; i < apiCousres.size(); i++) {
			if (apiCousres.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println("SoapUI Webservices testing price is : " + apiCousres.get(i).getPrice());
			}
		}
//		Print all the course title of web automation 
		List<WebAutomation> webAutomation = getCourse.getCourses().getWebAutomation();
		System.out.println("All the course title of web automation");
		for (int i = 0; i < webAutomation.size(); i++) {
			System.out.println(webAutomation.get(i).getCourseTitle());

		}
		ArrayList<String> arrayList = new ArrayList<String>();
		for (int j = 0; j < webAutomation.size(); j++) {
			arrayList.add(webAutomation.get(j).getCourseTitle());
		}
		Assert.assertEquals(arrayList, courseTitles, "Expected not match with Actual");
			

	}

}
