package actionsAPI;

import fileUtils.ExcelUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

//This class will Parse the JSON response received from API.
public class ResponseBody {

	private JsonPath jsp;
	private Double ECModal, ECMonthly, EEModal, EEMonthly, ESModal, ESMonthly, FFModal, FFMonthly;
	
	//method to parse the JSON response and fetch the values.
	public void postResponse(Response res, int i, String path) {
		jsp = res.jsonPath();
		
		String val01 = jsp.get("ECModalPremium");
		String val02 = jsp.get("ECMonthlyPremium");
		String val03 = jsp.get("EEModalPremium");
		String val04 = jsp.get("EEMonthlyPremium");
		String val05 = jsp.get("ESModalPremium");
		String val06 = jsp.get("ESMonthlyPremium");
		String val07 = jsp.get("FFModalPremium");
		String val08 = jsp.get("FFMonthlyPremium");
		
		ECModal = Double.parseDouble(val01);// ECModal Premium Rate from API.
		ECMonthly = Double.parseDouble(val02); //ECMonthly Premium Rate from API.
		EEModal = Double.parseDouble(val03); //EEModal Premium Rate from API.
		EEMonthly = Double.parseDouble(val04); //EEMonthly Premium Rate from API.
		ESModal = Double.parseDouble(val05); //ESModal Premium Rate from API.
		ESMonthly = Double.parseDouble(val06); //ESMonthly Premium Rate from API.
		FFModal = Double.parseDouble(val07); //FFModal Premium Rate from API.
		FFMonthly = Double.parseDouble(val08); //FFMonthly Premium Rate from API.
		
		//Writing the parsed value from JSON response to Excel file.
		ExcelUtils ex = new ExcelUtils();
		ex.write2Excel(i, path, ECModal, ECMonthly, EEModal, EEMonthly, ESModal, ESMonthly, FFModal, FFMonthly);
		
	}
	
	
}

