package lomt.pearson.pagefunctions;



import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class HomePage extends CommonAppReusable{

	public boolean verifyAllLobsClickable(ExtentTest test){
		boolean flag = false;
		if(
		rc.IsElementEnabled(driver, commonPOM.englishLOB, true, test, "English LOB")&&
		rc.IsElementEnabled(driver,commonPOM.schoolGlobalLOB, true, test,"School Global")&&
		rc.IsElementEnabled(driver,commonPOM.heLOB, true, test,"Higher Education")){
			flag = true;		
		}else{
			flag = false;
			}
		return flag;
	}	
	public boolean logout(ExtentTest test){		
		boolean flag = false;
		try{
		if(rc.clickOnInvisible(driver, commonPOM.logoutOption, "Logout Link", test)){
			Thread.sleep(1000);
			if(rc.clickOnInvisible(driver, commonPOM.logout, "Logout Button", test)){
				waitforLoadIcon(test);
				if(rc.VerifyObjectPresent(driver, login.userName, "User Name Text", test)){
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
	public boolean selectSchoolLOB(ExtentTest test){
		return selectLOB("School Global", test);
	}
	public boolean selectHigherEducationLOB(ExtentTest test){
		return selectLOB("Higher Education", test);
	}
	public boolean selectEnglishLOB(ExtentTest test){
		return selectLOB("English", test);
	} 
	public boolean getBuildVersion(ExtentTest test){
		boolean flag = false;
		try{
		if(rc.clickOnInvisible(driver, commonPOM.logoutOption, "Logout Link", test)){
			Thread.sleep(1000);
			rc.CreateScenarioHeader("The current build is "+commonPOM.buildVersion.getText().trim(), test);
		if(rc.clickOnInvisible(driver, commonPOM.logoutOption, "Logout Link", test)){
			flag = true;
		}
		}
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		System.out.println("this is for testing");
		return flag;
	}
}