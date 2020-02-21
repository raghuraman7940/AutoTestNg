package lomt.pearson.pagefunctions.english;

import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class EnglishGSE extends CommonAppReusable{
	
	public boolean searchGSEDescriptiveId(String desId,ExtentTest test){
		boolean flag = false;
		try{
			rc.CreateScenarioHeader("Browse Data "+desId, test);
			if(rc.SetText(driver, englishPOM.txtDescriptiveId, "Search Terms TextBox",desId , test)){
			
			if(rc.click(driver,commonPOM.get_btn_UpdateResults(), "Update Results button", test))
			{
				waitforLoadIcon(test);
				flag=rc.VerifyObjectPresent(driver, returnElement(By.xpath("//span[text()='"+desId+"']")), "Search Result - "+desId, test);
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public boolean clickGSENode(String gse, ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, returnElement(By.partialLinkText(gse)), "GSE Node", test);
			waitforLoadIcon(test);
			flag=rc.VerifyObjectPresent(driver, englishPOM.btnGseEdit, "GSE Edit Button", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	public boolean clickGSECheckBox(String gse,ExtentTest test){
		boolean flag = false;
		
		try{
			flag = rc.clickOnInvisible(driver, returnElement(By.xpath("//span[text()='"+gse+"']/ancestor::div[@class='list-row row']//input[@type='checkbox']")), "GSE Node Checkbox"+gse, test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public boolean clickExportButton(ExtentTest test){
		boolean flag = false;
		try{
		removeExistingFile();
		flag = rc.clickOnInvisible(driver, englishPOM.btnGSEExport, "Export button", test);
		waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
		
	}
	
	
	public boolean VerifyRecord(int itemNo,ExtentTest test){
		return verifySearchRecord(itemNo, test);
				
	}
	
	public String ReturnRecord(int itemNo,ExtentTest test){
		return GetSearchRecord(itemNo, test);
				
	}
	
	
	public boolean ClickAlignGood(ExtentTest test){
		return clickAlignType("Good",test);
				
	}
	
	public boolean verifyGSEActionButtons(ExtentTest test){
		boolean flag = false;
		try{
			if(rc.VerifyObjectPresent(driver, commonPOM.btnBrowse, "Browse button", test)&&rc.VerifyObjectPresent(driver, commonPOM.btnAlign, "Align button", test)&& rc.VerifyObjectPresent(driver, commonPOM.btnCreateReport, "Create Report button", test)){
				flag=true;
			}else {
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
}
