package fileUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {

	Properties localStorage = new Properties();

	public Properties readingProperties() {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream("src/main/java/Parameters/credentials.properties");
			prop = new Properties();
			prop.load(fis);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}

	public String URL() {
		localStorage = readingProperties();
		return localStorage.getProperty("URI");
	}

	public String UserName() {
		localStorage = readingProperties();
		return localStorage.getProperty("username");
	}

	public String Password() {
		localStorage = readingProperties();
		return localStorage.getProperty("password");
	}

	public String ExcelFile() {
		localStorage = readingProperties();
		return localStorage.getProperty("excelFile");
	}

	public String JsonFile() {
		localStorage = readingProperties();
		return localStorage.getProperty("jsonPath");
	}

	public String ActualRateFile() {
		localStorage = readingProperties();
		return localStorage.getProperty("actualRateExcel");
	}

	public String toEmails() {
		localStorage = readingProperties();
		return localStorage.getProperty("emailAddresses");
	}
}
