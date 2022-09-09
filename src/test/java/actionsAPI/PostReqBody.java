package actionsAPI;

import static io.restassured.RestAssured.given;

import fileUtils.PayLoads;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
// This class will send the request to the API and will return the Response from API.
public class PostReqBody {

	private static Response response;
	PayLoads payload = new PayLoads();

	public Response postRq(String link, int i, String uname, String pwd, String path) {
		response = given().contentType(ContentType.JSON).auth().basic(uname, pwd).body(payload.jsonFileReading(i, path))
				.post(link).then().extract().response();

		return response; //returns the Response.
	}
}
