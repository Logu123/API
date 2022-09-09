package groupACCI;

import java.io.File;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import actionsAPI.PostReqBody;
import actionsAPI.ResponseBody;
import fileUtils.ExcelUtils;
import fileUtils.ReadPropertiesFile;
import io.restassured.response.Response;
import testEmail.testEmail;

public class GrpAcciAPITest {
	private String POSTurl;
	private String username, Password, Excelpath, JsonPath, ActualRateFile;
	private Response response;
	private int fileCounts;

	@BeforeTest
	public void getValues() {
		ReadPropertiesFile property = new ReadPropertiesFile();
		POSTurl = property.URL();
		username = property.UserName();
		Password = property.Password();
		Excelpath = property.ExcelFile();
		JsonPath = property.JsonFile();
		ActualRateFile = property.ActualRateFile();
	}

	@BeforeClass
	public void preRequisite() {
		File fileDir = new File(JsonPath);
		fileCounts = fileDir.list().length;
		if (fileCounts < 1) {
			System.out.println("Your Directory don't have any files");
		}
		// code to reset previous value
		ExcelUtils workBook = new ExcelUtils();
		workBook.resetExcel(Excelpath);
	}

	@Test(priority = 1)
	public void postRequest() {

		for (int i = 1; i <= fileCounts; i++) {
			PostReqBody requestBody = new PostReqBody();
			response = requestBody.postRq(POSTurl, i, username, Password, JsonPath);

			ResponseBody responseBody = new ResponseBody();
			responseBody.postResponse(response, i, Excelpath);
		}
	}

	@Test(priority = 2)
	public void compareRates() {
		ExcelUtils spreadSheet = new ExcelUtils();
		spreadSheet.compareExcelRates(ActualRateFile, Excelpath);
	}

	@Test(priority = 3)
	public static void reports() {
		testEmail reports = new testEmail();
		reports.sendingReports();

	}
}
