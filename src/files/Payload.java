package files;

public class Payload {

	public static String AddPlace() {
		return "{\r\n" + "  \"location\": {\r\n" + "    \"lat\": -38.383494,\r\n" + "    \"lng\": 33.427362\r\n"
				+ "  },\r\n" + "  \"accuracy\": 50,\r\n" + "  \"name\": \"Sandeep house\",\r\n"
				+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "  \"address\": \"29, side layout, cohen 09\",\r\n" + "  \"types\": [\r\n" + "    \"shoe park\",\r\n"
				+ "    \"shop\"\r\n" + "  ],\r\n" + "  \"website\": \"http://Sandeep.com\",\r\n"
				+ "  \"language\": \"French-IN\"\r\n" + "}";
	}

	public static String UpdatePlaceAddress(String placeId, String address) {
		return "{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + address + "\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n" + "}";
	}

	public static String CoursePrice() {
		return "{\r\n" + "  \"dashboard\": {\r\n" + "    \"purchaseAmount\": 910,\r\n"
				+ "    \"website\": \"rahulshettyacademy.com\"\r\n" + "  },\r\n" + "  \"courses\": [\r\n" + "    {\r\n"
				+ "      \"title\": \"Selenium Python\",\r\n" + "      \"price\": 50,\r\n" + "      \"copies\": 6\r\n"
				+ "    },\r\n" + "    {\r\n" + "      \"title\": \"Cypress\",\r\n" + "      \"price\": 40,\r\n"
				+ "      \"copies\": 4\r\n" + "    },\r\n" + "    {\r\n" + "      \"title\": \"RPA\",\r\n"
				+ "      \"price\": 45,\r\n" + "      \"copies\": 10\r\n" + "    }\r\n" + "  ]\r\n" + "}";
	}

	public static String AddBook(String isbnNumber, String aisleNumber) {
		String payload = "{\r\n" + "\r\n" + "\"name\":\"Learn Appium Automation with Java\",\r\n" + "\"isbn\":\""
				+ isbnNumber + "\",\r\n" + "\"aisle\":\"" + aisleNumber + "\",\r\n" + "\"author\":\"John foe\"\r\n"
				+ "}\r\n" + "";
		return payload;
	}
	
	public static String AddIssue(String issueSummary) {
		String payload = "{\r\n"
				+ "  \"fields\": {\r\n"
				+ "    \"project\": {\r\n"
				+ "      \"key\": \"RJ\"\r\n"
				+ "    },\r\n"
				+ "    \"summary\": \""+issueSummary+"\",\r\n"
				+ "    \"issuetype\": {\r\n"
				+ "      \"name\": \"Bug\"\r\n"
				+ "    }\r\n"
				+ "  }\r\n"
				+ "}";
		return payload;
	}

}
