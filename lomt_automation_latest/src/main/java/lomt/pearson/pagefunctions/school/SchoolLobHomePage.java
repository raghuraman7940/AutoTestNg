package lomt.pearson.pagefunctions.school;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;
import lomt.pearson.constant.SchoolConstant;

public class SchoolLobHomePage extends CommonAppReusable {
	
	public boolean verifyAllStructures(ExtentTest test){
		boolean flag = false;
		if(rc.IsElementEnabled(driver, schoolPOM.lnkCurriculumStandard, true, test, "Curriculum Standard")
				&& rc.IsElementEnabled(driver, schoolPOM.lnkIntermedies, true, test, "Intermedies")&&
				rc.IsElementEnabled(driver, schoolPOM.lnkSchoolProduct, true, test, "School Product")
				){
			flag = true;
			
		}else {
			flag = false;
		}
		return flag;
	}

	public boolean clickStandardCurriculum(ExtentTest test){
		return SelectStructure("Curriculum Standard",test);
	}
	
	public boolean clickSchoolProduct(ExtentTest test){
		return SelectStructure("Product",test);
	}
	
	public boolean clickIntermediaries(ExtentTest test){
		return SelectStructure("Intermediaries",test);
	}
	
	public boolean clickSchoolGlobal(ExtentTest test) {
		boolean flag = false;
		try{
		flag= rc.click(driver, schoolPOM.lnkSchoolGlobal, "School Global", test);
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
	
	public boolean selectSchoolLOBRadio(ExtentTest test){
		boolean flag = false;
		try{
			flag= rc.click(driver, commonPOM.schoolGlobalLOBRadioButton, "School Global Radio", test);
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	public boolean selectCurriculumXMLRadio(ExtentTest test){
		boolean flag= false;
		try{
			flag = rc.click(driver, commonPOM.curriculumStandardRadioButton, "Curriculum ab.xml Radio", test);			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;		
	}
	//To select Intermediatery Radio button in Manage Ingestion
	public boolean selectIntermediatersRadio(ExtentTest test){
		boolean flag= false;
		try{
			flag = rc.click(driver, commonPOM.intermediaryRadioButton, "Intermediary Radio", test);			
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
	public boolean setCurriculumMetaData(ExtentTest test, String subjectValue, String countryValue, String authorityValue, String setValue, int givenYear) {
		boolean flag = false;
		try{
			if(rc.click(driver, schoolPOM.subjectDropdown, "Subject Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, schoolPOM.subjectDropdownList, "Subject dropdown list", test)){
					List<WebElement> drpSubjectList =schoolPOM.subjectDropdownList;
					for(WebElement drpSubject : drpSubjectList){
						if(drpSubject.getText().equalsIgnoreCase(subjectValue)){
							drpSubject.click();
							flag = true;
							break;
						}
					}
				}
			}
			if(rc.click(driver, schoolPOM.countryDropdown, "Country Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, schoolPOM.countryDropdownList, "Country dropdown list", test)){
					List<WebElement> drpCountryList = schoolPOM.countryDropdownList;
					for(WebElement drpCountry : drpCountryList ){
						if(drpCountry.getText().equalsIgnoreCase(countryValue)){
							drpCountry.click();
							flag = true;
							break;
						}
					}
				}
				
				if(rc.click(driver, schoolPOM.authorityDropdown, "Authority Dropdown", test)){
					if(rc.VerifyObjectsPresent(driver, schoolPOM.authorityDropdownList, "Country dropdown list", test)){
						List<WebElement> drpAuthorityList = schoolPOM.authorityDropdownList;
						for(WebElement drpAuthority : drpAuthorityList ){
							if(drpAuthority.getText().equalsIgnoreCase(authorityValue)){
								drpAuthority.click();
								flag = true;
								break;
							}
						}
					}
				}
			}
			
			
			if(rc.click(driver, schoolPOM.curriculumSetDropdown, "Curriculum Set Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, schoolPOM.curriculumSetDropdownList, "Country dropdown list", test)){
					List<WebElement> drpSetList = schoolPOM.curriculumSetDropdownList;
					for(WebElement drpSet : drpSetList){
						//if(drpSet.getText().equalsIgnoreCase(setValue)){
							drpSet.click();
							flag =true;
							break;
						//}
					}
				}
			}
			if(rc.SetText(driver, schoolPOM.adoptedYear, "Adopted year", String.valueOf(givenYear), test)){
				if(rc.SetText(driver, schoolPOM.csSourceURL, "Source URL", SchoolConstant.CS_SOURCE_URL, test)){
					if(rc.SetText(driver, schoolPOM.csInfoURL, "Curriculum info URL", SchoolConstant.CS_INFO_URL, test)){
						flag = true;
					}
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}
	
public boolean selectProductRadio(ExtentTest test){
	boolean flag = false;
	try{
		flag=rc.click(driver, commonPOM.productRadioButton, "School Product", test);
	}catch(Exception e){
		e.printStackTrace();
		flag = false;
	}	
	return flag;
}
	public boolean setSchoolProductMetadata(String subjectValue, String statusValue, ExtentTest test){
		boolean flag = false;
		try{
			if(rc.click(driver, schoolPOM.subjectDropdown, "Subject Dropdown", test)){
				if(rc.VerifyObjectsPresent(driver, schoolPOM.subjectDropdownList, "Subject dropdown list", test)){
					List<WebElement> drpSubjectList =schoolPOM.subjectDropdownList;
					for(WebElement drpSubject : drpSubjectList){
						if(drpSubject.getText().equalsIgnoreCase(subjectValue)){
							drpSubject.click();
							flag = true;
							break;
						}
					}
				}
				
				if(rc.click(driver, schoolPOM.statusSetDropdown, "Status Dropdown", test)){
					if(rc.VerifyObjectsPresent(driver, schoolPOM.statusSetDropdownList, "Status dropdown list", test)){
						List<WebElement> drpStatusList = schoolPOM.statusSetDropdownList;
						for(WebElement drpStatus : drpStatusList ){
							if(drpStatus.getText().equalsIgnoreCase(statusValue)){
								drpStatus.click();
								flag = true;
								break;
							}
						}
					}
				}
				flag = rc.SetText(driver, schoolPOM.schoolProductDesctiption, "School Product Description", RandomStringUtils.randomAlphanumeric(13), test);
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public boolean visualLinkIsDisplayed(ExtentTest test){
		return verifyVisualCorrelationLinkDisplayed(true,test);
	}

}
