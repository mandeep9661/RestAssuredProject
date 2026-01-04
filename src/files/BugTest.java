package files;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class BugTest {

	String id;

	@Test
	public void addIssueJira() {
		// Create issue with attachment
		RestAssured.baseURI = "https://mkc9661.atlassian.net";
		String response = given().header("Content-type", "application/json").header("Authorization",
				"Basic bWtjOTY2MUBnbWFpbC5jb206QVRBVFQzeEZmR0YwWlRhZ1VackJsQ0tKYjZqVFZWZUgxbmNCSTlsb3pCUDc2djk4OEZhTmR2M2VLVXZKb1YzRjViNC1yRkd0SXM1TDRXekl0VDd2cGNKYWRVZWNYcm1kWTIyTTBtQnZFTkl4M2RTMFZubFo1bHZoR092SnpBMWVCdHJORTdFUGFveVN3VHR4Rlg4RW5MdS1XaWg2dTNfN3NVTjYwM0g1MDZTWGpfaWJOSFpGeE9jPUIwMkQzRDMz")
				.body(Payload.AddIssue("Menu is not working - Automation")).when().post("rest/api/3/issue").then().log()
				.all().assertThat().statusCode(201).extract().response().asString();

		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		id = jsonPath.getString("id");
		System.out.println("Id :- " + id);

	}

	@Test(dependsOnMethods = "addIssueJira")
	public void addAttachment() {
		RestAssured.baseURI = "https://mkc9661.atlassian.net";
		given().pathParam("key", id).header("Authorization",
				"Basic bWtjOTY2MUBnbWFpbC5jb206QVRBVFQzeEZmR0YwWlRhZ1VackJsQ0tKYjZqVFZWZUgxbmNCSTlsb3pCUDc2djk4OEZhTmR2M2VLVXZKb1YzRjViNC1yRkd0SXM1TDRXekl0VDd2cGNKYWRVZWNYcm1kWTIyTTBtQnZFTkl4M2RTMFZubFo1bHZoR092SnpBMWVCdHJORTdFUGFveVN3VHR4Rlg4RW5MdS1XaWg2dTNfN3NVTjYwM0g1MDZTWGpfaWJOSFpGeE9jPUIwMkQzRDMz")
				.header("X-Atlassian-Token", "no-check")
				.multiPart("file",
						new File("C:\\Users\\mkc96\\eclipse-workspace\\RestAssuredProject\\src\\testData\\Image.png"))
				.when().post("rest/api/3/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
	}

}
