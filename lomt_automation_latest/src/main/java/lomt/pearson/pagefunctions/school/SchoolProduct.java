package lomt.pearson.pagefunctions.school;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class SchoolProduct extends CommonAppReusable {
	
	public boolean clickSchoolProduct(String searchedProduct, ExtentTest test){
		boolean flag = false;
		try{
			flag =rc.click(driver, returnElement(By.partialLinkText(searchedProduct)), "Searched Product", test);
		waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	public boolean clickParentNode(String parentNode,ExtentTest test){
		boolean flag = false;
		try{
			//flag = rc.click(driver, returnElement(By.xpath("//span[@class='node-name' and contains(text(),'"+parentNode+"')]")), "Parent Node"+parentNode, test);
			flag = rc.click(driver, returnElement(By.xpath("//*[contains(text(),'"+parentNode+"')]")), "Parent Node"+parentNode, test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean clickEditButton(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, schoolPOM.btnEditNode, "Edit button", test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public boolean updateLevelTitle(String updatedValue, ExtentTest test){
		boolean flag = false;
		try{
			schoolPOM.txtSchoolProductLevelTitle.clear();
			if(rc.SetText(driver, schoolPOM.txtSchoolProductLevelTitle, "Level Title field", updatedValue, test)){
				if(rc.clickOnInvisible(driver, schoolPOM.btnSaveChanges, "Save Changes", test)){
					waitforLoadIcon(test);
					flag = true;
				}else{
					flag = false;
				}
			}else {
				flag = false;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	public String getLevelTitle(ExtentTest test){		
		return rc.getElementAttribute(schoolPOM.txtSchoolProductLevelTitle, "value", "Level Title text field", test);

	}
	

}
