package fileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PayLoads {

	//reading the JSON file 
	public String jsonFileReading(int i, String path) {
		String file = path +"Scenario"+i+".json";
		String json="";
		try {
			json = readFileAsString(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public static String readFileAsString(String file) throws IOException{
		return new String(Files.readAllBytes(Paths.get(file)));
	}
	
}
