package files;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import googleAPIPojo.Location;
import googleAPIPojo.SetPlace;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest {

	SetPlace place = new SetPlace();

	@Test
	public void serializeTest() {

		place.setAccuracy(50);
		place.setName("Mandeep House");
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress("29, side layout, cohen 09");
		place.setWebsite("http://googleIn.com");
		place.setLanguage("Hindi");
		List<String> list = new ArrayList<String>();
		list.add("shoe park");
		list.add("shop");
		place.setTypes(list);
		Location location = new Location();
		location.setLat(-38.383494);
		location.setLng(33.427362);
		place.setLocation(location);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification resp = new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200)
				.build();

		RequestSpecification res = given().spec(req).body(place);

		String response = res.when().post("/maps/api/place/add/json").then().log().all().spec(resp).extract().response()
				.asString();
		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		System.out.println(jsonPath);

	}

}
