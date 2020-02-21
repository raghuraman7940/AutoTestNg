package lomt.pearson.pagefunctions.english;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class EnglishExternalFramework extends CommonAppReusable {
	public boolean clickManageIngestion(ExtentTest test){
		return SelectModule("Manage Ingestion", test);
	}
	
	
	public boolean selectEnglishLOBRadio(ExtentTest test){
		boolean flag = false;
		try{
			flag= SelectRadio("English",test);
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	
	public boolean selectExternalFrameworkRadio(ExtentTest test){
		boolean flag = false;
		try{
			flag= SelectRadio("External Framework",test);
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public boolean clickNextButton(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, commonPOM.nextButton, "Next Button", test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//Set Curriculum Meta Data details He-External Framework
	public boolean setCurriculumMetaDataHEEF(ExtentTest test, String Title, String DomainValue) {
		boolean flag = false;
		try{
			if(rc.SetText(driver, hePom.learningTitleInputText, "Learning Experience Title", String.valueOf(Title), test)){
				flag = true;
				}
			
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	

	//click EF in Higher Ed home page
	public boolean clickExternalFramework(ExtentTest test){
		return SelectStructure("External Framework",test);
	}
	
	
	public boolean SelectNode (String getEducationalObjectiveValue, ExtentTest test) {
		boolean flag=false;
		try {
			rc.clickOnInvisible(driver, returnElement(By.linkText(getEducationalObjectiveValue)),  getEducationalObjectiveValue+" Link", test);
			Thread.sleep(5000);
			waitforLoadIcon(test);
				flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//click child node for edit
	public boolean clickChildNodeForEditinEnglishProduct(String SearchText,ExtentTest test){
		boolean flag =false;
		try{
			flag=rc.clickOnInvisible(driver, returnElement(By.xpath("//a[contains(@text,'"+SearchText+"')] | //span[@class='node-name']")), "Search Result - "+SearchText, test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	} 
	
	public boolean updateValueinEditHEEF(String UpdateText,ExtentTest test){
		boolean flag =false;
		try{
			hePom.officialstandardcode.clear();
			rc.SetText(driver, hePom.officialstandardcode, "Official Standard Code", UpdateText , test);
			rc.click(driver, commonPOM.get_btn_save(), "Save button", test);
			waitforLoadIcon(test);
			flag=true;
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}	
	
	public boolean VerifyupdateValueinEdit(String UpdateText,ExtentTest test){
		boolean flag =false;
		
		try{
			rc.CreateScenarioHeader("Verify the Updated Text", test);
			if(rc.compareContainsText(driver, englishPOM.UpdatedHeaderValidation, UpdateText,UpdateText, test))
			{
				flag=true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}	

}
