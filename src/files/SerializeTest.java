package files;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import googleAPIPojo.Location;
import googleAPIPojo.SetPlace;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class SerializeTest {
	
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

		RestAssured.baseURI = "https://rahulshettyacademy.com";
	    String response = given().queryParam("key", "qaclick123").body(place)
	    		.when().post("/maps/api/place/add/json").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();
	    JsonPath jsonPath = ReUsableMethods.rawToJson(response);
	    System.out.println(jsonPath);
	    
	    
	}

}
