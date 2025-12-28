package rest;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {

		JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
		// Print No. of courses returned by API
		int count = jsonPath.getInt("courses.size()");
		// Verify if Sum of all Course prices matches with Purchase Amount
		int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
		int sumOfAllCourse = 0;
		for (int i = 0; i < count; i++) {
			int courseTitle = jsonPath.getInt("courses[" + i + "].price");
			int coursePrices = jsonPath.getInt("courses[" + i + "].copies");
			sumOfAllCourse += (coursePrices * courseTitle);
		}
		if (purchaseAmount == sumOfAllCourse) {
			System.out.println(
					"Sum of all Course prices " + sumOfAllCourse + " matches with Purchase Amount " + purchaseAmount);
		} else {
			System.out.println("Sum of all Course prices " + sumOfAllCourse + " is not matches with Purchase Amount "
					+ purchaseAmount);
		}
		
		// Other Way  
		
		Assert.assertEquals(sumOfAllCourse, purchaseAmount);
	}

}
