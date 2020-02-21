package lomt.pearson.pagefunctions.he;

import java.sql.Timestamp;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;
import lomt.pearson.constant.SchoolConstant;
import lomt.pearson.constant.HEConstant;

public class HELobHomePage extends CommonAppReusable{
	public boolean verifyAllStructures(ExtentTest test){
		boolean flag = false;
		if(rc.IsElementEnabled(driver, hePom.externalFrameworkLink, true, test, "External Framework")
				&& rc.IsElementEnabled(driver, hePom.productLink, true, test, "Product")&&
				rc.IsElementEnabled(driver, hePom.eoLink, true, test, "Educational Objective")
				){
			flag = true;
		}else {
			flag = false;
		}
		return flag;
	}

	//click EF in Higher Ed home page
	public boolean clickExternalFramework(ExtentTest test){
		return SelectStructure("External Framework",test);
	}
	
	//click Product in Higher Ed home page
	public boolean clickHeProduct(ExtentTest test){
		return SelectStructure("Product",test);
	}
	
	//click EO in Higher Ed home page
	public boolean clickEducationObjective(ExtentTest test){
		return SelectStructure("Educational Objective",test);
	}  
	
	//Click Educational Objective Breadcrump
	public boolean clickEducationalObjbreadcrump(ExtentTest test) {
		boolean flag = false;
		try{
		flag= rc.click(driver, hePom.educationalobjectivebreadcrump, "Educational Objective Breadcrump", test);
		 waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	

	
	//Click Higher Education Breadcrump
	public boolean clickHigherEducation(ExtentTest test) {
		boolean flag = false;
		try{
		flag= rc.click(driver, hePom.lnkHigherEducation, "Higher Education", test);
		 waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//Click Manage Ingestion in LOB Home Page
	public boolean clickManageIngestion(ExtentTest test){
		return SelectModule("Manage Ingestion", test);
	}
	
	//Click Higher Ed LOB Radio button Inside Manage Ingestion
	public boolean selectHeLOBRadio(ExtentTest test){
		boolean flag = false;
		try{
			flag= rc.click(driver, commonPOM.heLOBRadioButton, "Higher Education Radio", test);
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	// To select External Framework Radio Button in Manage Ingestion
	public boolean selectExternalFrameworkRadio(ExtentTest test){
		boolean flag= false;
		try{
			flag = rc.click(driver, commonPOM.heExternalFramework, "External Framework Radio", test);			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;		
	}
	
	//To select Product Radio button in Manage Ingestion
	public boolean selectProductRadio(ExtentTest test){
		boolean flag= false;
		try{
			flag = rc.click(driver, commonPOM.HEProductRadioButton, "Product Radio", test);			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;		
	}
	
	
	//To select Product Radio button in Manage Ingestion
		public boolean selectEORadio(ExtentTest test){
			boolean flag= false;
			try{
				flag = rc.click(driver, commonPOM.HEEORadioButton, "EO Radio", test);			
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
	
	public boolean clickPreviousButton(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, commonPOM.previousButton, "Previous Button", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//Set Curriculum Meta Data details
	public boolean setCurriculumMetaData(ExtentTest test, String Title, String DomainValue, String StatusValue) {
		boolean flag = false;
		try{
			if(rc.SetText(driver, hePom.learningTitleInputText, "Learning Experience Title", String.valueOf(Title), test)){
			//if(rc.SetText(driver, hePom.learningTitleInputText, "Learning Experience Title", Title , test)){
				flag = true;
				}
			
			if(rc.click(driver, hePom.domainNameDropDown, "Domain Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, hePom.domainList, "Domain dropdown list", test)){
					List<WebElement> drpDomainList = hePom.domainList;
					for(WebElement drpDomain : drpDomainList ){
						if(drpDomain.getText().equalsIgnoreCase(DomainValue)){
							drpDomain.click();
							flag = true;
							break;
						}
					}
				}
			}
			
			if(rc.click(driver, hePom.statusDropDown, "Status Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, hePom.statusDropDownList, "Status dropdown list", test)){
					List<WebElement> drpStatusList = hePom.statusDropDownList;
					for(WebElement drpStatus : drpStatusList ){
						if(drpStatus.getText().equalsIgnoreCase(StatusValue)){
							drpStatus.click();
							flag = true;
							break;
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

	
	public boolean clickEducationalObjective(ExtentTest test){
		return SelectStructure("Educational Objective",test);
	}
	
	
	public boolean ClickAlinged(ExtentTest test){
		return clickAlignType("Aligned",test);
				
	}
	
	//Set Curriculum Meta Data details He-External Framework
		public boolean setCurriculumMetaDataHEEF(ExtentTest test, String Title, String DomainValue) {
			boolean flag = false;
			try{
				if(rc.SetText(driver, hePom.learningTitleInputText, "Learning Experience Title", String.valueOf(Title), test)){
					flag = true;
					}
				
				if(rc.click(driver, hePom.domainNameDropDown, "Domain Dropdown", test)){
					if(rc.VerifyObjectsPresent(driver, hePom.domainList, "Domain dropdown list", test)){
						List<WebElement> drpDomainList = hePom.domainList;
						for(WebElement drpDomain : drpDomainList ){
							if(drpDomain.getText().equalsIgnoreCase(DomainValue)){
								drpDomain.click();
								flag = true;
								break;
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
		
	public boolean visualLinkNotDisplayed(ExtentTest test){
		return verifyVisualCorrelationLinkDisplayed(false,test);
	}

	
}
		
	

