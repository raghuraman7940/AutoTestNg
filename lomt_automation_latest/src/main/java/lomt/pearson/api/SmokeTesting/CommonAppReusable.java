package lomt.pearson.api.SmokeTesting;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import lomt.pearson.api.externalframework.ReadExternalFrameworkFile;
import lomt.pearson.common.BaseClass;
import lomt.pearson.common.LoadPropertiesFile;
import lomt.pearson.common.ValidationCheck;
import lomt.pearson.common.reusablecomponents;
import lomt.pearson.constant.HEConstant;
import lomt.pearson.constant.LOMTConstant;
import lomt.pearson.page_object.CommonPOM;
import lomt.pearson.page_object.EnglishPOM;
import lomt.pearson.page_object.ExternalFrameworkPOM;
import lomt.pearson.page_object.HEPom;
import lomt.pearson.page_object.IntermediaryPOM;
import lomt.pearson.page_object.Login;
import lomt.pearson.page_object.SchoolPOM;

public class CommonAppReusable extends BaseClass {
	
	private String environment = LoadPropertiesFile.getPropertiesValues(LOMTConstant.LOMT_ENVIRONMENT);
	public static String userName = LoadPropertiesFile.getPropertiesValues(LOMTConstant.USER_NAME); //PPE user name
	//private String userName = LoadPropertiesFile.getPropertiesValues(LOMTConstant.USER_NAME_TEST);
	public static String pwd = LoadPropertiesFile.getPropertiesValues(LOMTConstant.PASSWORD);
	
	String currentLOB = null;

	protected static WebDriver driver;
	protected static Login login = null;
	protected static CommonPOM commonPOM = null;
	protected static reusablecomponents rc;
	protected static EnglishPOM englishPOM = null;
	protected static HEPom hePom = null;
	protected static IntermediaryPOM intermPOM = null;
	private ExternalFrameworkPOM exfPOM = null;
	protected static SchoolPOM schoolPOM = null;
	private ValidationCheck validationCheck = null;
	protected static EnglishPOM EnglishPOM = null;
	
	
	private static int startYear = 1400;
	private static int endYear = 1500;
	String updateGoalframework = null;
	
	public void getDriverInstance(WebDriver driver) {
		this.driver = initialiseChromeDriver();
	}
	
	public void killBrowsers() {
		this.driver.quit();
	}
	
	public WebDriver getDriverInfo(){
		return this.driver;
	}
	
	public boolean openBrowser(ExtentTest test) {
		boolean flag=false;
		try{
		getDriverInstance(driver);
		driver.manage().window().maximize();
		driver.get(environment);
		driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.PASS, "LOMT Application Launched successfully");
		login = new Login(driver);
		commonPOM = new CommonPOM(driver);
		englishPOM = new EnglishPOM(driver);
		hePom = new HEPom(driver);
		exfPOM = new ExternalFrameworkPOM(driver);
		schoolPOM = new SchoolPOM(driver);
		intermPOM = new IntermediaryPOM(driver);
		englishPOM = new EnglishPOM(driver);
		
		rc=new reusablecomponents();
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "LAUNCH :LOMT Application Failure");
		}
		return flag;
	}
	//Login into LOMT
//	public boolean login(String UserName, String Password,ExtentTest test) {
//		boolean flag = false;
//		try {
//			
//			rc.CreateScenarioHeader("Login into LOMT", test);
//			login = new Login(driver);
//
//			if (rc.SetText(driver, login.getUserName(), "UserName", UserName, test)
//					&& rc.SetText(driver, login.getPassword(), "Password", Password, test)
//					&& rc.click(driver, login.getLoginButton(), "Login button", test)) {
//				waitforLoadIcon(test);
//				if(rc.VerifyObjectPresent(driver, commonPOM.getLblWelcomeTitle(), "Home Page", test))
//				{
//				flag = true;
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			// add logger
//		}
//		return flag;
//	}
//	
//	public boolean logout(ExtentTest test){
//		boolean flag = false;
//		try{
//			rc.CreateScenarioHeader("Logout from LOMT", test);
//			driver.findElement(By.xpath("//span[text()='LOMT ADMIN']")).click();
//			Thread.sleep(2000);
//			driver.findElement(By.className("logout-btn")).click();
//			flag = true;
//		}catch(Exception e){
//			e.printStackTrace();
//			flag = false;
//		}
//		return flag;
//	}

