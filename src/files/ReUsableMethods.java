package files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {

	// Return jsonPath
	public static JsonPath rawToJson(String response) {
		JsonPath jsonPath = new JsonPath(response);
		return jsonPath;
	}
	
	// Return Sting of JSon file 
	// Method name like (GenerateStringFromResource)
	public static String jsonFileToString(String filePath) throws IOException {
		return new String(Files.readAllBytes(Paths.get(filePath)));
	}

}
