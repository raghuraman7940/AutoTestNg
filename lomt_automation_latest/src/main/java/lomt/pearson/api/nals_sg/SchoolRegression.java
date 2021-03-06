package lomt.pearson.api.nals_sg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import lomt.pearson.api.externalframework.ReadExternalFrameworkFile;
import lomt.pearson.constant.LOMTConstant;
import lomt.pearson.constant.SchoolConstant;
import lomt.pearson.constant.TestCases;
import lomt.pearson.page_object.CommonPOM;
import lomt.pearson.page_object.ExternalFrameworkPOM;
import lomt.pearson.page_object.SchoolPOM;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SchoolRegression {
	
	private WebDriver driver;
	private JavascriptExecutor jse;
	private WebDriverWait wait;

	private CommonPOM commonPOM = null;
	private SchoolPOM schoolPOM = null;
	private ExternalFrameworkPOM exfPOM = null;
	
	public SchoolRegression(WebDriver driver) {
		commonPOM = new CommonPOM(driver);
		schoolPOM = new SchoolPOM(driver);
		exfPOM = new ExternalFrameworkPOM(driver);
		
		jse = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 900);
		this.driver = driver;
	}
	
	public boolean schoolGlobalBrowsePage() {
		boolean flag = false;
		try {
			commonPOM.getSchoolGlobalLOB().click();
			if (commonPOM.getManageIngestion().getText().equalsIgnoreCase(LOMTConstant.MANGE_INGESTION)) {
				commonPOM.getManageIngestion().click();
				flag = true;
			}
			
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean getLOBAndStructure() {
		boolean flag = false;
		
		try {
			commonPOM.getSchoolGlobalLOBRadioButton().click();
			Thread.sleep(1000);
			Assert.assertFalse(commonPOM.getNextButtonFirst().isEnabled());
			
			Assert.assertEquals(schoolPOM.getCurriculumStructure().getText(), "Curriculum Standard (ab.xml)");
			Assert.assertEquals(schoolPOM.getCurriculumStructureCustom().getText(), "Curriculum Standard (custom)");
			Assert.assertEquals(schoolPOM.getProductStructure().getText(), "Product");
			Assert.assertEquals(schoolPOM.getIntermediaryStructure().getText(), "Intermediary");
			
			commonPOM.getCurriculumStandardStructure().click();
			jse.executeScript("window.scrollBy(0,500)");
			
			Assert.assertTrue(commonPOM.getNextButtonFirst().isEnabled());
			commonPOM.getNextButtonFirst().click(); 
			Thread.sleep(3000);
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean getMetaDataFields(int year) {
		boolean flag = false;
		try {
			Thread.sleep(4000);
			//SUBJECT Selection
			schoolPOM.getSubjectDropdown().click();
			Thread.sleep(6000);
			List<WebElement> subjectList = schoolPOM.getSubjectDropdownList();
			int subjectLength = subjectList.size();
			if (subjectLength > 0) {
				for (int i = 0; i <= subjectLength; i++) {
					WebElement element = subjectList.get(i);
					if (element.getText()!= null) {
						element.click();
						flag = true;
						break;
					}
				}
			} else {
				Assert.assertFalse((subjectLength == 0), LOMTConstant.SUBJECT+LOMTConstant.DROPDOWN_SIZE_NOT_ZERO);
				return flag;
			}
			
			//Authority Selection, pick authority name, 
			schoolPOM.getAuthorityDropdown().click();
			Thread.sleep(6000);
			List<WebElement> authorityList = schoolPOM.getAuthorityDropdownList();
			int aLength = authorityList.size();
			if (aLength > 0) {
				for (int i = 0; i <= aLength; i++) {
					WebElement element = authorityList.get(i);
					if (element.getText()!= null) {
						element.click();
						flag = true;
						break;
					}
				}
			} else {
				Assert.assertFalse((subjectLength == 0), "Authority"+LOMTConstant.DROPDOWN_SIZE_NOT_ZERO);
				return flag;
			}
			
			//Curriculum Selection
			schoolPOM.getCurriculumSetDropdown().click();
			Thread.sleep(6000);
			List<WebElement> curriculumSetList = schoolPOM.getCurriculumSetDropdownList();
			int csLength = curriculumSetList.size();
			if (csLength > 0) {
				for (int i = 0; i <= csLength; i++) {
					WebElement element = curriculumSetList.get(i);
					if (element.getText()!= null) {
						element.click();
						flag = true;
						break;
					}
				}
			} else {
				Assert.assertFalse((subjectLength == 0), "Curriculum Set"+LOMTConstant.DROPDOWN_SIZE_NOT_ZERO);
				return flag;
			}
			
			//Adopted Year Selection
			schoolPOM.getAdoptedYear().sendKeys(String.valueOf(year));
			
			jse.executeScript("window.scrollBy(0,500)");
			
			commonPOM.getNextButton().click();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean getMetaDataReingestionFields(int year) {
		boolean flag = false;
		
		int counter = 0;
		try {
			Thread.sleep(3000);
			//SUBJECT Selection
			schoolPOM.getSubjectDropdown().click();
			Thread.sleep(6000);
			List<WebElement> subjectList = schoolPOM.getSubjectDropdownList();
			int subjectLength = subjectList.size();
			if (subjectLength > 0) {
				for (int i = 0; i <= subjectLength; i++) {
					WebElement element = subjectList.get(i);
					if (element.getText()!= null && counter == 1) {
						element.click();
						flag = true;
						counter = 0;
						break;
					}
					counter++;
				}
			} else {
				Assert.assertFalse((subjectLength == 0), LOMTConstant.SUBJECT+LOMTConstant.DROPDOWN_SIZE_NOT_ZERO);
				return flag;
			}
			
			//Authority Selection, pick authority name, 
			schoolPOM.getAuthorityDropdown().click();
			Thread.sleep(6000);
			List<WebElement> authorityList = schoolPOM.getAuthorityDropdownList();
			int aLength = authorityList.size();
			if (aLength > 0) {
				for (int i = 0; i <= aLength; i++) {
					WebElement element = authorityList.get(i);
					if (element.getText()!= null && counter == 1) {
						element.click();
						flag = true;
						counter = 0;
						break;
					}
					counter++;
				}
			} else {
				Assert.assertFalse((subjectLength == 0), "Authority"+LOMTConstant.DROPDOWN_SIZE_NOT_ZERO);
				return flag;
			}
			
			//Curriculum Selection
			schoolPOM.getCurriculumSetDropdown().click();
			Thread.sleep(6000);
			List<WebElement> curriculumSetList = schoolPOM.getCurriculumSetDropdownList();
			int csLength = curriculumSetList.size();
			if (csLength > 0) {
				for (int i = 0; i <= csLength; i++) {
					WebElement element = curriculumSetList.get(i);
					if (element.getText()!= null && counter == 1) {
						element.click();
						flag = true;
						counter = 0;
						break;
					}
					counter++;
				}
			} else {
				Assert.assertFalse((subjectLength == 0), "Curriculum Set"+LOMTConstant.DROPDOWN_SIZE_NOT_ZERO);
				return flag;
			}
			
			//Adopted Year Selection
			schoolPOM.getAdoptedYear().sendKeys(String.valueOf(year));
			
			jse.executeScript("window.scrollBy(0,500)");
			
			commonPOM.getNextButton().click();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean schoolCurriculumIngestion() {
		
		boolean flag = false;
		try {
			commonPOM.getUploadFileLink().click();
			//Thread.sleep(2000);
			
			Runtime.getRuntime().exec(SchoolConstant.SCHOOL_CURRICULUM_FILE_PATH);
			
			Thread.sleep(10000);		
			//jse.executeScript("window.scrollBy(0, 300)");
			commonPOM.getNextButtonSt2().click();
			
			jse.executeScript("window.scrollBy(0, -300)");
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOMTConstant.INGESTION_STATUS)));
			Thread.sleep(3000);
			
			if (commonPOM.getWaitForIngestionStatusText().getText().contains(LOMTConstant.INGESTION_SUCESSFULL_MESSAGE)) {
				flag = true;
				
				jse.executeScript("window.scrollBy(0,-500)");
				commonPOM.getBackLinkFirst().click();
				Thread.sleep(1000);
			} else {
				flag = false;
				
				jse.executeScript("window.scrollBy(0,-500)");
				commonPOM.getBackLinkFirst().click();
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean ingestWrongFile() {
		
		boolean flag = false;
		try {
			commonPOM.getUploadFileLink().click();
			Thread.sleep(2000);
			
			Runtime.getRuntime().exec(SchoolConstant.SCHOOL_CURRICULUM_WRONG_FILE_PATH);
			
			jse.executeScript("window.scrollBy(0, 300)");
			Thread.sleep(4000);		
			commonPOM.getNextButtonSt2().click();
			jse.executeScript("window.scrollBy(0, -400)");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOMTConstant.INGESTION_STATUS)));
			Thread.sleep(3000);
			if (commonPOM.getWaitForIngestionStatusText().getText().contains(LOMTConstant.INGESTION_FAILED_MESSAGE)) {
				flag = true;
				
				jse.executeScript("window.scrollBy(0,-500)");
				commonPOM.getPearsonLogo().click();
				Thread.sleep(1000);
			} else {
				flag = false;
				
				jse.executeScript("window.scrollBy(0,-500)");
				commonPOM.getPearsonLogo().click();
				Thread.sleep(1000);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean verifyingestedDataUI(boolean ingestionFlag, int year) {
		boolean flag = false;
		
		try {
			if (ingestionFlag) {
				commonPOM.getSchoolGlobalLOB().click();
				schoolPOM.getCurriculumSt().click();
				//Thread.sleep(15000);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
				
				//exfPOM.getEnterSearchTerm().sendKeys(String.valueOf(year)); // CSS changed
				schoolPOM.getEnterEnterSearch().sendKeys(String.valueOf(year));
				Thread.sleep(1000);
				
				//exfPOM.getUpdateResultButton().click(); cc changed
				schoolPOM.getSchoolUpdateResultButton().click();
				//Thread.sleep(10000);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
				
				schoolPOM.getEnterEnterSearch().clear();
				jse.executeScript("window.scrollBy(0,350)");
				
				if(schoolPOM.getResultFound().getText().contains("Showing")) {
					schoolPOM.getCurriculumGoalFramework().click();
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
					
					jse.executeScript("window.scrollBy(0,300)");
					
					List<String> list = SchoolConstant.getCurrilumTestData();
					for (String data : list) {
						schoolPOM.getInnerEnterSearch().sendKeys(data);
						Thread.sleep(1000);
						
						schoolPOM.getSchoolInnerUpdateResultButton().click();				
						//Thread.sleep(10000);
						wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
						if (schoolPOM.getInnerResultFound().getText().contains("Found Results")) {
							schoolPOM.getInnerEnterSearch().clear();
							flag = true;
							continue;
						} else {
							flag = false;
							return flag;
						}
					}
				} else {
					return flag;
				}
			} else {
				return flag;
			}
		} catch (Exception e) {
			flag = false;
			return flag;
		}
		return flag;
	}
	
	public void getHomePage() {
		try {
			jse.executeScript("window.scrollBy(0,-1000)");
			commonPOM.getPearsonLogo().click();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void searchAndExportIngestedCurriculumData(int year, ExtentTest logger, String fileSize) {
		try {
			commonPOM.getSchoolGlobalLOB().click();
			schoolPOM.getCurriculumSt().click();
			//Thread.sleep(15000);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
			
			jse.executeScript("window.scrollBy(0,100)");
			
			schoolPOM.getEnterEnterSearch().sendKeys(String.valueOf(year));
			Thread.sleep(1000);
			
			schoolPOM.getSchoolUpdateResultButton().click();				
			//Thread.sleep(10000);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
			
			jse.executeScript("window.scrollBy(0,300)");
			if(schoolPOM.getResultFound().getText().contains("Showing")) {
				removeExistingFile();
				schoolPOM.getAction().click();
				Thread.sleep(1000);
				
				logger.log(LogStatus.PASS, "TC-LOMT-612_16_Verify_Export_Option_In_Actions_For_CurriculumStandard_SchoolGlobal");
				
				schoolPOM.getExport().click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
				logger.log(LogStatus.PASS, "TC-LOMT-612_17_Verify_Export_CurriculumStandard_To_ExcelFile_SchoolGlobal");
				if (fileSize.equalsIgnoreCase("small")) {
					verifiedExportedFile(logger, fileSize);
				} else {
					verifiedExportedFile(logger, fileSize);
				}
				
				jse.executeScript("window.scrollBy(0,-800)");
				commonPOM.getPearsonLogo().click();
			} else {
				logger.log(LogStatus.FAIL, "Goalframework is not found using Enter search term option");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void verifiedExportedFile(ExtentTest logger, String fileSize) {
		InputStream inputStream = null;
		XSSFWorkbook workbook = null;
		XSSFSheet worksheet1 = null;
		XSSFSheet worksheet2 = null;
		XSSFSheet worksheet3 = null;
		ReadExternalFrameworkFile readExternalFrameworkFile = null;
		
		if (fileSize.equalsIgnoreCase("small")) {
			try {
				readExternalFrameworkFile = new ReadExternalFrameworkFile();
				String exportedFileName = readExternalFrameworkFile.getFileFromDirectory(LOMTConstant.EXPORTED_FILE_PATH);
				
				File curriculumExpFile = new File(LOMTConstant.EXPORTED_FILE_PATH + exportedFileName);
				
				inputStream =  new FileInputStream(curriculumExpFile);
				workbook = new XSSFWorkbook(inputStream);
				
				logger.log(LogStatus.PASS, "TC-LOMT-612_18_Verify_Curriculum Standard_written_To_ExcelSpreadsheet_SchoolGlobal");
				logger.log(LogStatus.PASS, "TC-LOMT-612_19_Verify_Title_Of_Excel_Spreadsheet_Is_curriculumStandardLabel_SchoolGlobal");
				
				worksheet1 = workbook.getSheetAt(0);
				
				//Title 
				if (!worksheet1.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty() ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_20_Verify_cell A1_label for the standard_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_20_Verify_cell A1_label for the standard_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//URN 
				if (!worksheet1.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty() ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_21_Verify_cellB1_URN_of_Standard_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_21_Verify_cellB1_URN_of_Standard_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				// Headers
				//Unique ID
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().contains(SchoolConstant.UNIQUE_ID) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_22_Verify_cell_A2 has column_Heading_Unique ID_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_22_Verify_cell_A2 has column_Heading_Unique ID_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Grade Low
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().contains(SchoolConstant.GRADE_LOW) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_23_Verify_cell_B2 has column_Heading_Grade Low_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_23_Verify_cell_B2 has column_Heading_Grade Low_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Grade High
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().contains(SchoolConstant.GRADE_HIGH) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_24_Verify_cell_C2 has column_Heading_Grade High_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_24_Verify_cell_C2 has column_Heading_Grade High_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Grade Title
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().contains(SchoolConstant.GRADE_TITLE) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_25_Verify_cell_D2 has column_Heading_Grade Title_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_25_Verify_cell_D2 has column_Heading_Grade Title_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Official Standard Code
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().contains(SchoolConstant.OFFICIAL_STANDARD_CODE) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_26_Verify_cell_E2 has column_Heading_OfficialStandardsCode_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_26_Verify_cell_E2 has column_Heading_OfficialStandardsCode_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Level 1
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().contains(SchoolConstant.LEVEL_1) ) {
				} else {
					logger.log(LogStatus.FAIL, "Level 1 Matched");
				}
				
				//Level 2
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().contains(SchoolConstant.LEVEL_2) ) {
				} else {
					logger.log(LogStatus.FAIL, "Level 2 Matched");
				}
				
				//Level 3
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().contains(SchoolConstant.LEVEL_3) ) {
				} else {
					logger.log(LogStatus.FAIL, "Level 3 Matched");
				}
				
				//Level 4
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().contains(SchoolConstant.LEVEL_4) ) {
				} else {
					logger.log(LogStatus.FAIL, "Level 4 Matched");
				}
				
				//Level 5, Grade K-12, Lowest Level Grade k-1, Grade 1
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().contains(SchoolConstant.LEVEL_5) ) {
				} else {
					logger.log(LogStatus.FAIL, "Grade K-12 : Level 3 Matched, Grade K-1 & Grade 1 : Lowest Level matched");
				}
				
				//Lowest 6, Tags
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().contains(SchoolConstant.LEVEL_6)) {
				} else {
					logger.log(LogStatus.FAIL, "Level 6 Matched");
				}
				
				//Lowest Level
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ELEVENTH).getStringCellValue().isEmpty() 
						&& worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ELEVENTH).getStringCellValue().contains(SchoolConstant.LOWEST_LEVEL) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_27_Verify_cell_F2 has column_Headings_ForEachLevel_Culminating_LowestLevel_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "Lowest Level Matched");
				}
				
				//Tags
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWELEVE).getStringCellValue().isEmpty() 
						&& worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWELEVE).getStringCellValue().contains(SchoolConstant.TAGS) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_28_Verify_Last_cell_column_Heading_Tags_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_28_Verify_Last_cell_column_Heading_Tags_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				// Verify Data
				//Grade K-12, Row 3
				if (worksheet1.getRow(LOMTConstant.TWO) != null) {
					
					Assert.assertNotNull(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.ZERO).getStringCellValue());// URN
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.ONE).getStringCellValue(), "K"); //Grade Low
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.TWO).getStringCellValue(), "12"); //Grade High
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.THREE).getStringCellValue(), "K-12"); //Grade Title
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.FOUR).getStringCellValue(), "R"); //OSCode
					
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.FIVE).getStringCellValue(), "Reading"); //Level 1
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.THREE).getCell(LOMTConstant.SIX).getStringCellValue(), "Key Ideas and Details"); //Level 2
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.FOUR).getCell(LOMTConstant.SEVEN).getStringCellValue(), "Read closely to determine what the text says explicitly and to make logical inferences from it; cite specific textual evidence when writing or speaking to support conclusions drawn from the text."); //Level 3
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.FIVE).getCell(LOMTConstant.EIGHT).getStringCellValue(), "Key Ideas and Details updated-1."); //Level 4
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.SIX).getCell(LOMTConstant.NINE).getStringCellValue(), "Key Ideas and Details updated-2."); //Level 5
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.SEVEN).getCell(LOMTConstant.TEN).getStringCellValue(), "Key Ideas and Details updated-3."); //Level 6
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.EIGHT).getCell(LOMTConstant.ELEVENTH).getStringCellValue(), "Key Ideas and Details updated-4."); //Lowest Level 
					
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_29_Verify_Educational_goal_row3_onwards_In_ExcelSpreadsheet_SchoolGlobal : Grade K-12");
					logger.log(LogStatus.FAIL, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal : Grade K-12");
				}
				
				if (worksheet1.getRow(57) != null) {
					Assert.assertEquals(worksheet1.getRow(57).getCell(LOMTConstant.FIVE).getStringCellValue(), "Language"); //Level 1
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal : Grade K-12");
				}
				
				logger.log(LogStatus.PASS, "TC-LOMT-612_29_Verify_Educational_goal_row3_onwards_In_ExcelSpreadsheet_SchoolGlobal");
				logger.log(LogStatus.PASS, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal");
				
				inputStream.close();			
			} catch (Exception e) {
				logger.log(LogStatus.FAIL, "Curriculum Export Data verification is failed");
			}
		} else {
			// medium file
			try {
				readExternalFrameworkFile = new ReadExternalFrameworkFile();
				String exportedFileName = readExternalFrameworkFile.getFileFromDirectory(LOMTConstant.EXPORTED_FILE_PATH);
				
				File curriculumExpFile = new File(LOMTConstant.EXPORTED_FILE_PATH + exportedFileName);
				
				inputStream =  new FileInputStream(curriculumExpFile);
				workbook = new XSSFWorkbook(inputStream);
				
				logger.log(LogStatus.PASS, "TC-LOMT-612_18_Verify_Curriculum Standard_written_To_ExcelSpreadsheet_SchoolGlobal");
				logger.log(LogStatus.PASS, "TC-LOMT-612_19_Verify_Title_Of_Excel_Spreadsheet_Is_curriculumStandardLabel_SchoolGlobal");
				
				worksheet1 = workbook.getSheetAt(0);
				worksheet2 = workbook.getSheetAt(1);
				worksheet3 = workbook.getSheetAt(2);
				
				//Title 
				if (!worksheet1.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty()
						&& !worksheet2.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty()
						&& !worksheet3.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty()) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_20_Verify_cell A1_label for the standard_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_20_Verify_cell A1_label for the standard_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//URN 
				if (!worksheet1.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty()
						&& !worksheet2.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty()
						&& !worksheet3.getRow(LOMTConstant.ZERO).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty()) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_21_Verify_cellB1_URN_of_Standard_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_21_Verify_cellB1_URN_of_Standard_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				// Headers
				//Unique ID
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().contains(SchoolConstant.UNIQUE_ID)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().contains(SchoolConstant.UNIQUE_ID)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ZERO).getStringCellValue().contains(SchoolConstant.UNIQUE_ID) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_22_Verify_cell_A2 has column_Heading_Unique ID_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_22_Verify_cell_A2 has column_Heading_Unique ID_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Grade Low
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().contains(SchoolConstant.GRADE_LOW)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().contains(SchoolConstant.GRADE_LOW)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ONE).getStringCellValue().contains(SchoolConstant.GRADE_LOW) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_23_Verify_cell_B2 has column_Heading_Grade Low_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_23_Verify_cell_B2 has column_Heading_Grade Low_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Grade High
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().contains(SchoolConstant.GRADE_HIGH)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().contains(SchoolConstant.GRADE_HIGH)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWO).getStringCellValue().contains(SchoolConstant.GRADE_HIGH)) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_24_Verify_cell_C2 has column_Heading_Grade High_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_24_Verify_cell_C2 has column_Heading_Grade High_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Grade Title
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().contains(SchoolConstant.GRADE_TITLE)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().contains(SchoolConstant.GRADE_TITLE)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.THREE).getStringCellValue().contains(SchoolConstant.GRADE_TITLE)) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_25_Verify_cell_D2 has column_Heading_Grade Title_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_25_Verify_cell_D2 has column_Heading_Grade Title_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Official Standard Code
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().contains(SchoolConstant.OFFICIAL_STANDARD_CODE)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().contains(SchoolConstant.OFFICIAL_STANDARD_CODE)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FOUR).getStringCellValue().contains(SchoolConstant.OFFICIAL_STANDARD_CODE)) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_26_Verify_cell_E2 has column_Heading_OfficialStandardsCode_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_26_Verify_cell_E2 has column_Heading_OfficialStandardsCode_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				//Level 1
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().contains(SchoolConstant.LEVEL_1)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().contains(SchoolConstant.LEVEL_1)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.FIVE).getStringCellValue().contains(SchoolConstant.LEVEL_1)) {
				} else {
					logger.log(LogStatus.FAIL, "Level 1 Matched");
				}
				
				//Level 2
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().contains(SchoolConstant.LEVEL_2)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().contains(SchoolConstant.LEVEL_2)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SIX).getStringCellValue().contains(SchoolConstant.LEVEL_2)) {
				} else {
					logger.log(LogStatus.FAIL, "Level 2 Matched");
				}
				
				//Level 3
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().contains(SchoolConstant.LEVEL_3)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().contains(SchoolConstant.LEVEL_3)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.SEVEN).getStringCellValue().contains(SchoolConstant.LEVEL_3)) {
				} else {
					logger.log(LogStatus.FAIL, "Level 3 Matched");
				}
				
				//Level 4
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().contains(SchoolConstant.LEVEL_4)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().contains(SchoolConstant.LEVEL_4)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.EIGHT).getStringCellValue().contains(SchoolConstant.LEVEL_4)) {
				} else {
					logger.log(LogStatus.FAIL, "Level 4 Matched");
				}
				
				//Level 5, Grade K-12, Lowest Level Grade k-1, Grade 1
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().contains(SchoolConstant.LEVEL_5)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().contains(SchoolConstant.LOWEST_LEVEL)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.NINE).getStringCellValue().contains(SchoolConstant.LOWEST_LEVEL)) {
				} else {
					logger.log(LogStatus.FAIL, "Grade K-12 : Level 3 Matched, Grade K-1 & Grade 1 : Lowest Level matched");
				}
				
				//Lowest 6, Tags
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().isEmpty() && worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().contains(SchoolConstant.LEVEL_6)
						&& !worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().isEmpty() && worksheet2.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().contains(SchoolConstant.TAGS)
						&& !worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().isEmpty() && worksheet3.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TEN).getStringCellValue().contains(SchoolConstant.TAGS)) {
				} else {
					logger.log(LogStatus.FAIL, "Level 6 Matched");
				}
				
				//Lowest Level
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ELEVENTH).getStringCellValue().isEmpty() 
						&& worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.ELEVENTH).getStringCellValue().contains(SchoolConstant.LOWEST_LEVEL) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_27_Verify_cell_F2 has column_Headings_ForEachLevel_Culminating_LowestLevel_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "Lowest Level Matched");
				}
				
				//Tags
				if (!worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWELEVE).getStringCellValue().isEmpty() 
						&& worksheet1.getRow(LOMTConstant.ONE).getCell(LOMTConstant.TWELEVE).getStringCellValue().contains(SchoolConstant.TAGS) ) {
					logger.log(LogStatus.PASS, "TC-LOMT-612_28_Verify_Last_cell_column_Heading_Tags_In_ExcelSpreadsheet_SchoolGlobal");
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_28_Verify_Last_cell_column_Heading_Tags_In_ExcelSpreadsheet_SchoolGlobal");
				}
				
				// Verify Data
				//Grade K-12, Row 3
				if (worksheet1.getRow(LOMTConstant.TWO) != null) {
					
					Assert.assertNotNull(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.ZERO).getStringCellValue());// URN
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.ONE).getStringCellValue(), "K"); //Grade Low
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.TWO).getStringCellValue(), "12"); //Grade High
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.THREE).getStringCellValue(), "K-12"); //Grade Title
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.FOUR).getStringCellValue(), "R"); //OSCode
					
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.TWO).getCell(LOMTConstant.FIVE).getStringCellValue(), "Reading"); //Level 1
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.THREE).getCell(LOMTConstant.SIX).getStringCellValue(), "Key Ideas and Details"); //Level 2
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.FOUR).getCell(LOMTConstant.SEVEN).getStringCellValue(), "Read closely to determine what the text says explicitly and to make logical inferences from it; cite specific textual evidence when writing or speaking to support conclusions drawn from the text."); //Level 3
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.FIVE).getCell(LOMTConstant.EIGHT).getStringCellValue(), "Key Ideas and Details updated-1."); //Level 4
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.SIX).getCell(LOMTConstant.NINE).getStringCellValue(), "Key Ideas and Details updated-2."); //Level 5
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.SEVEN).getCell(LOMTConstant.TEN).getStringCellValue(), "Key Ideas and Details updated-3."); //Level 6
					Assert.assertEquals(worksheet1.getRow(LOMTConstant.EIGHT).getCell(LOMTConstant.ELEVENTH).getStringCellValue(), "Key Ideas and Details updated-4."); //Lowest Level 
					
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_29_Verify_Educational_goal_row3_onwards_In_ExcelSpreadsheet_SchoolGlobal : Grade K-12");
					logger.log(LogStatus.FAIL, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal : Grade K-12");
				}
				
				if (worksheet1.getRow(57) != null) {
					Assert.assertEquals(worksheet1.getRow(57).getCell(LOMTConstant.FIVE).getStringCellValue(), "Language"); //Level 1
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal : Grade K-12");
				}
				
				//Grade K
				if (worksheet2.getRow(55) != null) {
					Assert.assertEquals(worksheet2.getRow(55).getCell(LOMTConstant.FIVE).getStringCellValue(), "Writing"); //Level 1
					Assert.assertEquals(worksheet2.getRow(56).getCell(LOMTConstant.SIX).getStringCellValue(), "Text Types and Purposes"); //Level 1
					Assert.assertEquals(worksheet2.getRow(57).getCell(LOMTConstant.SEVEN).getStringCellValue(), "Use a combination of drawing, dictating, and writing to compose opinion pieces in which they tell a reader the topic or the name of the book they are writing about and state an opinion or preference about the topic or book (e.g., My favorite book is...)."); //Level 1
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal : Grade K");
				}
				
				//Grade
				if (worksheet3.getRow(57) != null) {
					Assert.assertEquals(worksheet3.getRow(LOMTConstant.TWO).getCell(LOMTConstant.FIVE).getStringCellValue(), "Reading advanced"); //Level 1
					Assert.assertEquals(worksheet3.getRow(57).getCell(LOMTConstant.FIVE).getStringCellValue(), "Writing"); //Level 1
					Assert.assertEquals(worksheet3.getRow(72).getCell(LOMTConstant.FIVE).getStringCellValue(), "Speaking and Listening"); //Level 1
					Assert.assertEquals(worksheet3.getRow(84).getCell(LOMTConstant.FIVE).getStringCellValue(), "Language"); //Level 1
				} else {
					logger.log(LogStatus.FAIL, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal : Grade 1");
				}
				
				logger.log(LogStatus.PASS, "TC-LOMT-612_29_Verify_Educational_goal_row3_onwards_In_ExcelSpreadsheet_SchoolGlobal");
				logger.log(LogStatus.PASS, "TC-LOMT-612_30_Verify_RenderedRowData_In_ExcelSpreadsheet_SchoolGlobal");
				
				inputStream.close();			
			} catch (Exception e) {
				logger.log(LogStatus.FAIL, "Curriculum Export Data verification is failed");
			}
		}
	}
	
	public void removeExistingFile() throws IOException {
		if (new File(LOMTConstant.EXPORTED_FILE_PATH).exists()) 
			FileUtils.cleanDirectory(new File(LOMTConstant.EXPORTED_FILE_PATH));
	}
	
	public void reIngestionCurriculumStandard(ExtentTest logger, String useCase) {
		try {
			//Update goal framework like name and meta data
			if (useCase.equalsIgnoreCase(SchoolConstant.C_USECASE_1) ) {
				commonPOM.getUploadFileLink().click();
				
				Runtime.getRuntime().exec(SchoolConstant.SCHOOL_CURRICULUM_FILE_PATH_1);
				
				Thread.sleep(10000);		
				//jse.executeScript("window.scrollBy(0, 300)");
				commonPOM.getNextButtonSt2().click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOMTConstant.INGESTION_STATUS)));
				Thread.sleep(3000);
				jse.executeScript("window.scrollBy(0, -300)");
				if (commonPOM.getWaitForIngestionStatusText().getText().contains(LOMTConstant.INGESTION_SUCESSFULL_MESSAGE)) {
					
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_01_RE_INGEST_CURRICULUM);
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_10_RE_INGEST_UPDATE_NAME);
					
					jse.executeScript("window.scrollBy(0,-500)");
					commonPOM.getBackLinkFirst().click();
					Thread.sleep(1000);
				} else {
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_01_RE_INGEST_CURRICULUM);
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_10_RE_INGEST_UPDATE_NAME);
					
					jse.executeScript("window.scrollBy(0,-500)");
					commonPOM.getBackLinkFirst().click();
					Thread.sleep(1000);
				}
			} 
			// update fields like description, state num etc
			else if (useCase.equalsIgnoreCase(SchoolConstant.C_USECASE_2) ) {
				commonPOM.getUploadFileLink().click();
				
				Runtime.getRuntime().exec(SchoolConstant.SCHOOL_CURRICULUM_FILE_PATH_2);
				
				Thread.sleep(10000);		
				//jse.executeScript("window.scrollBy(0, 300)");
				commonPOM.getNextButtonSt2().click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOMTConstant.INGESTION_STATUS)));
				Thread.sleep(3000);
				jse.executeScript("window.scrollBy(0, -300)");
				if (commonPOM.getWaitForIngestionStatusText().getText().contains(LOMTConstant.INGESTION_SUCESSFULL_MESSAGE)) {
					
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_04_RE_INGEST_UPDATE_DESCNODE);
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_05_RE_INGEST_UPDATE_STATENUM);
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_06_RE_INGEST_UPDATE_HIERARCHY_PARENT_CHILD);
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_07_RE_INGEST_UPDATE_GRADEVALUE);
					
					
					jse.executeScript("window.scrollBy(0,-500)");
					commonPOM.getBackLinkFirst().click();
					Thread.sleep(1000);
				} else {
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_04_RE_INGEST_UPDATE_DESCNODE);
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_05_RE_INGEST_UPDATE_STATENUM);
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_06_RE_INGEST_UPDATE_HIERARCHY_PARENT_CHILD);
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_07_RE_INGEST_UPDATE_GRADEVALUE);
					
					jse.executeScript("window.scrollBy(0,-500)");
					commonPOM.getBackLinkFirst().click();
					Thread.sleep(1000);
				}
			} 
			//Add new node
			else if (useCase.equalsIgnoreCase(SchoolConstant.C_USECASE_3) ) {
				commonPOM.getUploadFileLink().click();
				
				Runtime.getRuntime().exec(SchoolConstant.SCHOOL_CURRICULUM_FILE_PATH_3);
				
				Thread.sleep(10000);		
				//jse.executeScript("window.scrollBy(0, 300)");
				commonPOM.getNextButtonSt2().click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOMTConstant.INGESTION_STATUS)));
				Thread.sleep(3000);
				jse.executeScript("window.scrollBy(0, -300)");
				if (commonPOM.getWaitForIngestionStatusText().getText().contains(LOMTConstant.INGESTION_SUCESSFULL_MESSAGE)) {
					
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_08_RE_INGEST_NEWNODE);
					
					jse.executeScript("window.scrollBy(0,-500)");
					commonPOM.getBackLinkFirst().click();
					Thread.sleep(1000);
				} else {
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_08_RE_INGEST_NEWNODE);
					
					jse.executeScript("window.scrollBy(0,-500)");
					commonPOM.getBackLinkFirst().click();
					Thread.sleep(1000);
				}
			} else {
				//SchoolConstant.C_USECASE_4 (delete node)

				commonPOM.getUploadFileLink().click();
				
				Runtime.getRuntime().exec(SchoolConstant.SCHOOL_CURRICULUM_FILE_PATH_4);
				
				Thread.sleep(10000);						
				commonPOM.getNextButtonSt2().click();
				
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOMTConstant.INGESTION_STATUS)));
				Thread.sleep(3000);
				jse.executeScript("window.scrollBy(0, -300)");
				if (commonPOM.getWaitForIngestionStatusText().getText().contains(LOMTConstant.INGESTION_SUCESSFULL_MESSAGE)) {
					
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_09_RE_INGEST_DELETENODE);
					
					jse.executeScript("window.scrollBy(0,-800)");
					commonPOM.getPearsonLogo().click();
					Thread.sleep(1000);
				} else {
					logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_09_RE_INGEST_DELETENODE);
					
					jse.executeScript("window.scrollBy(0,-800)");
					commonPOM.getPearsonLogo().click();
					Thread.sleep(1000);
				}
			}
		} catch (Exception e) {
			logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion failed due to Exception");
			e.printStackTrace();
		}
	}
	
	public void verifyReingestedDataUI(ExtentTest logger, int yearReingestion) {
		boolean dataVFlag = true;
		try {
			commonPOM.getSchoolGlobalLOB().click();
			schoolPOM.getCurriculumSt().click();

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
			exfPOM.getEnterSearchTerm().sendKeys(String.valueOf(yearReingestion));
			Thread.sleep(1000);
			
			exfPOM.getUpdateResultButton().click();				
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
			
			exfPOM.getEnterSearchTerm().clear();
			jse.executeScript("window.scrollBy(0,350)");
			
			if(schoolPOM.getResultFound().getText().contains("Showing")) {
				schoolPOM.getCurriculumGoalFramework().click();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
				jse.executeScript("window.scrollBy(0,300)");
				
				// compare grades name
				if (schoolPOM.getGradeText1().getText().contains(SchoolConstant.GRADE_1)) {
				} else {
					dataVFlag = false;
					logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Grade name does not match 1");
				}
				if (schoolPOM.getGradeText2().getText().contains(SchoolConstant.GRADE_2)) {
				} else {
					dataVFlag = false;
					logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Grade name does not match 2");
				}
				if (schoolPOM.getGradeText3().getText().contains(SchoolConstant.GRADE_2)) {
				} else {
					dataVFlag = false;
					logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Grade name does not match 2");
				}
				//grades comparision end
				
				//Parent child description comparision
				int pcCounter = 1;
				List<String> list = SchoolConstant.getCurrilumDescUpdatedData();
				for (String data : list) {
					schoolPOM.getInnerEnterSearch().sendKeys(data);
					Thread.sleep(1000);
					
					//exfPOM.getUpdateResultButton().click();		
					schoolPOM.getSchoolInnerUpdateResultButton().click();
					Thread.sleep(10000);
					//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
					List<WebElement> webElement  =  schoolPOM.getParentChildList();
					if (!webElement.isEmpty()) {
						Iterator<WebElement> itr = webElement.iterator();
						while (itr.hasNext()) {
							WebElement childStructureElement = 	itr.next();
							String structureName = childStructureElement.getText();
							if (structureName.contains(data)) {
								pcCounter++;
								continue;
							} else {
								pcCounter++;
								dataVFlag = false;
								logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Parent/Child description does not match, "+"Level - "+pcCounter);
							}
						}
						schoolPOM.getInnerEnterSearch().clear();
						//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOMTConstant.LOADER)));
						continue;
					} else {
						dataVFlag = false;
						logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Parent/Child description does not filtered using Enter serach term option"
								+ "so data verification is failed");
					}
				}
				
				//Verifying Newly added node
				List<String> list1 = SchoolConstant.getCurrilumNewAddedNode();
				for (String data : list1) {
					schoolPOM.getInnerEnterSearch().sendKeys(data);
					Thread.sleep(1000);
					
					exfPOM.getUpdateResultButton().click();				
					Thread.sleep(10000);
					
					List<WebElement> webElement  =  schoolPOM.getParentChildList();
					if (!webElement.isEmpty()) {
						Iterator<WebElement> itr = webElement.iterator();
						while (itr.hasNext()) {
							WebElement childStructureElement = 	itr.next();
							String structureName = childStructureElement.getText();
							if (structureName.contains(data)) {
								pcCounter++;
								continue;
							} else {
								pcCounter++;
								dataVFlag = false;
								logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Newly added does not found using Enter search terms field");
							}
						}
						schoolPOM.getInnerEnterSearch().clear();
						continue;
					} else {
						dataVFlag = false;
						logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Newly added does not found using Enter search terms field");
					}
				}
				
				// Deleted Node
				if (SchoolConstant.DELETED_NODE != null) {
					schoolPOM.getInnerEnterSearch().sendKeys(SchoolConstant.DELETED_NODE);
					Thread.sleep(1000);

					exfPOM.getUpdateResultButton().click();
					Thread.sleep(10000);

					List<WebElement> webElement = schoolPOM.getParentChildList();
					if (!webElement.isEmpty()) {
						Iterator<WebElement> itr = webElement.iterator();
						while (itr.hasNext()) {
							WebElement childStructureElement = itr.next();
							String structureName = childStructureElement.getText();
							if (structureName.contains(SchoolConstant.DELETED_NODE)) {
								dataVFlag = false;
								logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : Deleted Node found using Enter search terms field");
							} else {
								continue;
							}
						}
						schoolPOM.getInnerEnterSearch().clear();
					}
				}
				
				// state num verification
				if (SchoolConstant.STATE_NUM_DESC != null) {
					schoolPOM.getInnerEnterSearch().sendKeys(SchoolConstant.STATE_NUM_DESC);
					Thread.sleep(1000);

					exfPOM.getUpdateResultButton().click();
					Thread.sleep(10000);

					List<WebElement> webElement = schoolPOM.getParentChildList();
					if (!webElement.isEmpty()) {
						Iterator<WebElement> itr = webElement.iterator();
						while (itr.hasNext()) {
							WebElement childStructureElement = itr.next();
							String structureName = childStructureElement.getText();
							if (structureName.contains(SchoolConstant.STATE_NUM_DESC)) {
								continue;
							} else {
								dataVFlag = false;
								logger.log(LogStatus.FAIL, "Curriculum Standard Re-ingestion : State Num does not found using Enter search terms field");
							}
						}
						schoolPOM.getInnerEnterSearch().clear();
					}
				}
				
				if (dataVFlag) {
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_02_RE_INGEST_VERIFY);
					logger.log(LogStatus.PASS, TestCases.TC_LOMT_947_03_RE_INGEST_VERIFYDATA);
				}
				
			} else {
				logger.log(LogStatus.FAIL, "Unable to open Curriculum Standard Goalframework on UI : data verification failed");
				logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_02_RE_INGEST_VERIFY);
				logger.log(LogStatus.FAIL, TestCases.TC_LOMT_947_03_RE_INGEST_VERIFYDATA);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
