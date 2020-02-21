package lomt.pearson.test_script.visualcorrelation;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import lomt.pearson.common.utils.LOMTBaseClass;

public class SchoolLob  extends LOMTBaseClass{
	
	@Test	
	public void verifyVisualCorrelationLink(){	
try{

	      logger = reports.startTest("LOMT-1875_Verify Visual Correlation Link");	   
		   Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "Login Failed");	
		   Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"HE Link was not displayed");
		   Assert.assertTrue(heHomePage.visualLinkNotDisplayed(logger),"Visual Correlation link is displayed in HE LOB");
		   Assert.assertTrue(heHomePage.clickLOMTLogo(logger),"LOMT logo was not clicked");
		   Assert.assertTrue(homePage.selectEnglishLOB(logger),"English LOB was not clicked");
		   Assert.assertTrue(engHomePage.visualLinkNotDisplayed(logger),"Visual Correlation link is displayed in English LOB");
		   Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"LOMT logo was not clicked");  
		   Assert.assertTrue(homePage.selectSchoolLOB(logger), "School LOB was not clicked");		  
		   Assert.assertTrue(schoolHomePage.visualLinkIsDisplayed(logger), "Standard Curriculum was not clicked");
		   reports.endTest(logger);
		   
		   
}catch(Exception e){
	e.printStackTrace();
	logger.log(LogStatus.FAIL, "Failed because of this exception "+e.getMessage());
	   reports.endTest(logger);
}
	}

}
