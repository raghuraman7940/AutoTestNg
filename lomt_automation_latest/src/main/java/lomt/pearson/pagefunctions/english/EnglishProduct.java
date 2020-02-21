package lomt.pearson.pagefunctions.english;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class EnglishProduct extends CommonAppReusable {
	
	public boolean clickEnglishProduct(String searchedProduct, ExtentTest test){
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
			flag = rc.click(driver, englishPOM.btnGseEdit, "Edit button", test);
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
			englishPOM.txtEnglishProductLevelTitle.clear();
			if(rc.SetText(driver, englishPOM.txtEnglishProductLevelTitle, "Level Title field", updatedValue, test)){
				if(rc.clickOnInvisible(driver, englishPOM.btnSaveChanges, "Save Changes", test)){
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
		return rc.getElementAttribute(englishPOM.txtEnglishProductLevelTitle, "value", "Level Title text field", test);
	}
}
