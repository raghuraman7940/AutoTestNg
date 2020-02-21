package lomt.pearson.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.Spring;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import lomt.pearson.constant.LOMTConstant;

public class reusablecomponents extends BaseClass{

	//To get the value from Property File
	public static String getPropertiesValues(String value) {
		InputStream input = null;
		String propertiesValue = null;
		Properties properties = new Properties();
		try {
			input = new FileInputStream(LOMTConstant.CONFIGURATION_FILE_PATH);
			properties.load(input);
			propertiesValue = properties.getProperty(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return propertiesValue;
	}
	
	//To Verify the presence of element used inside the Selenium action methods
	public boolean IsElementPresent(WebDriver driver,  WebElement obj)
	{
		boolean flag = false;
		
		try{
			
		if(obj.isDisplayed())
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", obj);
			flag=true;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return flag;
	}
	
	// To verify the presence of element is enabled or not 
	public boolean IsElementEnabled(WebDriver driver,WebElement obj, boolean flag, ExtentTest test, String objName){
		boolean actualFlag = false;
		
		if(flag){
			try{
				JavascriptExecutor js = (JavascriptExecutor) driver;
				if(obj.isEnabled()){
					test.log(LogStatus.PASS, objName+" is Enabled");					
					js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", obj);
					actualFlag=true;
				}
			}catch(Exception e){
				e.printStackTrace();
				actualFlag = false;
			}
		}else{
			try{
				if(!obj.isEnabled()){
					test.log(LogStatus.PASS, objName+" is not Enabled");
					actualFlag = true;
				}
				
			}catch(Exception e){
				e.printStackTrace();
				actualFlag = false;
			}
		}
		return actualFlag;
	}

	//To Perform Click operation on the specified element
	public boolean click(WebDriver driver, WebElement obj, String ObjName, ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				obj.click();
				test.log(LogStatus.PASS, "Clicked on "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: Problem in clicking "+ObjName+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//TO verify the presence of the element in the web page.
	public boolean VerifyObjectPresent(WebDriver driver, WebElement obj, String ObjName, ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				test.log(LogStatus.PASS, "Element is present "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To verify the absence of the element in the web page.
	public boolean VerifyObjectNOTPresent(WebDriver driver, WebElement obj, String ObjName, ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(!IsElementPresent(driver,obj))
			{
				test.log(LogStatus.PASS, "Element is not present "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName+" is present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To perform click operation on the element using Java script executor.
	public boolean clickOnInvisible(WebDriver driver, WebElement obj, String ObjName, ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				JavascriptExecutor executor = (JavascriptExecutor)driver;
				executor.executeScript("arguments[0].click();", obj);
				test.log(LogStatus.PASS, "Clicked on "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: Problem in clicking "+ObjName+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To compare the text value of the element
	public boolean compareElementText(WebDriver driver, WebElement obj, String ObjName, String value,ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				if(obj.getText().equals(value))
				test.log(LogStatus.PASS, ObjName + " value matches "+value);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName + " value does not match "+value+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To compare the TextBox value.
	public boolean comparePropertyValue(WebDriver driver, WebElement obj, String ObjName, String Prop,String value,ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				if(obj.getAttribute(Prop).equals(value))
				test.log(LogStatus.PASS, ObjName + " Property -"+ Prop +"value matches "+value);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName + " Property -"+ Prop +"value does not matches "+value+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To perform sendkeys on text box.
	public boolean SetText(WebDriver driver, WebElement obj, String ObjName, String value,ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				obj.sendKeys(value);
				test.log(LogStatus.PASS, "Entered the text '"+value+"' in "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: Problem in entering the text '"+value+"' in "+ObjName+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To select the list box using visible text
	public boolean SelectbyValue(WebDriver driver, WebElement obj, String ObjName, String value,ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				new Select(obj).selectByVisibleText(value);;
				test.log(LogStatus.PASS, "Selected the text '"+value+"' in "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: Problem in selecting the text '"+value+"' in "+ObjName+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	//To select the list box using Index value
	public boolean SelectbyIndex(WebDriver driver, WebElement obj, String ObjName, int index,ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				new Select(obj).selectByIndex(index);
				Thread.sleep(2000);
				test.log(LogStatus.PASS, "Selected the text '"+new Select(obj).getFirstSelectedOption().getText()+"' in "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: Problem in selecting the option at the index '"+index+"' in "+ObjName+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	
	public boolean CreateScenarioHeader(String Scenario, ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{
				test.log(LogStatus.INFO, "<h3>"+Scenario+"</h3>");
				flag=true;
			
		}
		catch(Exception e)
		{
			
		}
		return flag;
	}
	//To capture screenshot during failures.
	public static String getScreenhot(WebDriver driver, String screenshotName) throws Exception {
		String destination =null;
		try{
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
		destination = LOMTConstant.screenShotPath+"/"+screenshotName+"_"+dateName+".jpeg";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		}
		catch(Exception e)
		{
			
		}
		return destination;
	}
	
	// To verify elements are present
	public boolean VerifyObjectsPresent(WebDriver driver, List<WebElement> obj, String ObjName, ExtentTest test) throws Exception
	{
		WebDriverWait wait = new WebDriverWait(driver,180);
		boolean flag = false;
		try{

			if(wait.until(ExpectedConditions.visibilityOfAllElements(obj)) != null)
			{
				test.log(LogStatus.PASS, "Element is present "+ObjName);
				flag=true;
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	
	public String getObjectValue(WebDriver driver, WebElement obj, String ObjName, ExtentTest test) throws Exception
	{
		String text=null;
		try{

			if(IsElementPresent(driver,obj))
			{
				text = obj.getText();
				test.log(LogStatus.PASS, "Fetched the Data "+text+" from the Object "+ ObjName);
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName+" Problem in fetching the Data"+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return text;
	}

	// To verify the contains text
	public boolean compareContainsText(WebDriver driver, WebElement obj, String ObjName, String value,ExtentTest test) throws Exception
	{
		boolean flag = false;
		try{

			if(IsElementPresent(driver,obj))
			{
				if(obj.getText().contains(value))
				{
				test.log(LogStatus.PASS, ObjName + " value matches "+value);
				flag=true;
				}
				else
				{
					test.log(LogStatus.FAIL, ObjName + " value does not matches "+value + " " +test.addScreenCapture(getScreenhot(driver, ObjName)));
				}
			}
			else
			{
				test.log(LogStatus.FAIL, ObjName+" is not present "+test.addScreenCapture(getScreenhot(driver, ObjName)));
			}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+ObjName + " value does not match "+value+" "+test.addScreenCapture(getScreenhot(driver, ObjName)));
		}
		return flag;
	}
	
	// To get the attribute of an element
	public String getElementAttribute(WebElement obj, String attr, String objName, ExtentTest test){
		
		String getAtrrVal = null;
		try{
			getAtrrVal	= obj.getAttribute(attr);
			if(getAtrrVal.isEmpty()){
				test.log(LogStatus.FAIL, "The element "+objName+" has empty value for "+ attr +" Attribute");
			
			}else{
				test.log(LogStatus.PASS,"The element "+objName+" has "+getAtrrVal+" value for "+attr+" attribute");
		
			}
		}catch(Exception e){
			e.printStackTrace();
			test.log(LogStatus.FAIL, "An exception occured while retrieving the attribute value for the element");
		}
		
		return getAtrrVal;
	}
	
}
