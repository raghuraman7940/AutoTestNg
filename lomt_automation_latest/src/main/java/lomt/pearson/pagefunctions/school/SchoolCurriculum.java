package lomt.pearson.pagefunctions.school;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class SchoolCurriculum extends CommonAppReusable{
	
	public boolean verifyCurriculumIsEnabled(int year, ExtentTest test){
	return	rc.IsElementEnabled(driver, returnElement(By.partialLinkText(String.valueOf(year))), true, test, "Curriculum Goal framework");
	}
	public boolean clickCurriculum(int year, ExtentTest test){
	boolean flag = false;
	try{
		flag = rc.click(driver, returnElement(By.partialLinkText(String.valueOf(year))), "Curriculum Goal framework", test);
		waitforLoadIcon(test);
	}catch(Exception e){
		e.printStackTrace();
		flag = false;
	}
	return flag;
}
	
	

	public boolean ClickAlignExcat(ExtentTest test){
		return clickAlignType("Exact",test);
				
	}
	
	
	public boolean ClickAlignCentral(ExtentTest test){
		return clickAlignType("Central",test);
				
	}
	
	
	
}