//	//To select LOB
	public boolean selectLOB(String LobLink,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("select LOB - "+LobLink, test);
			flag =rc.click(driver, returnElement(By.linkText(LobLink)), LobLink+" Link", test);
			waitforLoadIcon(test);
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	// Used inside Browsedata method.
	public boolean Browse(String ApplyFilters, ExtentTest test)
	{
		boolean flag = true;
		try {
			String[] Filtervalues = ApplyFilters.split("/");
			List<WebElement> FilterItems = new ArrayList<WebElement>();
			List<WebElement> FilterInputs = new ArrayList<WebElement>();
			FilterItems = driver.findElements(By.className("Select-control"));
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			for (int i = 0; i < Filtervalues.length; i++) {
				try {
					WebElement objclear;
					try {
						objclear = FilterItems.get(i).findElement(By.className("Select-clear"));
					} catch (Exception e) {
						objclear = null;
					}
					if (!Filtervalues[i].isEmpty() && FilterItems.get(i).isEnabled() && objclear == null) {
						FilterItems.get(i).click();
						FilterItems.get(i).findElement(By.tagName("input")).sendKeys(Filtervalues[i]);
						Actions action = new Actions(driver);
						action.sendKeys(Keys.ENTER).build().perform();
						Thread.sleep(1000);
						test.log(LogStatus.PASS, "Applied the Filter  - "+Filtervalues[i]);
					}
				} catch (Exception e1) {
					// e1.printStackTrace();
					flag = false;
					test.log(LogStatus.FAIL, "Problem is Applying the filter - "+Filtervalues[i]
							+ test.addScreenCapture(rc.getScreenhot(driver, "Apply Filter")));
				}
			}
		} catch (Exception e) {
			try {
				test.log(LogStatus.FAIL, "Problem is Applying the filter "
						+ test.addScreenCapture(rc.getScreenhot(driver, "Apply Filter")));
				Thread.sleep(1000);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return flag;
	}
	
	//To search Goal framework with/without filters
	public boolean BrowseData(String SearchText,String ApplyFilter,ExtentTest test) {
		boolean flag=false;
		try {
			if(rc.IsElementEnabled(driver, commonPOM.clearFilter, true, test, "Clear Filter link")){
				if(rc.clickOnInvisible(driver, commonPOM.clearFilter, "Clear filter", test)){
					waitforLoadIcon(test);
					flag = true;
				}else {
					flag= false;
				}
			}
			
			
			
			rc.CreateScenarioHeader("Browse Data "+SearchText, test);
			if(!SearchText.isEmpty()){
			rc.SetText(driver, driver.findElement(By.className("form-control")), "Search Terms TextBox",SearchText , test);
		}
			if(!ApplyFilter.isEmpty())
			{
			Browse(ApplyFilter,test);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			}
			if(rc.click(driver,commonPOM.get_btn_UpdateResults(), "Update Results button", test))
			{
				waitforLoadIcon(test);
				flag=rc.VerifyObjectPresent(driver, returnElement(By.partialLinkText(SearchText)), "Search Result - "+SearchText, test);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//To select structure
	public boolean SelectStructure(String Structure,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Select Structure "+Structure, test);
			if(rc.click(driver, returnElement(By.linkText(Structure)), Structure +" Link", test))
			{
				waitforLoadIcon(test);
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	// To select the modules from the browse grid
	public boolean SelectModule(String ModuleName,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Select Module "+ModuleName, test);
			if(rc.clickOnInvisible(driver, returnElement(By.xpath("//span[contains(text(),'"+ModuleName+"')]")),  ModuleName+" Link", test))
			{
				waitforLoadIcon(test);
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}

/////////////////////////////////////SET////////////////////////////////////
	//To create a set
	public boolean CreateSet(String SetName,String SetDesc,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Create Set - "+SetName, test);
			if(rc.click(driver, commonPOM.get_btn_CreateNewSet(), "Create New Set Button", test))
			{
			if(rc.SetText(driver, commonPOM.get_txt_Setname(), "Set Name field", SetName, test)&&
			rc.SetText(driver, commonPOM.get_txt_Setdescription(), "Set description field", SetDesc, test)&&
			rc.click(driver, commonPOM.get_btn_save(), "Save button", test))
			{
			waitforLoadIcon(test);
				flag=true;
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	//To verify the created set
	public boolean VerifyCreatedSet(String SetName,ExtentTest test) {
		boolean flag=false;
		
		try {
			rc.CreateScenarioHeader("Verify Created Set - "+SetName, test);
			if(rc.SetText(driver, commonPOM.get_txt_SearchBox(), "Search box field", SetName, test)&&
			rc.click(driver, commonPOM.get_btn_Search(), "Search button", test))
			{
			waitforLoadIcon(test);
			if(rc.VerifyObjectPresent(driver,returnElement(By.xpath("//span[contains(text(),'"+SetName+"')]")),SetName, test))
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	//To add structure to the set
	public boolean AddStructureToset(String SetName,String Structure,String searchGoalFramework,String Applyfilter,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Add "+searchGoalFramework+" Structure To set "+SetName, test);
			Thread.sleep(3000);
			if(rc.SetText(driver, commonPOM.get_txt_SearchBox(), "Search box field", SetName, test)&&
			rc.click(driver, commonPOM.get_btn_Search(), "Search button", test))
			{
			waitforLoadIcon(test);
			if(rc.VerifyObjectPresent(driver,returnElement(By.xpath("//span[contains(text(),'"+SetName+"')]")),SetName, test))
				if(rc.click(driver, commonPOM.get_btn_Actions(), "Actions button", test)){
					if(rc.click(driver, commonPOM.get_btn_ADDstructures(), "ADD structures To the Set button", test))
					{
						if(rc.click(driver, returnElement(By.linkText(Structure)), "ADD structures To the Set button", test))
						{
							waitforLoadIcon(test);
							if(BrowseData(searchGoalFramework,Applyfilter,test))
							{
								if(rc.click(driver, commonPOM.get_btn_AddToSet(), "ADD To Set button", test))
								{
									if(rc.click(driver, commonPOM.get_btn_Proceed(), "Proceed button", test))
									{
									waitforLoadIcon(test);
									flag=true;
									}
								}
							}
						}
					}
				}
				
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	//To verify the added structure to the set
	public boolean VerifyaddStructureToset(String LOB,String Modulename,String SetName,String searchGoalFramework,String Applyfilter,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Verify add Structure To set "+SetName, test);
			if(rc.click(driver,commonPOM.getPearsonLogo(), "Home Logo", test))
			{
				if(selectLOB(LOB, test))
				{
					if(SelectModule(Modulename, test))
					{
			if(rc.SetText(driver, commonPOM.get_txt_SearchBox(), "Search box field", SetName, test)&&
			rc.click(driver, commonPOM.get_btn_Search(), "Search button", test))
			{
			waitforLoadIcon(test);
			
			if(rc.VerifyObjectPresent(driver,returnElement(By.xpath("//span[contains(text(),'"+SetName+"')]")),SetName, test))
				if(rc.click(driver, commonPOM.get_btn_Actions(), "Actions button", test)){
					
						if(rc.click(driver, commonPOM.get_btn_VIEWstructuresinthisset(), "VIEW structures in this set button", test))
						{
							waitforLoadIcon(test);
									if(rc.VerifyObjectPresent(driver, returnElement(By.partialLinkText(searchGoalFramework)), searchGoalFramework, test))
									{
									flag=true;
									}
						}
					}
			}
			}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	//To remove the structure from the set.
	public boolean RemoveStructureFromset(String LOB,String Modulename,String SetName,String searchGoalFramework,String Applyfilter,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Remove Structure From set"+SetName, test);
			if(rc.click(driver,commonPOM.getPearsonLogo(), "Home Logo", test))
			{
				if(selectLOB(LOB, test))
				{
					if(SelectModule(Modulename, test))
					{
			if(rc.SetText(driver, commonPOM.get_txt_SearchBox(), "Search box field", SetName, test)&&
			rc.click(driver, commonPOM.get_btn_Search(), "Search button", test))
			{
			waitforLoadIcon(test);
			
			if(rc.VerifyObjectPresent(driver,returnElement(By.xpath("//span[contains(text(),'"+SetName+"')]")),SetName, test))
				if(rc.click(driver, commonPOM.get_btn_Actions(), "Actions button", test)){
					
						if(rc.click(driver, commonPOM.get_btn_REMOVEstructuresfromthisset(), "REMOVE structures from this set button", test))
						{
							waitforLoadIcon(test);
									if(rc.VerifyObjectPresent(driver, returnElement(By.partialLinkText(searchGoalFramework)), searchGoalFramework, test))
									{
										if(rc.click(driver, commonPOM.get_btn_RemovefromSet(), "Remove from Set button", test))
										{
											if(rc.click(driver, commonPOM.get_btn_Proceed(), "Proceed button", test))
											{
											waitforLoadIcon(test);
											flag=true;
											}
										}
										
										
									}
						}
					}
			}
			}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	
	public boolean VerifyDeletedStructureToset(String LOB,String Modulename,String SetName,String searchGoalFramework,String Applyfilter,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Verify Deleted Structure From set "+SetName, test);
			if(rc.click(driver,commonPOM.getPearsonLogo(), "Home Logo", test))
			{
				if(selectLOB(LOB, test))
				{
					if(SelectModule(Modulename, test))
					{
			if(rc.SetText(driver, commonPOM.get_txt_SearchBox(), "Search box field", SetName, test)&&
			rc.click(driver, commonPOM.get_btn_Search(), "Search button", test))
			{
			waitforLoadIcon(test);
			
			if(rc.VerifyObjectPresent(driver,returnElement(By.xpath("//span[contains(text(),'"+SetName+"')]")),SetName, test))
				if(rc.click(driver, commonPOM.get_btn_Actions(), "Actions button", test)){
					
						if(rc.click(driver, commonPOM.get_btn_VIEWstructuresinthisset(), "VIEW structures in this set button", test))
						{
							waitforLoadIcon(test);
									if(rc.VerifyObjectNOTPresent(driver, returnElement(By.partialLinkText(searchGoalFramework)), searchGoalFramework, test))
									{
									flag=true;
									}
						}
					}
			}
			}
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
/////////////////////////////////////SET////////////////////////////////////
	//To return webelement
	public WebElement returnElement(By by) {
		try {
			if (driver.findElement(by).isDisplayed())
				return driver.findElement(by);
			else
				return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	//To wait for the Load scroller.
	public boolean waitforLoadIcon(ExtentTest test) {
		boolean flag=false;
		try {
			for(int i=0;i<180;i++)
			{
				if(rc.IsElementPresent(driver, commonPOM.getLoadingicon()))
				{
					Thread.sleep(1000);
				}
				else
				{
					Thread.sleep(3000);
					flag=true;
					break;
				}
			}
			
		} catch (Exception e) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				System.out.println("Loader expection");
				e1.printStackTrace();
			}
			e.printStackTrace();
			// add logger
		}
		
		return flag;
	}

	//To move back in the browser
	public boolean BrowserBack(ExtentTest test) {
		boolean flag = false;
		try {
			driver.navigate().back();
			waitforLoadIcon(test);
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	//To close the driver instance
	public void closeDriverInstance() {
		driver.close();
	}
	
//	public boolean verifyAllLobsClickable(ExtentTest test){
//		boolean flag = false;
//		if(
//		rc.IsElementEnabled(driver, commonPOM.getEnglishLOB(), true, test, "English LOB")&&
//		rc.IsElementEnabled(driver,commonPOM.getSchoolGlobalLOB(), true, test,"School Global")&&
//		rc.IsElementEnabled(driver,commonPOM.getHeLOB(), true, test,"Higher Education")){
//			flag = true;		
//		}else{
//			flag = false;
//			}
//		return flag;
//	}
	
	
	public boolean uploadFile(String filePath, ExtentTest test){
		boolean flag = false;
		try{
			if(rc.click(driver, commonPOM.uploadFileLink, "Upload File link", test)){
				Thread.sleep(5000);
				String[] cmd = new String[2];
                cmd[0] = LOMTConstant.INGESTION_FILE_PATH;
                cmd[1] = filePath;
                Runtime.getRuntime().exec(cmd);
                Thread.sleep(4000);
                if(commonPOM.selectedFileName.getText().contains(filePath)){                	
                if(rc.click(driver, commonPOM.nextButton, "Next Button", test)){
                	waitforLoadIcon(test);
                	if(rc.VerifyObjectPresent(driver, commonPOM.ingestSucessful, "Ingest Successful", test)){
                		flag = true;
                	}
                }
                }
                

			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//Validation of Unsucessful message in Ingestion
	public boolean uploadIncorrectFile(String filePath, ExtentTest test){
		boolean flag = false;
		try{
			if(rc.click(driver, commonPOM.uploadFileLink, "Upload File link", test)){
				Thread.sleep(7000);
				String[] cmd = new String[2];
                cmd[0] = LOMTConstant.INGESTION_FILE_PATH;
                cmd[1] = filePath;
                Runtime.getRuntime().exec(cmd);
                Thread.sleep(5000);
                if(commonPOM.selectedFileName.getText().contains(filePath)){                	
                if(rc.click(driver, commonPOM.nextButton, "Next Button", test)){
                	waitforLoadIcon(test);
                	if(rc.VerifyObjectPresent(driver, commonPOM.Ingestfailed, "Ingest failed", test)){
                		flag = true;
                	}
                }
                }
                

			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public boolean clickLOMTLogo(ExtentTest test){
		boolean flag =false;
		try{
			flag = rc.click(driver, commonPOM.pearsonLogo, "LOMT Logo", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	// Update Excel value as unique before ingestion
	public static String updateExcelValue(String filePath, String sheetName,int rowValue, int colValue, String valueUpdated, ExtentTest test){
	      try{
	    	  
	      String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
	      String fileSeparator = System.getProperty("file.separator");
	    filePath = "src/main/java/lomt/pearson/fileupload/Ingestion/Files/"+filePath;     
	      
	      FileInputStream file = new FileInputStream(new File (relativePath+fileSeparator+filePath));
	      XSSFWorkbook workbook = new XSSFWorkbook(file);
	      XSSFSheet sheet = workbook.getSheet(sheetName);
	      Row row = sheet.getRow(rowValue);
	
	      valueUpdated = valueUpdated.concat(String.valueOf(System.currentTimeMillis()));
	      row.getCell(colValue).setCellValue(valueUpdated);
	      file.close();

	      
	      FileOutputStream fileOutput = new FileOutputStream(new File(relativePath+fileSeparator+filePath));
	      workbook.write(fileOutput);
	      fileOutput.close();
	      test.log(LogStatus.PASS, "Excel Value has been updated sucessfully");
	      }catch(FileNotFoundException e){
	            e.printStackTrace();
	            test.log(LogStatus.FAIL, "Excel Value has not been updated");
	            return null;
	      }catch(Exception e){
	            e.printStackTrace();
	            test.log(LogStatus.FAIL, "Excel Value has not been updated");
	            return null;
	      }
	      return valueUpdated;
	}
	//Clear the filter in Browse page
	public boolean clearFilter(ExtentTest test){
		boolean flag =false;
		try{
			flag = rc.click(driver, commonPOM.clearFilter, "Clear Filter", test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	//Click Action button
	public boolean clickActions(ExtentTest test){
		boolean flag =false;
		try{
			flag = rc.click(driver, commonPOM.get_btn_Actions(), "Action Button", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	// Click Browse button
	public boolean clickBrowse(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, commonPOM.btnBrowse, "Browse Button", test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	//Click Action Button Inside Intermediary Accounting
	public boolean clickActionIntermediaryAccounting(ExtentTest test){
		boolean flag =false;
		try{
			flag = rc.click(driver, commonPOM.actionIntermediaryAction_Accounting(), "Action Button Intermediary Accounting", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//Clicking Export Button
	public boolean exportClick(ExtentTest test){
		boolean flag =false;
		try{
			flag = rc.click(driver, commonPOM.getExportButton(), "Export Button", test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	//Clicking Intermediary in Back Button
		public boolean clickIntermediariesback(ExtentTest test){
			boolean flag =false;
			try{
				flag = rc.click(driver, commonPOM.clickIntermediariesBack(), "Intermediary Button Back", test);
				waitforLoadIcon(test);
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			return flag;
		}
		
//Clicking Edit button inside node
		public boolean clickEdit(ExtentTest test){
			boolean flag =false;
			try{
				flag = rc.click(driver, commonPOM.clickEdit(), "Click Edit", test);
				waitforLoadIcon(test);
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			return flag;
		}
		
// updating MSS id for ab.xml file
		public boolean updateXMLFile(ExtentTest test, String fileName, String replacableValue){
			boolean flag = false;
			try{				
				String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
			    String fileSeparator = System.getProperty("file.separator");
			    fileName = "src/main/java/lomt/pearson/fileupload/Ingestion/Files/"+fileName;
			  	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				Document doc = docBuilder.parse(new File(relativePath+fileSeparator+fileName));
				Node Mss = doc.getElementsByTagName("MSS").item(0);
				NamedNodeMap attr = Mss.getAttributes();
				Node nodeAttr = attr.getNamedItem("id");
				String[] getOldValue = nodeAttr.getTextContent().split("-");
				getOldValue[0]=replacableValue;
				String newValue = "";
				for(String mssId : getOldValue){
					newValue = newValue.concat(mssId.concat("-")).trim();
				}
				
				System.out.println("Existing value "+nodeAttr.getTextContent());
				System.out.println("New value "+newValue.substring(0, newValue.length()-1));
				
				nodeAttr.setTextContent(newValue.substring(0, newValue.length()-1));
				
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(relativePath+fileSeparator+fileName));
				transformer.transform(source, result);
				flag = true;
				test.log(LogStatus.PASS, "MSS id is updated with new value "+newValue.substring(0, newValue.length()-1));
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
				test.log(LogStatus.FAIL, "MSS id was not updated");
			}
			return flag;
		}
		
		//getting MSS id value from xml file
		public String getMssId(ExtentTest test, String fileName){
			String mssValue = null;
			try{
			String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		    String fileSeparator = System.getProperty("file.separator");
		    fileName = "src/main/java/lomt/pearson/fileupload/Ingestion/Files/"+fileName;
		    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(new File(relativePath+fileSeparator+fileName));
			Node Mss = doc.getElementsByTagName("MSS").item(0);
			NamedNodeMap attr = Mss.getAttributes();
			Node nodeAttr = attr.getNamedItem("id");
			mssValue= nodeAttr.getTextContent().split("-")[0];
			}catch(Exception e){
				e.printStackTrace();
			}
			return mssValue;
		}
		public boolean expandCollapseParentCurriculumNode(boolean expand, ExtentTest test){
					int nodesCount =0;
					boolean flag = false;
					List<WebElement> parentNodes;	
					
					try{
					if(expand){
						 parentNodes = schoolPOM.parentNodeExpanded;
						 System.out.println(parentNodes.size());
					
						for(int i=0;i<parentNodes.size();i++){
							nodesCount = i+1;
							flag = rc.clickOnInvisible(driver, parentNodes.get(i).findElement(By.xpath("//span[contains(@class,'rc-tree-switcher_close')]")), "Expand Node "+nodesCount, test);
							waitforobject(returnElement(By.xpath("//span[contains(@class,'rc-tree-icon_loading')]")),90, test);
					}
					}else{
						parentNodes = schoolPOM.parentNodeCollapse;
						
					for(int i=parentNodes.size()-1;i>=0;i--){
						nodesCount = i+1;
						flag = rc.clickOnInvisible(driver, parentNodes.get(i), "Collapse Node "+nodesCount, test);
					}
					}
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					
					return flag;
				}
	
				public String getValuesExcel(String filePath,String sheetName,int rowValue,int colValue,ExtentTest test){
					String valueFromExcel = null;
					try{
					   String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
					      String fileSeparator = System.getProperty("file.separator");
					      FileInputStream file = new FileInputStream(new File (relativePath+fileSeparator+filePath));
					      XSSFWorkbook workbook = new XSSFWorkbook(file);
					      XSSFSheet sheet = workbook.getSheet(sheetName);
					      Row row = sheet.getRow(rowValue);
					      valueFromExcel = row.getCell(colValue).getStringCellValue();
					      test.log(LogStatus.PASS, "The value from Excel is"+valueFromExcel);
					}catch(Exception e){
						e.printStackTrace();
						test.log(LogStatus.FAIL, "Value was not fetched from excel");
					}
					     return valueFromExcel; 
				}
				public void removeExistingFile() throws IOException {
					if (new File(LOMTConstant.EXPORTED_FILE_PATH).exists()) 
						FileUtils.cleanDirectory(new File(LOMTConstant.EXPORTED_FILE_PATH));
				}
				
				// Update Excel value as unique from downloads for reingestion
				public static String updateExcelValueFromDownload(String filePath, String sheetName,int rowValue, int colValue, String valueUpdated, ExtentTest test){
				      try{				    	  
				      String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
				      String fileSeparator = System.getProperty("file.separator");
				filePath = "src/main/java/lomt/pearson/downloads/Files/"+filePath;				   
			   FileInputStream file = new FileInputStream(new File (relativePath+fileSeparator+filePath));				 
				      XSSFWorkbook workbook = new XSSFWorkbook(file);
				      XSSFSheet sheet = workbook.getSheet(sheetName);
				      Row row = sheet.getRow(rowValue);			
				      valueUpdated = valueUpdated.concat(String.valueOf(System.currentTimeMillis()));
				      row.getCell(colValue).setCellValue(valueUpdated);
				      file.close(); 
				      FileOutputStream fileOutput = new FileOutputStream(new File(relativePath+fileSeparator+filePath));
				      workbook.write(fileOutput);
				      fileOutput.close();
				      test.log(LogStatus.PASS, "Excel Value has been updated sucessfully");
				      }catch(FileNotFoundException e){
				            e.printStackTrace();
				            test.log(LogStatus.FAIL, "Excel Value has not been updated");
				            return null;
				      }catch(Exception e){
				            e.printStackTrace();
				            test.log(LogStatus.FAIL, "Excel Value has not been updated");
				            return null;
				      }
				      return valueUpdated;
				}
				
				// Uploading Updated Excel File from Download				
				public boolean uploadFileFromDownloadAfterExcelUpdate(String filePath, ExtentTest test){
									boolean flag = false;
									try{
										if(rc.click(driver, commonPOM.uploadFileLink, "Upload File link", test)){
											Thread.sleep(5000);
											String[] cmd = new String[2];
							                cmd[0] = LOMTConstant.INGESTION_FILE_PATH_DOWNLOAD1;
							                cmd[1] = filePath;
							                Runtime.getRuntime().exec(cmd);
							                Thread.sleep(4000);
							                if(commonPOM.selectedFileName.getText().contains(filePath)){                	
							                if(rc.click(driver, commonPOM.nextButton, "Next Button", test)){
							                	waitforLoadIcon(test); 
				Thread.sleep(20000);
							                	if(rc.VerifyObjectPresent(driver, commonPOM.ingestSucessful, "Ingest Successful", test)){
							                		flag = true;
							                	}
							                }
							                }
							         }
									}catch(Exception e){
										e.printStackTrace();
										flag = false;
									}
									return flag;
								}	
				
				//Clicking Edit button inside node in HE
				public boolean clickEditinHE(ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver, commonPOM.clickEditinHE, "Click Edit", test);
						waitforLoadIcon(test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				} 
				
				//To search Goal framework with/without filters
				public boolean BrowseDataHEProduct(String SearchText,String ApplyFilter,ExtentTest test) {
					boolean flag=false;
					try {
						rc.CreateScenarioHeader("Browse Data "+SearchText, test);
						if(!SearchText.isEmpty()){
						rc.SetText(driver, driver.findElement(By.className("form-control")), "Search Terms TextBox",SearchText , test);
					}
						if(!ApplyFilter.isEmpty())
						{
						Browse(ApplyFilter,test);
						driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
						}
						if(rc.click(driver,commonPOM.get_btn_UpdateResults(), "Update Results button", test))
						{
							waitforLoadIcon(test);
							flag=rc.VerifyObjectPresent(driver, returnElement(By.partialLinkText("Feldman_Product_TOC_25Feb-1")), "Search Result - "+"Feldman_Product_TOC_25Feb-1", test);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					return flag;
				}	
				
				//Clicking Edit button inside node in HE Product
				public boolean clickEditinHEProduct(ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver, commonPOM.clickEditinHE, "Click Edit", test);
						waitforLoadIcon(test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
				
				public boolean VerifyRecord(int itemNo,ExtentTest test){
					return verifySearchRecord(itemNo, test);
							
				}
				
				
				public boolean verifySearchRecord(int itemNo, ExtentTest test) {
					boolean flag=false;
					try{
						List<WebElement> records =driver.findElements(By.className("list-link-box"));
						for (int i=0;i<records.size();i++) {
							if((i+1)==itemNo)
							{
								flag=rc.VerifyObjectPresent(driver,records.get(i) , "Verify the "+itemNo+"record", test);
								break;
							}
						}
						
					}
					catch(Exception e)
					{
						
					}
					return flag;
				}
				
				public String ReturnRecord(int itemNo,ExtentTest test){
					return GetSearchRecord(itemNo, test);
							
				}
				
				public String GetSearchRecord(int itemNo, ExtentTest test) {
					String text=null;
					try{
						List<WebElement> records =driver.findElements(By.className("list-link-box"));
						for (int i=0;i<records.size();i++) {
							if((i+1)==itemNo)
							{
								text = rc.getObjectValue(driver,records.get(i) , "Verify the "+itemNo+"record", test);				
								break;
							}
						}
						
					}
					catch(Exception e)
					{
						
					}
					return text;
				}
				
				//Click Align button
				public boolean clickAlign(ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver,commonPOM.btn_Align , "Align Button", test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
				public boolean ClickSelectforAligment(ExtentTest test){
					return clickSelectForAlignment(test);
							
				}
				
				public boolean clickSelectForAlignment(ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver,commonPOM.btn_selectforAlignment , "select for Alignment link", test);
						waitforLoadIcon(test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
				
				//To select the child and parent Educational goal
				public boolean SelectEducationalGoals(ExtentTest test) {
					boolean flag=false;
					try{
						rc.CreateScenarioHeader("Select Nodes From Source A and Source B", test);
						List<WebElement> Sources =driver.findElements(By.className("source-tree-structure"));
						for (WebElement webElement : Sources) {		
							List<WebElement> ParentNodes =webElement.findElements(By.className("root-node"));
							for(int i=0;i<ParentNodes.size();i++)
							{
								rc.clickOnInvisible(driver, ParentNodes.get(i).findElement(By.tagName("span")), "Expand icon", test);
									waitforobject(returnElement(By.xpath("//span[contains(@class,'rc-tree-icon_loading')]")),90, test);
									List<WebElement> ChildNodes = ParentNodes.get(i).findElements(By.className("child-node"));
									if(ChildNodes.size()>0){
									for(int j=0;j<ChildNodes.size();j++)
									{
										flag=false;
										if(rc.clickOnInvisible(driver, ChildNodes.get(j).findElement(By.className("rc-tree-checkbox")), "Node", test))
										{
											flag=true;
											break;
										}
									}
									
									break;
									}
									else
									{
										flag=false;
										if(rc.clickOnInvisible(driver, ParentNodes.get(i).findElement(By.className("rc-tree-checkbox")), "Node", test))
										{
										flag=true;
										break;
										}
									}
								
						}
							}	
						if(flag){
						test.log(LogStatus.PASS,"Selected the Nodes");
						}
						else
						{
							test.log(LogStatus.FAIL, "Problem in selecting the Nodes"+test.addScreenCapture(rc.getScreenhot(driver, "Aligment")));
							
						}
					}
					catch(Exception e)
					{
						flag=false;
						try {
							test.log(LogStatus.FAIL, "Problem in selecting the Nodes"+test.addScreenCapture(rc.getScreenhot(driver, "Aligment")));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					return flag;
				}
				
				
				//To wait for the Load scroller.
				public boolean waitforobject(WebElement obj,int seconds,ExtentTest test) {
					boolean flag=false;
					try {
						for(int i=1;i<seconds;i++)
						{
							if(rc.IsElementPresent(driver, obj))
							{
								Thread.sleep(1000);
							}
							else
							{
								Thread.sleep(3000);
								flag=true;
								break;
							}
						}
						
					} catch (Exception e) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						e.printStackTrace();
						// add logger
					}
					
					return flag;
				}
				
				
				
				public boolean clickAlignAction(ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver, commonPOM.btn_AlignAction, "Action Button", test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
	
				public boolean clickAlignType(String AlignType,ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver, returnElement(By.xpath("//button[contains(text(),'"+AlignType+"')]")), AlignType+" Button", test);
						waitforLoadIcon(test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
				public boolean VerifyAlignmentInformation(String SourceA,String SourceB,String AlignType,ExtentTest test){
					boolean flag =false;
					try{
						rc.CreateScenarioHeader("Verify the Aligment Details", test);
					 if(rc.compareElementText(driver,returnElement(By.xpath("//div[@class='sourceA']/p[2]/strong")), "Source A", SourceA, test) && 
							 rc.compareElementText(driver,returnElement(By.xpath("//div[@class='sourceB']/p[2]/strong")), "Source B", SourceB, test) &&
							 rc.compareElementText(driver,returnElement(By.xpath("//div[@class='middle']/div")), "AlignType", AlignType, test))
					 {
						 flag=true;
					 }
					}
					catch(Exception e)
					{
						
					}
					return flag;
							
				}
				
				public boolean EditAlignment(String AlignType, ExtentTest test) {
					boolean flag = false;
					try {
						rc.CreateScenarioHeader("Edit Aligment", test);
						if (rc.clickOnInvisible(driver, returnElement(By.xpath("//div[@class='existing-alignment-row']//img[contains(@src,'Edit')]")), "Edit icon", test)) {
							if (rc.SetText(driver, commonPOM.AligmentTextbox, "AlignType Text Box", AlignType, test)) {
								Actions action = new Actions(driver);
								action.sendKeys(Keys.ENTER).build().perform();
								Thread.sleep(1000);
								if (rc.clickOnInvisible(driver, commonPOM.btn_save, "Save button", test)) {
									waitforLoadIcon(test);
									rc.CreateScenarioHeader("Verify the Edited Alignment Type", test);
									if (rc.compareContainsText(driver, returnElement(By.xpath("//div[@class='existing-alignment-row']")), "Note Section",AlignType.toUpperCase() , test)) {
										flag = true;
									}

								}
							}

							
						}
					} catch (Exception e) {

					}
					return flag;

				}
				
				public boolean AddNote(ExtentTest test) {
					boolean flag = false;
					try {
						rc.CreateScenarioHeader("Add Align Note", test);
						if (rc.clickOnInvisible(driver, commonPOM.Lnk_AddNote, "Add Note", test)) {
							String Notes = "Note - " + System.currentTimeMillis();
							if (rc.SetText(driver, commonPOM.Notes, "Notes", Notes, test)) {
								if (rc.clickOnInvisible(driver, commonPOM.btn_AddNote, "Add Note button", test)) {
									waitforLoadIcon(test);
									rc.CreateScenarioHeader("Verify the created Align Note", test);
									if (rc.compareContainsText(driver, commonPOM.AlignNote_Section, "Note Section", Notes, test)) {
										flag = true;
									}

								}
							}

							
						}
					} catch (Exception e) {

					}
					return flag;

				}
				//To search Goal framework with/without filters
				public boolean BrowseDataHEExtermalFramework(String SearchText,String ApplyFilter,ExtentTest test) {
					boolean flag=false;
					try {
						rc.CreateScenarioHeader("Browse Data "+SearchText, test);
						if(!SearchText.isEmpty()){
						rc.SetText(driver, driver.findElement(By.className("form-control")), "Search Terms TextBox",SearchText , test);
					}
						if(!ApplyFilter.isEmpty())
						{
						Browse(ApplyFilter,test);
						driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
						}
						if(rc.click(driver,commonPOM.get_btn_UpdateResults(), "Update Results button", test))
						{
							waitforLoadIcon(test);
							flag=rc.VerifyObjectPresent(driver, returnElement(By.partialLinkText("Demo for LOMT EF")), "Search Result - "+"Feldman_Product_TOC_25Feb-1", test);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					return flag;
				}
				
				public boolean exportActions(ExtentTest test){
					boolean flag = false;
					try{
						removeExistingFile();
						flag = rc.clickOnInvisible(driver, commonPOM.btnExportActions, "Export button", test);
						waitforLoadIcon(test);
					}catch(Exception e){
						e.printStackTrace();
					}
					return flag;
				}
				
				public boolean SelectRadio(String name,ExtentTest test)
				{
				boolean flag =false;
				try{
					flag = rc.click(driver, returnElement(By.xpath("//input[@name='"+name+"']")), "Radio - "+name, test);
				}catch(Exception e){
					e.printStackTrace();
					flag = false;
				}
				return flag;
			}
				
				public boolean LaunchApp(ExtentTest test) {
					boolean flag=false;
					try {
						rc.CreateScenarioHeader("Launch Application", test);
						driver.get(environment);
						//waitforLoadIcon(test);
						test.log(LogStatus.PASS, "Application Launched - "+environment );
						//waitforLoadIcon(test);
					} catch (Exception e) {
						test.log(LogStatus.FAIL, "Application NOT Launched- " +environment);
						e.printStackTrace();
						// add logger
					}
					return flag;
				}
				
				public boolean verifyVisualCorrelationLinkDisplayed(boolean validateDisplayed,ExtentTest test){
					boolean flag = false;
					try{
						if(validateDisplayed){
							flag=rc.VerifyObjectPresent(driver, schoolPOM.lnkVisualCorrelation, "Visual Correlation link", test);
						}else{
							flag = rc.VerifyObjectNOTPresent(driver, schoolPOM.lnkVisualCorrelation, "Visual Correlation link", test);
						}
						
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
}