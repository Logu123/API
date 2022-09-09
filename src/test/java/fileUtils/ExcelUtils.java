package fileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//This class will write to the Excel and compare the values.
public class ExcelUtils {

	public void resetExcel(String filePath) {
		try {
			FileInputStream fis = new FileInputStream(new File(filePath));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = workbook.getSheetAt(0);

			int rowCounts = sheet1.getLastRowNum();
			for (int i = 0; i <= rowCounts; i++) {
				XSSFRow rows = sheet1.getRow(i);
				sheet1.removeRow(rows);
			}
			XSSFRow row = sheet1.createRow(0); // row creation in the excel.

			XSSFCell cell1 = row.createCell(0, CellType.STRING);
			cell1.setCellValue("Sceanrio #");
			XSSFCell cell2 = row.createCell(1, CellType.STRING);
			cell2.setCellValue("ECModalPremium");
			XSSFCell cell3 = row.createCell(2, CellType.STRING);
			cell3.setCellValue("ECMonthlyPremium");
			XSSFCell cell4 = row.createCell(3, CellType.STRING);
			cell4.setCellValue("EEModalPremium");
			XSSFCell cell5 = row.createCell(4, CellType.STRING);
			cell5.setCellValue("EEMonthlyPremium");
			XSSFCell cell6 = row.createCell(5, CellType.STRING);
			cell6.setCellValue("ESModalPremium");
			XSSFCell cell7 = row.createCell(6, CellType.STRING);
			cell7.setCellValue("ESMonthlyPremium");
			XSSFCell cell8 = row.createCell(7, CellType.STRING);
			cell8.setCellValue("FFmodalPremium");
			XSSFCell cell9 = row.createCell(8, CellType.STRING);
			cell9.setCellValue("FFMonthlyPremium");

			fis.close(); // closing the input file stream.

			FileOutputStream fos = new FileOutputStream(filePath);
			workbook.write(fos);
			workbook.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method for writing the data into an Excel.
	public void write2Excel(int rowNum, String path, Double ECModal, Double ECMonthly, Double EEModal, Double EEMonthly,
			Double ESModal, Double ESMonthly, Double FFModal, Double FFMonthly) {

		try {
			FileInputStream fis = new FileInputStream(new File(path)); // passing the file location to work with.

			// opening the excel file.
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheetName = workbook.getSheetAt(0);

			int rowCount = rowNum;
			XSSFRow row = sheetName.createRow(rowCount); // row creation in the excel.

			XSSFCell cell1 = row.createCell(0, CellType.STRING);
			cell1.setCellValue("Sceanrio" + rowNum);

			int columCount = 1;
			XSSFCell cell = row.createCell(columCount); // column creation row wise.

			Double[] data = { ECModal, ECMonthly, EEModal, EEMonthly, ESModal, ESMonthly, FFModal, FFMonthly };

			for (Double value : data) {
				cell = row.createCell(columCount++);
				cell.setCellValue(value); // writing the value cell wise.
			}
			fis.close(); // closing the input file stream.

			FileOutputStream fos = new FileOutputStream(path);
			workbook.write(fos); // saving the changes made to the excel.
//			System.out.println("Scenario " + rowNum + " values updated in Excel");

			// closing the excel file.
			workbook.close();
			fos.close();

			// catching the exception w.r.t file operation
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// method to compare the values from two different excel sheet.
	public void compareExcelRates(String actualPath, String APIpath) {

		int count = 0;
		try {
//			for redirecting print statements to a file of our choice
			File outputFile = new File("src/main/java/Data From API/Summary.txt");
			PrintStream pStream = new PrintStream(outputFile);
			System.setOut(pStream);

			FileInputStream fis = new FileInputStream(new File(actualPath)); // passing the path of the excel which
																				// contains the rates from API.
			XSSFWorkbook workbook = new XSSFWorkbook(fis); // opening the excel with rates from API.

			FileInputStream fis1 = new FileInputStream(new File(APIpath)); // passing the path of excel file which
																			// contains the actual rates.
			XSSFWorkbook workbook1 = new XSSFWorkbook(fis1); // opening the excel with actual rates.

			XSSFSheet sheet1 = workbook1.getSheetAt(0); // reading the first sheet of the excel workbook.
			XSSFSheet sheet2 = workbook.getSheetAt(1); // reading the second sheet of the excel workbook (actual rates).

			// color coding for the pass & fail premiums.
			XSSFCellStyle failStyle = workbook1.createCellStyle(); // style workbook name should be the same in which we
																	// want to do our work
			XSSFCellStyle passStyle = workbook1.createCellStyle();
			failStyle.setFillForegroundColor(IndexedColors.RED1.getIndex());
			failStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			passStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			passStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			int rowCounts = sheet1.getLastRowNum(); // getting the row count for the excel which has rates from API, so
													// that comparison can be done only till the values received from
													// API.
			for (int i = 1; i <= rowCounts; i++) {

				boolean flag = true;
				int colErr = 9;

				// iteration till numbers of row present in API rates excel.
				for (int j = 0; j < 8; j++) {

					Row row1 = sheet1.getRow(i);
					Row row2 = sheet2.getRow(i);

					Cell cell1 = row1.getCell(j + 1);
					Cell cell2 = row2.getCell(j);

					String value1 = Double.toString(cell1.getNumericCellValue());
					String value2 = Double.toString(cell2.getNumericCellValue());

					String premiumName = sheet1.getRow(0).getCell(j + 1).getStringCellValue();
					premiumName = premiumName.substring(0, premiumName.length() - 7);

					// comparing the values -- cell by cell.
					// in case the values mismatches:
					if (!value1.equals(value2)) {
						System.out.println("at Scenario : " + i);

						// after the values/rates, going to next column and writing in the cell which
						// plan's value doesn't matched.
						Cell errCell = row1.createCell(colErr, CellType.STRING);

						errCell.setCellValue(premiumName);
						errCell.setCellStyle(failStyle); // setting the color.
						System.out.println("Mismatch Occurred: Expected- " + value2 + " Got " + value1 + " from API\n");
						System.out.println("For the Plan: " + sheet1.getRow(0).getCell(j + 1).getStringCellValue());
						colErr++;
						flag = false;
					} else { // passed
						Cell errCell = row1.createCell(colErr, CellType.STRING);
						errCell.setCellValue(premiumName);
						errCell.setCellStyle(passStyle);
						colErr++;
					}

				}
				// counting the failed scenarios.
				if (flag == false) {
					count++;
				}

			}
			// printing the numbers of scenarios executed, passed and failed.
			System.out.println("\n------------------------Summary of Scenario Execution------------------------\n");
			System.out.println("Total Scenario Executed = " + rowCounts);
			System.out.println("Passed Scenarios = " + (rowCounts - count));
			System.out.println("Failed Scenarios = " + count);
			System.out.println("\n------------------------Please Ignore anything below this Line------------------------");
			// closing the file operations.
			fis.close();
			fis1.close();

			FileOutputStream fos = new FileOutputStream(APIpath);
			workbook1.write(fos); // saving the changes made to the excel, which contains the rates from API.

			// closing the workbooks.
			workbook.close();
			workbook1.close();

			// catching the exception w.r.t. file operation.
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
