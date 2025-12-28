package rest;

import static org.testng.Assert.assertEquals;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath jsonPath = new JsonPath(Payload.CoursePrice());
		// Print No. of courses returned by API
		int count = jsonPath.getInt("courses.size()");
		System.out.println("No. of count : " + count);

		// Print Purchase Amount
		int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount is : " + purchaseAmount);

		// Print Title of the first course
		String firstCourse = jsonPath.getString("courses[0].title");
		System.out.println("Title of the first course : " + firstCourse);

		// Print All course titles and their respective Prices
		for (int i = 0; i < count; i++) {
			String courseTitle = jsonPath.getString("courses[" + i + "].title");
			int coursePrices = jsonPath.getInt("courses[" + i + "].price");
			System.out.println("Course title is " + courseTitle + " and their respective Price is " + coursePrices);
		}

		// Other way of print this in one line
		for (int i = 0; i < count; i++) {
			System.out.println("Another Way, Course title is " + jsonPath.getString("courses[" + i + "].title")
					+ " and their respective Price is " + jsonPath.getInt("courses[" + i + "].price"));
		}

		// Print no of copies sold by RPA Course
		for (int i = 0; i < count; i++) {
			String courseTitle = jsonPath.getString("courses[" + i + "].title"); // instead of getString use get only
																					// work
			if (courseTitle.equals("RPA")) {
				int copiesNumber = jsonPath.getInt("courses[" + i + "].copies");
				System.out.println("Number of copies sold by RPA Course : " + copiesNumber);
				break;
			}
		}

		// Verify if Sum of all Course prices matches with Purchase Amount
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

	}

}
