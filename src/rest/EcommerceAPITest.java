package rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import ecomPojo.EcomLogin;
import ecomPojo.OrderDetails;
import ecomPojo.Orders;
import files.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.ProductResponse;

public class EcommerceAPITest {

	String token;
	String userId;
	String productId;

	@Test(description = "Login to application to get token")
	public void login() {

		EcomLogin ecomLogin = new EcomLogin();
		ecomLogin.setUserEmail("mkc9661@gmail.com");
		ecomLogin.setUserPassword("Maddy@123");

		RequestSpecification ress = given().spec(getRequestSpec()).body(ecomLogin);
		String response = ress.when().post("api/ecom/auth/login").then().log().all().spec(getResponseSpec(200))
				.extract().asString();

		JsonPath jsonPath = ReUsableMethods.rawToJson(response);
		token = jsonPath.getString("token");
		userId = jsonPath.getString("userId");
	}

	@Test(description = "Create a product to order", dependsOnMethods = "login")
	public void createProduct() {

		RequestSpecification requestAddProduct = given().spec(getRequestSpecWithToken()).param("productName", "Home")
				.param("productAddedBy", userId).param("productCategory", "Dream")
				.param("productSubCategory", "Building").param("productPrice", "1233222")
				.param("productDescription", "Home Originals").param("productFor", "women").multiPart("productImage",
						new File("C:\\Users\\mkc96\\eclipse-workspace\\RestAssuredProject\\src\\testData\\Home.png"));
		ProductResponse productResponse = requestAddProduct.when().post("api/ecom/product/add-product").then().log()
				.all().spec(getResponseSpec(201)).extract().response().as(ProductResponse.class);
		productId = productResponse.getProductId();
	}

	@Test(description = "Create order", dependsOnMethods = "createProduct")
	public void createOrder() {
		
		Orders order = new Orders();
		order.setCountry("India");
		order.setProductOrderedId(productId);
		List<Orders> listOrders = new ArrayList<>();
		listOrders.add(order);
		OrderDetails details = new OrderDetails();
		details.setOrders(listOrders);
		
		RequestSpecification requestOrderCreate = given().spec(getRequestSpecWithToken()).body(details);
		requestOrderCreate.when().post("api/ecom/order/create-order").then().log().all().spec(getResponseSpec(201));
		
		
	}

	public RequestSpecification getRequestSpec() {
		return new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
				.build();
	}

	public RequestSpecification getRequestSpecWithToken() {
		return new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.build();
	}

	public ResponseSpecification getResponseSpec(int statusCode) {
		return new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(statusCode).build();
	}

}
