package lomt.pearson.pagefunctions.english;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;
import lomt.pearson.page_object.EnglishPOM;

public class EnglishLobHomePage extends CommonAppReusable {
	
	public boolean verifyAllStructures(ExtentTest test){
		boolean flag = false;
		if(rc.IsElementEnabled(driver, englishPOM.gseLink, true,test,"GSE")&& rc.IsElementEnabled(driver, englishPOM.externalFrameworkLink, true,test,"External Framework")
				&&rc.IsElementEnabled(driver, englishPOM.productLink, true,test,"Product")){
			flag = true;
			
		}else{
			flag = false;
		}
		return flag;
	}
public boolean clickGSE(ExtentTest test){	
	return SelectStructure("GSE", test);
}


public boolean clickExternalFramework(ExtentTest test){
	return SelectStructure("External Framework",test);
}

public boolean clickProduct(ExtentTest test){
	return SelectStructure("Product",test);
}

public boolean clickEnglishBreadCrumb(ExtentTest test){
	boolean flag = false;
	try{
	flag = rc.click(driver, englishPOM.lnkEnglish, "English Bread crumb", test);
	 waitforLoadIcon(test);
	}catch(Exception e){
		e.printStackTrace();
		flag = false;
	}
	return flag;
}
public boolean clickManageIngestion(ExtentTest test){
	return SelectModule("Manage Ingestion", test);
}

public boolean selectEnglishLOBRadio(ExtentTest test){
	boolean flag = false;
	try{
		flag = rc.click(driver, commonPOM.engLOBRadioButton, "English LOB Radio", test);
	}catch(Exception e){
		e.printStackTrace();
		flag = false;
	}
	return flag;
}

public boolean selectGSERadio(ExtentTest test){
	boolean flag= false;
	try{
		flag = rc.click(driver, commonPOM.engGSERadio, "English GSE Radio", test);
	}catch(Exception e){
		e.printStackTrace();
		flag = false;
	}
	return flag;
}

public boolean selectEngProductRadio(ExtentTest test){
	boolean flag = false;
	try{
		flag=rc.click(driver, commonPOM.engProductRadio, "English Product Radio", test);
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

public boolean setEnglishProductMetadata(String subjectValue, String statusValue, ExtentTest test){
	boolean flag = false;
	try{

		if(rc.click(driver, englishPOM.subjectDropdown, "Subject Dropdown", test)){
			if(rc.VerifyObjectsPresent(driver, englishPOM.subjectDropdownList, "Subject dropdown list", test)){
				List<WebElement> drpSubjectList =englishPOM.subjectDropdownList;
				for(WebElement drpSubject : drpSubjectList){
					if(drpSubject.getText().equalsIgnoreCase(subjectValue)){
						drpSubject.click();
						flag = true;
						break;
					}
				}
			}
			
			if(rc.click(driver, englishPOM.statusSetDropdown, "Status Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, englishPOM.statusSetDropdownList, "Status dropdown list", test)){
					List<WebElement> drpStatusList = englishPOM.statusSetDropdownList;
					for(WebElement drpStatus : drpStatusList ){
						if(drpStatus.getText().equalsIgnoreCase(statusValue)){
							drpStatus.click();
							flag = true;
							break;
						}
					}
				}
			}
			flag = rc.SetText(driver, englishPOM.engProductDesctiption, "English Product Description", RandomStringUtils.randomAlphanumeric(13), test);
		}
	
		
	}catch(Exception e){
		e.printStackTrace();
		flag = false;
	}
	return flag;
}

public boolean visualLinkNotDisplayed(ExtentTest test){
	return verifyVisualCorrelationLinkDisplayed(false,test);
}


}
