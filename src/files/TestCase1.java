package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class TestCase1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Given - all input details
		// When - Submit the API
		// Then - Validate the response

		// Add place -> Update Place with new address -> Get Place to validate if new
		// address is present in response

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().extract().response().asString();
		System.out.println("Response :- " + response);

		String placeId = ReUsableMethods.rawToJson(response).getString("place_id");
		String address = "ImpactQA Noida";

		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.UpdatePlaceAddress(placeId, address)).when().put("maps/api/place/update/json").then()
				.log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

		String getPlaceResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeId).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		String actualAddress = ReUsableMethods.rawToJson(getPlaceResponse).getString("address");
		System.out.println("Actual Address :- " + actualAddress);

		Assert.assertEquals(actualAddress, address);

	}

}
