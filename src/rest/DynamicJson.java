package rest;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";
		
		// Adding a book 
		String response = given().log().all().header("Content-Type", "application/json")
				.body(Payload.AddBook(isbn, aisle)).when().post("Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		String id = jsonPath.get("ID");
		System.out.println("Id :- " + id);
		
		// Getting book details
		String getDetailsResponse = given().log().all().queryParam("ID", id)
		.when().get("Library/GetBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("Get Details : " + getDetailsResponse);
		
		// Delete book
		given().log().all().header("Content-Type", "application/json")
		.body("{\r\n"
				+ " \r\n"
				+ "\"ID\" : \""+id+"\"\r\n"
				+ " \r\n"
				+ "}")
		.when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
		
	}
	
	@DataProvider(name = "BookData")
	public Object[][] getBookData() {
		return new Object[][] {{"heka", "3343"}, {"svgfd", "434"}, {"fvdrr", "3445"}};
	}

}
