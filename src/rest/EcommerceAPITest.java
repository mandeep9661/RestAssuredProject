package rest;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
	String orderId;

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
		System.out.println("Product ID : " + productId);
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

		RequestSpecification requestOrderCreate = given().spec(getRequestSpecWithToken())
				.header("Content-Type", "application/json").body(details);
		String responseOrderCreate = requestOrderCreate.when().post("api/ecom/order/create-order").then().log().all()
				.spec(getResponseSpec(201)).extract().response().asString();
		JsonPath jsonPathOrderCreate = ReUsableMethods.rawToJson(responseOrderCreate);
		orderId = jsonPathOrderCreate.getString("orders[0]");
		System.out.println("Order Id : " + orderId);

	}

	@Test(description = "Get order Place Details", dependsOnMethods = "createOrder")
	public void viewOrderPlaceDetails() {

		RequestSpecification orderPlaceDetailsReq = given().spec(getRequestSpecWithToken()).queryParam("id", orderId);

		String responseOrderPlaceDetails = orderPlaceDetailsReq.when().get("api/ecom/order/get-orders-details").then()
				.log().all().spec(getResponseSpec(200)).extract().response().asString();

		JsonPath jsonPathOrderCreate = ReUsableMethods.rawToJson(responseOrderPlaceDetails);
		String message = jsonPathOrderCreate.getString("message");
		Assert.assertEquals(message, "Orders fetched for customer Successfully", "Order not placed");

	}

	@Test(description = "Delete Product", dependsOnMethods = "createProduct")
	public void deleteProduct() {

		RequestSpecification requestDeleteOrder = given().spec(getRequestSpecWithToken()).pathParam("productId",
				productId);
		requestDeleteOrder.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all()
				.spec(getResponseSpec(200));
	}
	
	@Test(description = "Delete Order", dependsOnMethods = "viewOrderPlaceDetails")
	public void deleteOrder() {

		RequestSpecification requestDeleteOrder = given().spec(getRequestSpecWithToken()).pathParam("orderId",
				orderId);
		requestDeleteOrder.when().delete("api/ecom/order/delete-order/{orderId}").then().log().all()
				.spec(getResponseSpec(200));
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
