package lomt.pearson.pagefunctions.school;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;
import lomt.pearson.page_object.IntermediaryPOM;

public class SchoolIntermediaries extends CommonAppReusable{
	
	//Select the option as Intermediary inside Ingestion
	public boolean SelectIntermediatiesRadioInIngestion(String Intermediate,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Select Module "+Intermediate, test);
			String InterName = Intermediate.toLowerCase().trim().replace(" ", "-").replace(",","");
			if(rc.clickOnInvisible(driver, returnElement(By.xpath("//input[contains(@value,'"+InterName+"')]")),  InterName+" Link", test))
				// if(rc.clickOnInvisible(driver, intermPOM.intermediaryProvideMessage,  InterName+" Link", test))    //
			{
				flag=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	// Select type of the intermediary inside school global
	public boolean SelectIntermediarySchoolglobal(String Intermediate,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Select Module "+Intermediate, test);
			// String InterName = Intermediate.toLowerCase().trim().replace(" ", "-").replace(",","");
			if(rc.clickOnInvisible(driver, returnElement(By.linkText(Intermediate)),  Intermediate+" Link", test))
			{
				flag=true;
				waitforLoadIcon(test);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// add logger
		}
		return flag;
	}
	
	// Clicking on Search Node
	public boolean SelectNode (String getIntermediaryValue, ExtentTest test) {
		boolean flag=false;
		try {
			rc.clickOnInvisible(driver, returnElement(By.linkText(getIntermediaryValue)),  getIntermediaryValue+" Link", test);
			Thread.sleep(5000);
			waitforLoadIcon(test);
				flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//Update Value inside Edit(Node)
	public boolean updateValueinEdit(String UpdateText,ExtentTest test){
		boolean flag =false;
		try{
			rc.SetText(driver, intermPOM.statementCode, "Statement Code Test box", UpdateText , test);
			rc.click(driver, commonPOM.get_btn_save(), "Save button", test);
			waitforLoadIcon(test);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}	
	
	
}
