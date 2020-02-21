package lomt.pearson.test_script.somkeTesting;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import lomt.pearson.common.utils.LOMTBaseClass;
import lomt.pearson.constant.EnglishTestData;
import lomt.pearson.constant.HEConstant;
import lomt.pearson.constant.SchoolConstant;
import lomt.pearson.page_object.EnglishPOM;
import lomt.pearson.pagefunctions.english.EnglishLobHomePage;
import lomt.pearson.pagefunctions.he.HEEducationalObjective;

public class SmokeTestCases extends LOMTBaseClass{
	private static int startYear = 1901;
	private static int endYear = 2000;
	private static int year = 0;
	private static int reingestionYear = 0;
	private  String mssNewValue; 
	
	@Test(priority =1)
	public void ingestSchoolXml(){
		String[] tcCovered = {"TC_0","TC_1","TC_2","TC_3","TC_4","TC_5", "TC_6"};
		mssNewValue = RandomStringUtils.randomAlphanumeric(13);
		try{
			logger = reports.startTest("Current Build version");
			
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_0 - Login Failed");					
			Assert.assertTrue(homePage.getBuildVersion(logger));
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_1_Verify all LOB's are clickable");	
			Assert.assertTrue(homePage.verifyAllLobsClickable(logger), "TC_1 - LOB's are not enabled");			
			Assert.assertTrue(homePage.selectSchoolLOB(logger),"TC_1 - School Global link is not clickable");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_2_Verify all structures under School are dispalyed");
			Assert.assertTrue(schoolHomePage.verifyAllStructures(logger), "TC_2 - All structures under school global are not enabled");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_3_Verify all structures under school are clickable");
			Assert.assertTrue(schoolHomePage.clickStandardCurriculum(logger), "TC_3 - Standard Curriculum was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolGlobal(logger),"TC_3 - School Global link was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolProduct(logger), "TC_3 - School Product was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolGlobal(logger),"TC_3 - School Global link was not clicked");
			Assert.assertTrue(schoolHomePage.clickIntermediaries(logger), "TC_3 - Intermediaries was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolGlobal(logger),"TC_3 - School Global link was not clicked");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_4_Ingestion of Curriculum via ab.xml");
			Assert.assertTrue(schoolHomePage.updateXMLFile(logger, SchoolConstant.CurriculumXMLFile, mssNewValue),"TC_4 - ab.xml file was not updated properly");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger), "TC_4 - School Manage Ingestion link was not clicked");
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_4 - School LOB Radio is not clicked");
			Assert.assertTrue(schoolHomePage.selectCurriculumXMLRadio(logger),"TC_4 - School Curriculum XML Radio is not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_4 - Next button was not clicked");
			 year = startYear + (int)Math.round(Math.random() * (endYear - startYear));
			 System.out.println("year : "+year);
			 Assert.assertTrue(schoolHomePage.setCurriculumMetaData(logger, SchoolConstant.Ingestion_Subject, SchoolConstant.Ingestion_Country, SchoolConstant.Ingestion_Authority, SchoolConstant.Ingestion_Set, year), "TC_4 - Curriculum Meta data was not set");
			 Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_4 - Next button was not clicked");
			 Assert.assertTrue(schoolHomePage.uploadFile(SchoolConstant.CurriculumXMLFile, logger), "TC_4 - Ingestion of xml file got failed");	
			 reports.endTest(logger);	
			 logger = reports.startTest("Smoke_TC_5_Apply filter for Curriculum");
			 Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_5 - LOMT logo was not clicked");
			 Assert.assertTrue(homePage.selectSchoolLOB(logger),"TC_5 - School Global link is not clickable");
			 Assert.assertTrue(schoolHomePage.clickStandardCurriculum(logger), "TC_5 - Standard Curriculum was not clicked");
			 Assert.assertTrue(schoolHomePage.BrowseData(String.valueOf(year),"" , logger),"TC_5 - Filter option was not set");
			 reports.endTest(logger);
			 logger = reports.startTest("Smoke_TC_6_Click on searched curriculum");
			 Assert.assertTrue(schoolCurriculum.verifyCurriculumIsEnabled(year, logger), "TC_6 - Searched curriculum is not enabled");
			 Assert.assertTrue(schoolCurriculum.clickCurriculum(year, logger),"TC_6 - Standard curriculum is not clickable");
			 reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
					
				}
			}			
			Assert.fail();
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
	}
	
	@Test(priority =2)
	public void reingestStandardXML(){
		String[] tcCovered = {"TC_7","TC_8"};		
		try{
			logger = reports.startTest("Smoke_TC_7_Reingest standard curriculum ab.xml");
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_7 - Login Failed");
			Assert.assertTrue(homePage.selectSchoolLOB(logger),"TC_7 - School Global link is not clickable");			
			mssNewValue = schoolHomePage.getMssId(logger, SchoolConstant.CurriculumXMLFile);
			Assert.assertNotNull(mssNewValue,"TC_7 - MSS id value was not obtained");		
			Assert.assertTrue(schoolHomePage.updateXMLFile(logger, SchoolConstant.CurriculumXMLFile_Reingest, mssNewValue),"TC_7 - reingestion ab.xml file was not updated properly");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger), "TC_7 - School Manage Ingestion link was not clicked");
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_7 - School LOB Radio is not clicked");
			Assert.assertTrue(schoolHomePage.selectCurriculumXMLRadio(logger),"TC_7 - School Curriculum XML Radio is not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_7 - Next button was not clicked");
			reingestionYear = startYear + (int)Math.round(Math.random() * (endYear - startYear));
			 System.out.println("re-ingested year : "+reingestionYear);
			 Assert.assertTrue(schoolHomePage.setCurriculumMetaData(logger, SchoolConstant.Reingestion_Subject, SchoolConstant.Ingestion_Country, SchoolConstant.Ingestion_Authority, SchoolConstant.Ingestion_Set,reingestionYear ), "TC_7 - Curriculum Meta data was not set");
			 Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_7 - Next button was not clicked");
			 Assert.assertTrue(schoolHomePage.uploadFile(SchoolConstant.CurriculumXMLFile_Reingest, logger), "TC_7 - Ingestion of xml file got failed");	
			 reports.endTest(logger);
				logger = reports.startTest("Smoke_TC_8_Expand and collapse curriculum node");
			 Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_8 - LOMT logo was not clicked");
			 Assert.assertTrue(homePage.selectSchoolLOB(logger),"TC_8 - School Global link is not clickable");
			 Assert.assertTrue(schoolHomePage.clickStandardCurriculum(logger), "TC_8 - Standard Curriculum was not clicked");
			 Assert.assertTrue(schoolHomePage.BrowseData(String.valueOf(reingestionYear),"" , logger),"TC_8 - Filter option was not set");
			 Assert.assertTrue(schoolCurriculum.clickCurriculum(reingestionYear, logger),"TC_8 - Standard curriculum is not clickable");
			Assert.assertTrue(schoolCurriculum.expandCollapseParentCurriculumNode(true, logger), "TC_8 - Node was not expanded");		
			Assert.assertTrue(schoolCurriculum.expandCollapseParentCurriculumNode(false, logger), "TC_8 - Node was not collapsed");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;					
				}
			}			
			Assert.fail();
			
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
		
	}
		////SCENARIO - 3////
	@Test(priority = 3)
	public void ingestintermediary(){
		String[] tcCovered = {"TC_9","TC_10","TC_11","TC_12","TC_13"};
		String getIntermediaryValue;
		try{
			logger = reports.startTest("Smoke_TC_9_Ingestion of Intermediary");	
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_9 - Unable to Login into LOMT");			
			Assert.assertTrue(schoolHomePage.selectLOB("School Global", logger),"TC_9 - School Global link was not clicked");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger), "TC_9 - School Manage Ingestion link was not clicked");
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_9 - School LOB Radio is not clicked");
			Assert.assertTrue(schoolHomePage.selectIntermediatersRadio(logger),"TC_9 - Intermediater Radio is not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_9 - Next button was not clicked");
			Assert.assertTrue(SchoolIntermediaries.SelectIntermediatiesRadioInIngestion("Accounting",logger),"TC_9 - Intermediatery is not selected");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_9 - Next button was not clicked");
			getIntermediaryValue = schoolHomePage.updateExcelValue("Intermediary_Template.xlsx","Sheet1",1,0,"Ingestionofintermediary", logger);
		    Assert.assertNotNull(getIntermediaryValue,"TC_9 - Excel update is not working");
		    Assert.assertTrue(schoolHomePage.uploadFile(SchoolConstant.IntermediatersExcelFile, logger), "TC_9 - Ingestion of excel file got failed");	
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_10_Set filter in Intermediary");	
			Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_10 - LOMT logo was not clicked");
			Assert.assertTrue(schoolHomePage.selectLOB("School Global", logger),"TC_10 - School Global link was not clicked");
			Assert.assertTrue(schoolHomePage.clickIntermediaries(logger), "TC_10 - Intermediaries was not clicked");
			Assert.assertTrue(SchoolIntermediaries.SelectIntermediarySchoolglobal("Accounting",logger), "TC_10 - Intermediaries was not clicked");
			Assert.assertTrue(schoolHomePage.BrowseData(getIntermediaryValue, "Processes (P)/Cognitive Process", logger), "TC_10 - Intermediary Filter is not working/");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_11_Verify ingested intermediary is available");	
			Assert.assertTrue(schoolHomePage.clearFilter(logger), "TC_11 - Intermediary Search Results is not working");
			Assert.assertTrue(schoolHomePage.BrowseData(getIntermediaryValue, "", logger), "TC_11 - Intermediary Search Results is not working");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_12_Verify Action is visible for intermediary");			
			Assert.assertTrue(schoolHomePage.clickIntermediariesback(logger), "TC_12 - Intermediaries was not clicked");
			Assert.assertTrue(schoolHomePage.clickActionIntermediaryAccounting(logger), "TC_12 - Action click is not working");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_13_Export the intermediary");	
			Assert.assertTrue(schoolHomePage.exportClick(logger), "TC_13 - Export button is not working");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
					
				}
			}			
			Assert.fail();
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
	}
	
	////SCENARIO - 4////
	@Test(priority = 4)
	public void reingestintermediary(){
		String[] tcCovered = {"TC_14","TC_15","TC_16"};
		String getIntermediaryValue;
		try{	
			logger = reports.startTest("Smoke_TC_14_Ingestion of Intermediary");	
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_14 - Unable to Login into LOMT");
			Assert.assertTrue(schoolHomePage.selectLOB("School Global", logger),"TC_14 - School Global link was not clicked");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger), "TC_14 - School Manage Ingestion link was not clicked");
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_14 - School LOB Radio is not clicked");
			Assert.assertTrue(schoolHomePage.selectIntermediatersRadio(logger),"TC_14 - Intermediater Radio is not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_14 - Next button was not clicked");
			Assert.assertTrue(SchoolIntermediaries.SelectIntermediatiesRadioInIngestion("Accounting",logger),"TC_14 - Intermediatery is not selected");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_14 - Next button was not clicked");
		    Assert.assertTrue(schoolHomePage.uploadIncorrectFile(SchoolConstant.IntermediatersIncorrectExcelFile, logger), "TC_14 - Ingestion of incorrect excel file got failed");	
		    Assert.assertTrue(schoolHomePage.clickPreviousButton(logger),"TC_14 - Previous button was not clicked");
		    getIntermediaryValue = schoolHomePage.updateExcelValue("Intermediary_Template.xlsx","Sheet1",1,0,"Ingestionofintermediary", logger);
		    Assert.assertNotNull(getIntermediaryValue,"TC_14 - Excel update is not working");
		    Assert.assertTrue(schoolHomePage.uploadFile(SchoolConstant.IntermediatersExcelFile, logger), "TC_14 - Ingestion of excel file got failed");
		    reports.endTest(logger);
		    logger = reports.startTest("Smoke_TC_15_Selecting the Search node of Intermediary");	
		    Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_15 - LOMT logo was not clicked");
			Assert.assertTrue(schoolHomePage.selectLOB("School Global", logger),"TC_15 - School Global link was not clicked");
			Assert.assertTrue(schoolHomePage.clickIntermediaries(logger), "TC_15 - Intermediaries was not clicked");
			Assert.assertTrue(SchoolIntermediaries.SelectIntermediarySchoolglobal("Accounting",logger), "TC_15 - Intermediaries was not clicked");
			Assert.assertTrue(schoolHomePage.BrowseData(getIntermediaryValue, "Processes (P)/Cognitive Process", logger), "TC 15 - Intermediary Filter is not working/");
			Assert.assertTrue(SchoolIntermediaries.SelectNode(getIntermediaryValue, logger), "TC_15 - Intermediary Filter is not working/");
			reports.endTest(logger);		
			logger = reports.startTest("Smoke_TC_16_verify Edit in Intermediary");
			Assert.assertTrue(schoolHomePage.clickEdit(logger), "TC_16- LOMT logo was not clicked");
			Assert.assertTrue(SchoolIntermediaries.updateValueinEdit("Updated", logger), "TC_16 - Update Value is not working");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
					
				}
			}			
			Assert.fail();
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
	}	
	
	
	//SCENARIO - 5
	@Test(priority =5)
	public void ingestHigherEdEO(){
		String[] tcCovered = {"TC_17","TC_18","TC_19","TC_20","TC_21","TC_22","TC_23","TC_24","TC_25"};
		String getEducationalObjectiveValue;
		try{	
			logger = reports.startTest("Smoke_TC_17_Ingestion of HigherEducation EO");			
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_17 - Login Failed");
			Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_17 - Higher Education link is not clickable");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC 18_Verify All Structures in Higher ED");
			Assert.assertTrue(heHomePage.verifyAllStructures(logger), "TC_18 - All structures under Higher Education are not enabled");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC 19_Verify All Links in HE");
			Assert.assertTrue(heHomePage.clickExternalFramework(logger), "TC_19 - External framework was not clicked");
			Assert.assertTrue(heHomePage.clickHigherEducation(logger),"TC_19 - Higher Education link was not clicked");
			Assert.assertTrue(heHomePage.clickHeProduct(logger), "TC_19 - Higher Ed Product was not clicked");
			Assert.assertTrue(heHomePage.clickHigherEducation(logger),"TC_19 - Higher Education link was not clicked");
			Assert.assertTrue(heHomePage.clickEducationObjective(logger), "TC_19 - Educational Objective was not clicked");
			Assert.assertTrue(heHomePage.clickHigherEducation(logger),"TC_19 - Higher Education link was not clicked");
			reports.endTest(logger);  
			logger = reports.startTest("Smoke_TC_20_Ingestion in HE EO");
			Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_20 - Higher Education Manage Ingestion link was not clicked");
			Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_20 - Higher Education LOB Radio is not clicked");
			Assert.assertTrue(heHomePage.selectEORadio(logger),"TC_20 - Educational Objective Radio is not clicked");
			Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_20 - Next button was not clicked");
			Assert.assertTrue(heHomePage.setCurriculumMetaData(logger, HEConstant.Title, HEConstant.A, HEConstant.AWAITING_APPROVAL), "TC 20 - Curriculum Meta data was not set");
			Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_20 - Next button was not clicked");
			getEducationalObjectiveValue = heHomePage.updateExcelValue("Higher Ed_EducationalObjective.xlsx","DOMAIN MODEL",12,6,"Analyze", logger);
		    Assert.assertNotNull(getEducationalObjectiveValue,"TC 20 - Excel update is not working");
			Assert.assertTrue(heHomePage.uploadFile(HEConstant.EducationalObjectiveExcelFile, logger), "TC 20 - Ingestion of excel file got failed");
			reports.endTest(logger); 
			logger = reports.startTest("Smoke_TC_21_verify intested data in HE EO");
			Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_21 - LOMT logo was not clicked");
			Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_21 - Higher Education link is not clickable");
			Assert.assertTrue(heHomePage.clickEducationObjective(logger), "TC_21 - Educational Objective was not clicked");
			Assert.assertTrue(schoolHomePage.BrowseData(HEConstant.Title, "Anatomy & Physiology", logger), "TC_21 - Educational Objective Filter is not working/");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_22_Verify Chile Node in Higher Ed EO");
			Assert.assertTrue(heEducational.SelectNode(HEConstant.Title, logger), "TC_22 - Select Node is not working/");
			Assert.assertTrue(heEducational.BrowseDataEO(getEducationalObjectiveValue,"", logger), "TC_22 - Educational Objective Filter is not working/");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_23_Verify Expand and Collapse in Higher Ed EO");
			Assert.assertTrue(heEducational.clickSelectAll(logger), "TC_23 - Select All is not working/");
			Assert.assertTrue(heEducational.clickSelectNone(logger), "TC_23 - Node Expandable is not working/");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_24_Verify Action button in Higher Ed EO");
			Assert.assertTrue(heHomePage.clickEducationalObjbreadcrump(logger), "TC_24 - Educational Objective was not clicked");
			Assert.assertTrue(heHomePage.BrowseData(HEConstant.Title, "Anatomy & Physiology", logger), "TC 25 - Educational Objective Filter is not working/");
			Assert.assertTrue(heEducational.clickAction(logger), "TC_24 - Actions is not working/");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_25_Verify Export in Higher Ed EO");
			Assert.assertTrue(heEducational.exportClick(logger), "TC_25 - Export button is not working");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
				}
			}			
			Assert.fail();
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
	}
	
	
//Scenario - 6
	@Test(priority =6)
	public void reingestHigherEdEO(){
	     String getEducationalObjectiveValue;
	     String getEducationalObjectiveValueafterdownload;
	     String Filename;
	     
	     String[] tcCovered = {"TC_26","TC_27","TC_28"};
	     try {
	    	 logger = reports.startTest("Smoke_TC_26_Ingestion in Higher Ed EO");
	     	 Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_26 - Login Failed");
	    	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_26 - Higher Education link is not clickable");
	    	 Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_26 - Higher Education Manage Ingestion link was not clicked");
	    	 Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_26 - Higher Education LOB Radio is not clicked");
	    	 Assert.assertTrue(heHomePage.selectEORadio(logger),"TC_26 - Educational Objective Radio is not clicked");
	    	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_26 - Next button was not clicked");
	    	 Assert.assertTrue(heHomePage.setCurriculumMetaData(logger, HEConstant.Title, HEConstant.A, HEConstant.AWAITING_APPROVAL), "TC_26 - Curriculum Meta data was not set");
	    	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_26 - Next button was not clicked");
	    	 getEducationalObjectiveValue = heHomePage.updateExcelValue("Higher Ed_EducationalObjective.xlsx","DOMAIN MODEL",12,6,"Analyze", logger);
	    	 Assert.assertNotNull(getEducationalObjectiveValue,"TC_26 - Excel update is not working");
	    	 Assert.assertTrue(heHomePage.uploadFile(HEConstant.EducationalObjectiveExcelFile, logger), "TC_26 - Ingestion of excel file got failed");
	    	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_26 - LOMT logo was not clicked");
	    	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_26 - Higher Education link is not clickable");
	    	 Assert.assertTrue(heHomePage.clickEducationObjective(logger), "TC_26 - Educational Objective was not clicked");
	    	 Assert.assertTrue(schoolHomePage.BrowseData(HEConstant.Title, "Anatomy & Physiology", logger), "TC_26 - Educational Objective Filter is not working/");
	    	 Assert.assertTrue(heEducational.clickAction(logger), "TC_26 - Actions is not working/");
	    	 Assert.assertTrue(heEducational.exportClick(logger), "TC_26 - Export button is not working");
	    	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_26 - LOMT logo was not clicked");
		     Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_26 - Higher Education link is not clickable");
		     Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_26 - Higher Education Manage Ingestion link was not clicked");
	    	 Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_26 - Higher Education LOB Radio is not clicked");
	    	 Assert.assertTrue(heHomePage.selectEORadio(logger),"TC_26 - Educational Objective Radio is not clicked");
	    	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_26 - Next button was not clicked");
	    	 Assert.assertTrue(heHomePage.setCurriculumMetaData(logger, HEConstant.Title, HEConstant.A, HEConstant.AWAITING_APPROVAL), "TC_26 - Curriculum Meta data was not set");
	    	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_26 - Next button was not clicked");
	    	 Assert.assertNotNull(Filename = HEEducationalObjective.getTheNewestFile(),"Finding Newest File is not working");
	    	 Assert.assertNotNull(Filename,"TC_26 - Getting Latest File is not working");
	    	 getEducationalObjectiveValueafterdownload = heHomePage.updateExcelValueFromDownload(Filename,"DOMAIN MODEL",12,6,"Analyze", logger);
	    	 Assert.assertNotNull(getEducationalObjectiveValueafterdownload,"TC_26 - Excel update is not working");
	    	 Assert.assertTrue(heHomePage.uploadFileFromDownloadAfterExcelUpdate(Filename, logger), "TC_26 - Ingestion of excel file got failed");
	    	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_26 - LOMT logo was not clicked");
	    	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_26 - Higher Education link is not clickable");
	    	 Assert.assertTrue(heHomePage.clickEducationObjective(logger), "TC_26 - Educational Objective was not clicked");
	    	 Assert.assertTrue(schoolHomePage.BrowseData(HEConstant.Title, "Anatomy & Physiology", logger), "TC_26 - Educational Objective Filter is not working/");
	    	 reports.endTest(logger);
	    	 logger = reports.startTest("Smoke_TC_27_Verify Expand and Collapse");
	    	 Assert.assertTrue(heEducational.SelectNode(HEConstant.Title, logger), "TC_27 - Select Node is not working/");
	         Assert.assertTrue(heHomePage.expandCollapseParentCurriculumNode(true, logger), "TC_27 - Node was not expanded");       
	         Assert.assertTrue(heHomePage.expandCollapseParentCurriculumNode(false, logger), "TC_27 - Node was not collapsed");
			 reports.endTest(logger);
			 logger = reports.startTest("Smoke_TC_28_Verify Edit in Higher Ed EO");
			 Assert.assertTrue(heEducational.BrowseDataEO("Analyze how cohorts influence development.","", logger), "TC_28 - Educational Objective Filter is not working/");
			 Assert.assertTrue(heEducational.clickChildNodeForEdit("Analyze how cohorts influence development.", logger), "TC_28 - Selecting Child Node is not working/"); 
			 Assert.assertTrue(heEducational.clickEditinHE(logger), "TC_28 - Edit button was not clicked");
			 Assert.assertTrue(heEducational.updateValueinEditHE("Updated", logger), "TC_28 - Edit and Save is not working");
			 reports.endTest(logger);
			 }catch(AssertionError e){
	        	 logger.log(LogStatus.WARNING, "Test scripts got failed");
	        	 reports.endTest(logger);
	        	 String[] getFailedTC = e.getLocalizedMessage().split("-");
	        	 for(int i=0;i<tcCovered.length;i++){
	        		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
	        			 i++;
	        			 while(i<tcCovered.length){
	        				 logger = reports.startTest("Smoke "+tcCovered[i]);
	        				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
	        				 reports.endTest(logger);
	        				 i++;
	        			 }
	        			 break;
	        		 }
	        	 }			
	        	 Assert.fail();
	         }catch(Exception e){
	        	 reports.endTest(logger);
	        	 e.printStackTrace();
	         }
		}
		
	// Scenario 7
	
	@Test(priority=7)
	public void ingestGSEFile(){
		String[] tcCovered = {"TC_29","TC_30","TC_31","TC_32","TC_33","TC_34","TC_35"};
		String desId = null;
		String gseCurriculum = null;
		try{
			logger = reports.startTest("Smoke_TC_29_Verify English LOB link is clickable");
		Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_29 - Login Failed");
		Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_29 - English LOB was not clicked");
		reports.endTest(logger);
		logger = reports.startTest("Smoke_TC_30_Verify all structures under English are displayed");
		Assert.assertTrue(engHomePage.verifyAllStructures(logger), "TC_30 - All structures under school global are not enabled");
		reports.endTest(logger);
		logger = reports.startTest("Smoke_TC_31_Verify all structures under English are clickable");
		Assert.assertTrue(engHomePage.clickGSE(logger),"TC_31 - GSE structure was not clicked");
		Assert.assertTrue(engHomePage.clickEnglishBreadCrumb(logger),"TC_31 - English Breadcrumb was not clicked");
		Assert.assertTrue(engHomePage.clickExternalFramework(logger),"TC_31 - External framework structure was not clicked");
		Assert.assertTrue(engHomePage.clickEnglishBreadCrumb(logger),"TC_31 - English Breadcrumb was not clicked");
		Assert.assertTrue(engHomePage.clickProduct(logger),"TC_31 - Product structure was not clicked");
		Assert.assertTrue(engHomePage.clickEnglishBreadCrumb(logger),"TC_31 - English Breadcrumb was not clicked");
		reports.endTest(logger);
		logger = reports.startTest("Smoke_TC_32_GSE Ingestion");
		Assert.assertTrue(engHomePage.clickManageIngestion(logger), "TC_32 - Manage Ingestion link was not clicked");
		Assert.assertTrue(engHomePage.selectEnglishLOBRadio(logger),"TC_32 - English radio was not clicked");
		Assert.assertTrue(engHomePage.selectGSERadio(logger),"TC_32 - GSE radio was not clicked");
		Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_32 - Next button was not clicked");
		Assert.assertTrue(engHomePage.uploadFile(EnglishTestData.GSE_File, logger),"TC_32 - GSE Ingestion got failed");
		 reports.endTest(logger);
		 logger = reports.startTest("Smoke_TC_33_Verify GSE filter");
		 Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"TC_33 - LOMT logo was not clicked");		
		 Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_33 - English LOB was not clicked");
		 Assert.assertTrue(engHomePage.clickGSE(logger),"TC_33 - GSE link was not clicked");
		 Assert.assertTrue(engGse.clickActions(logger),"TC_33 - Click Actions was not clicked");
		 Assert.assertTrue(engGse.clickBrowse(logger),"TC_33 - Click browse was not clicked");
		 Assert.assertTrue(engGse.clearFilter(logger),"TC_33 - Clear filter was not clicked");
		 desId = engGse.getValuesExcel("src/main/java/lomt/pearson/fileupload/Ingestion/Files/Gse_Ingestion_Template.xlsx","data",1,1,logger);
		Assert.assertNotNull(desId, "TC_33 - Description Id was not taken from Excel");
		 Assert.assertTrue(engGse.searchGSEDescriptiveId(desId, logger),"TC_33 - Searched data is not displayed");
		 reports.endTest(logger);
		 logger = reports.startTest("Smoke_TC_34_Verify GSE is exportable");
		 Assert.assertTrue(engGse.clickGSECheckBox(desId, logger),"TC_34 - Check box was not clicked");
		 Assert.assertTrue(engGse.clickExportButton(logger), "TC_34 - Export button was not clicked");
		 reports.endTest(logger);
		 logger = reports.startTest("Smoke_TC_35_Verify GSE is clickable");
		 gseCurriculum = engGse.getValuesExcel("src/main/java/lomt/pearson/fileupload/Ingestion/Files/Gse_Ingestion_Template.xlsx", "data", 1, 7, logger);
		Assert.assertNotNull(gseCurriculum, "TC_35 - GSE Node is not taken from Excel");
		Assert.assertTrue(engGse.clickGSENode(gseCurriculum, logger),"TC_35 - GSE Node was not clicked");
		reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
				}
			}			
			Assert.fail();
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
		
	}
//Scenario 8
	@Test(priority =8)
	public void reingestGSE(){
		String[] tcCovered = {"TC_36","TC_37","TC_38"};
		String desId = null;
		String gseCurriculum = null;
		try{		
			logger = reports.startTest("Smoke_TC_36_GSE Reingestion");
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_36 - Login Failed");
			Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_36 - English LOB was not clicked");
			Assert.assertTrue(engHomePage.clickManageIngestion(logger), "TC_36 - Manage Ingestion link was not clicked");
			Assert.assertTrue(engHomePage.selectEnglishLOBRadio(logger),"TC_36 - English radio was not clicked");
			Assert.assertTrue(engHomePage.selectGSERadio(logger),"TC_36 - GSE radio was not clicked");
			Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_36 - Next button was not clicked");
			Assert.assertTrue(engHomePage.uploadFile(EnglishTestData.GSE_Reingest_File, logger),"TC_36 - GSE Ingestion got failed");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_37_Verify Actions in GSE");
			Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"TC_37 - LOMT logo was not clicked");		
			 Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_37 - English LOB was not clicked");
			 Assert.assertTrue(engHomePage.clickGSE(logger),"TC_37 - GSE link was not clicked");
			 Assert.assertTrue(engGse.clickActions(logger),"TC_37 - Click Actions was not clicked");
			Assert.assertTrue(engGse.verifyGSEActionButtons(logger),"TC_37 - GSE Action buttons are not displayed");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_38_Verify ingested data");
			 Assert.assertTrue(engGse.clickBrowse(logger),"TC_38 - Click browse was not clicked");
			 Assert.assertTrue(engGse.clearFilter(logger),"TC_38 - Clear filter was not clicked");
			 desId = engGse.getValuesExcel("src/main/java/lomt/pearson/fileupload/Ingestion/Files/GSERe-ingestion.xlsx","data",1,1,logger);
			 Assert.assertNotNull(desId, "TC_38 - Description Id was not taken from Excel");
			 Assert.assertTrue(engGse.searchGSEDescriptiveId(desId, logger),"TC_38 - Searched data is not displayed");
			 gseCurriculum = engGse.getValuesExcel("src/main/java/lomt/pearson/fileupload/Ingestion/Files/GSERe-ingestion.xlsx", "data", 1, 7, logger);
			 Assert.assertNotNull(gseCurriculum, "TC_38 - GSE Node is not taken from Excel");
			 Assert.assertTrue(engGse.clickGSENode(gseCurriculum, logger),"TC_38 - GSE Node was not clicked");
			 reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
				}
			}			
			Assert.fail();
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
		}
	
//Scenario 9
	
	@Test(priority =9)
	public void AlignmentCirr_To_Cirr(){
		String[] tcCovered = {"TC_39","TC_40","TC_41"};
		try{
			logger = reports.startTest("Smoke_TC_39_Create_Aligment_Cirrculum_to_Cirrculum");
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_39 - Login Failed");
			
			Assert.assertTrue(homePage.selectSchoolLOB(logger),"TC_39 - School Global link is not clickable");
			Assert.assertTrue(schoolHomePage.clickStandardCurriculum(logger), "TC_39 - Standard Curriculum was not clicked");
			Assert.assertTrue(schoolHomePage.BrowseData("","" , logger),"TC_39 - Filter option was not set");
			Assert.assertTrue(schoolCurriculum.VerifyRecord(1,logger),"TC_39 - Frist Record is not present");
			String Fristrecord = schoolCurriculum.ReturnRecord(1,logger);
			Assert.assertTrue(schoolHomePage.clickActions(logger),"TC_39 - Click Actions");
			Assert.assertTrue(schoolHomePage.clickAlign(logger),"TC_39 - Click Align");
			Assert.assertTrue(schoolHomePage.clickStandardCurriculum(logger), "TC_39 - Standard Curriculum was not clicked");
			Assert.assertTrue(schoolHomePage.BrowseData("","" , logger),"TC_39 - Filter option was not set");
			Assert.assertTrue(schoolCurriculum.VerifyRecord(2,logger),"TC_39 - Second Record is not present");
			String secondrecord = schoolCurriculum.ReturnRecord(2,logger);
			Assert.assertNotNull(secondrecord,"TC_39 - Second Record is not present");
			Assert.assertTrue(schoolHomePage.BrowseData(secondrecord.split("\\(")[0].trim(),"" , logger),"TC_39 - Search Record is not present");
			Assert.assertTrue(schoolCurriculum.VerifyRecord(1,logger),"TC_39 - Frist Record is not present");
			Assert.assertTrue(schoolCurriculum.ClickSelectforAligment(logger),"TC_39 - SelectforAligment was not clicked");
			Assert.assertTrue(schoolCurriculum.SelectEducationalGoals(logger),"TC_39 - Select educational goals was not clicked");
			Assert.assertTrue(schoolHomePage.clickAlignAction(logger),"TC_39 - Align Action was not clicked");
			Assert.assertTrue(schoolCurriculum.ClickAlignExcat(logger),"TC_39 -  Align Excat was not clicked");
			Assert.assertTrue(schoolCurriculum.VerifyAlignmentInformation(Fristrecord.split("\\(")[0].trim(),secondrecord.split("\\(")[0].trim(),"EXACT",logger),"TC_39 -  Align Excat was not clicked");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_40_Edit_Aligment_Cirrculum_to_Cirrculum");
			Assert.assertTrue(schoolCurriculum.EditAlignment("Narrow",logger),"TC_40 -  Not able to Edit Alignment");
			reports.endTest(logger);
			
			logger = reports.startTest("Smoke_TC_41_AddNote_Aligment_Cirrculum_to_Cirrculum");
			Assert.assertTrue(schoolCurriculum.AddNote(logger),"TC_41 -  Not able to add note");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
					
				}
			}			
			Assert.fail();
			
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
		
	}

//Scenario 10
	
	@Test(priority =10)
	public void AlignmentHed_To_ExFrame(){
		String[] tcCovered = {"TC_42","TC_43","TC_44"};
		try{
			logger = reports.startTest("Smoke_TC_42_Create_Aligment_HigherEducation_to_ExternalFramework");
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_42 - Login Failed");			
			Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_42 - Higher education link is not clickable");
			Assert.assertTrue(heHomePage.clickExternalFramework(logger), "TC_42 - External Framework was not clicked");
			Assert.assertTrue(heHomePage.BrowseData("","" , logger),"TC_42 - Filter option was not set");
			Assert.assertTrue(heHomePage.VerifyRecord(1,logger),"TC_42 - Frist Record is not present");
			String Fristrecord = heHomePage.ReturnRecord(1,logger);
			Assert.assertTrue(heHomePage.clickActions(logger),"TC_42 - Click Actions");
			Assert.assertTrue(heHomePage.clickAlign(logger),"TC_42 - Click Align");
			Assert.assertTrue(heHomePage.clickEducationalObjective(logger), "TC_42 - Educational Objective was not clicked");			
			String secondrecord = heHomePage.ReturnRecord(1,logger);			
			Assert.assertTrue(heHomePage.ClickSelectforAligment(logger),"TC_42 - SelectforAligment was not clicked");
			Assert.assertTrue(heHomePage.SelectEducationalGoals(logger),"TC_42 - Nodes were not Selected");
			Assert.assertTrue(heHomePage.clickAlignAction(logger),"TC_42 - Align Action was not clicked");
			Assert.assertTrue(heHomePage.ClickAlinged(logger),"TC_42 -  Alinged was not clicked");
			Assert.assertTrue(heHomePage.VerifyAlignmentInformation(Fristrecord.split("\\(")[0].trim(),secondrecord.split("\\(")[0].trim(),"ALIGNED",logger),"TC_42 -  Align CENTRAL was not matched");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_43_Edit_Aligment_HigherEducation_to_ExternalFramework");
			Assert.assertTrue(heHomePage.EditAlignment("Aligned",logger),"TC_43 -  Not able to Edit Alignment");
			reports.endTest(logger);
			
			logger = reports.startTest("Smoke_TC_44_AddNote_Aligment_HigherEducation_to_ExternalFramework");
			Assert.assertTrue(heHomePage.AddNote(logger),"TC_44 -  Not able to add note");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
					
				}
			}			
			Assert.fail();
			
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
		
	}

	
//Scenario 11

	@Test(priority =11)
	public void AlignmentGSE_To_GSE(){
		String[] tcCovered = {"TC_45","TC_46","TC_47"};
		try{
			logger = reports.startTest("Smoke_TC_45_Create_Aligment_GSE_to_GSE");
			Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_45 - Login Failed");
			
			Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_45 - English link is not clickable");
			Assert.assertTrue(engHomePage.clickGSE(logger), "TC_45 - GSE was not clicked");			
			Assert.assertTrue(engGse.VerifyRecord(1,logger),"TC_45 - Frist Record is not present");
			String Fristrecord = engGse.ReturnRecord(1,logger);
			Assert.assertTrue(schoolHomePage.clickActions(logger),"TC_45 - Click Actions");
			Assert.assertTrue(schoolHomePage.clickAlign(logger),"TC_45 - Click Align");
			Assert.assertTrue(engHomePage.clickGSE(logger), "TC_45 - GSE was not clicked");			
			String secondrecord = engGse.ReturnRecord(1,logger);
			Assert.assertTrue(engGse.ClickSelectforAligment(logger),"TC_45 - SelectforAligment was not clicked");
			Assert.assertTrue(engGse.SelectEducationalGoals(logger),"TC_45 - Nodes were not Selected");
			Assert.assertTrue(engGse.clickAlignAction(logger),"TC_45 - Align Action was not clicked");
			Assert.assertTrue(engGse.ClickAlignGood(logger),"TC_45 -  Align Good was not clicked");
			Assert.assertTrue(engGse.VerifyAlignmentInformation(Fristrecord.split("\\(")[0].trim(),secondrecord.split("\\(")[0].trim(),"GOOD",logger),"TC_45 -  Align GOOD was not matched");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_46_Edit_Aligment_GSE_to_GSE");
			Assert.assertTrue(engGse.EditAlignment("Close",logger),"TC_46 -  Not able to Edit Alignment");
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_47_AddNote_Aligment_GSE_to_GSE");
			Assert.assertTrue(engGse.AddNote(logger),"TC_47 -  Not able to add note");
			reports.endTest(logger);
		}catch(AssertionError e){
			logger.log(LogStatus.WARNING, "Test scripts got failed");
			reports.endTest(logger);
			String[] getFailedTC = e.getLocalizedMessage().split("-");
			for(int i=0;i<tcCovered.length;i++){
				if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
					i++;
					while(i<tcCovered.length){
						logger = reports.startTest("Smoke "+tcCovered[i]);
						logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
						reports.endTest(logger);
						i++;
					}
					break;
					
				}
			}			
			Assert.fail();
			
		}catch(Exception e){
			reports.endTest(logger);
			e.printStackTrace();
		}
		
	}
	//Scenario 12
@Test(priority = 12)
public void ingestSchoolProduct(){
	   String[] tcCovered = {"TC_48","TC_49"};
	   
	   try{
		   logger = reports.startTest("Smoke_TC_48_Ingest School Product");
		   Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_48 - Login Failed");			
			Assert.assertTrue(homePage.selectSchoolLOB(logger), "TC_48 - School LOB was not clicked");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger),"TC_48 - Manage Ingestion was not clicked");
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_48 - Select School LOB radio was not clicked");
			Assert.assertTrue(schoolHomePage.selectProductRadio(logger),"TC_48 - Select Product radio was not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_48 - Click Next button was not clicked");
			Assert.assertTrue(schoolHomePage.setSchoolProductMetadata(SchoolConstant.Ingestion_Subject, SchoolConstant.Approved_Status, logger),"TC_48 - School Product Metadata was not set");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_48 - Click Next button was not clicked");
			String productUpdatedValue = schoolHomePage.updateExcelValue(SchoolConstant.SchoolProductXLSXFile,"Sheet1",2,1,"SchoolProduct_", logger);
			Assert.assertNotNull(productUpdatedValue,"TC_48 - Product value was not updated");
			Assert.assertTrue(schoolHomePage.uploadFile(SchoolConstant.SchoolProductXLSXFile, logger), "TC_48 - Ingestion of Product xlsx file got failed");	
			reports.endTest(logger);
			logger = reports.startTest("Smoke_TC_49_Edit the Ingested Product");
			Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_49 - LOMT logo was not clicked");
			Assert.assertTrue(homePage.selectSchoolLOB(logger), "TC_49 - School LOB was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolProduct(logger),"TC_49 - School Product was not clicked");
			Assert.assertTrue(schoolProduct.BrowseData(productUpdatedValue, "", logger), "TC_49 - School product is not available");
			Assert.assertTrue(schoolProduct.clickSchoolProduct(productUpdatedValue, logger),"TC_49 - School product was not clicked");
			String parentNodeValue = schoolProduct.getValuesExcel("src/main/java/lomt/pearson/fileupload/Ingestion/Files/SchoolProduct.xlsx", "Sheet1", 12, 3, logger);
			Assert.assertNotNull(parentNodeValue, "TC_49 - Parent Node value was not obtained");
			Assert.assertTrue(schoolProduct.clickParentNode(parentNodeValue, logger),"TC_49 - Parent node was not clicked");			
			Assert.assertTrue(schoolProduct.clickEditButton(logger), "TC_49 - Edit button was not clicked"); 
			Assert.assertTrue(schoolProduct.updateLevelTitle(SchoolConstant.SchoolProduct_Updated, logger),"TC_49 - Value was not updated properly");
			Assert.assertEquals(schoolProduct.getLevelTitle(logger), SchoolConstant.SchoolProduct_Updated,"TC_49 - Value is not matching with the updated value");
			reports.endTest(logger);
	   }catch(AssertionError e){
      	 logger.log(LogStatus.WARNING, "Test scripts got failed");
      	 reports.endTest(logger);
      	 String[] getFailedTC = e.getLocalizedMessage().split("-");
      	 for(int i=1;i<tcCovered.length;i++){
      		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
      			 i++;
      			 while(i<tcCovered.length){
      				 logger = reports.startTest("Smoke "+tcCovered[i]);
      				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
      				 reports.endTest(logger);
      				 i++;
      			 }
      			 break;
      		 }
      	 }			
      	 Assert.fail();
       }catch(Exception e){
      	 reports.endTest(logger);
      	 e.printStackTrace();
       }
	   
}
	
	////Scenario - 13
@Test(priority =13)
public void ingestionHEProductandEdit(){
    String[] tcCovered = {"TC_50","TC_51"};
     try {
    	 logger = reports.startTest("Smoke_TC_50_Ingestion of HE Product");
     	 Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_50 - Login Failed");    	 
    	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_50 - Higher Education link is not clickable");
    	 Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_50 - Higher Education Manage Ingestion link was not clicked");
    	 Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_50 - Higher Education LOB Radio is not clicked");
    	 Assert.assertTrue(heHomePage.selectProductRadio(logger),"TC_50 - Product Radio is not clicked");
    	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_50 - Next button was not clicked");
    	 Assert.assertTrue(heHomePage.setCurriculumMetaData(logger, HEConstant.Title, HEConstant.A, HEConstant.AWAITING_APPROVAL), "TC_50 - Curriculum Meta data was not set");
    	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_50 - Next button was not clicked");
    	 Assert.assertTrue(heHomePage.uploadFile(HEConstant.ProductExcelFile, logger), "TC_50 - Ingestion of excel file got failed");
    	 reports.endTest(logger);
    	 logger = reports.startTest("Smoke_TC_51_Edit the ingested HE product");
    	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_51 - LOMT logo was not clicked");
    	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_51 - Higher Education link is not clickable");
    	 Assert.assertTrue(heHomePage.clickHeProduct(logger), "TC_51 - Product was not clicked");
    	 Assert.assertTrue(heHomePage.BrowseDataHEProduct(HEConstant.Title, "Anatomy & Physiology", logger), "TC_51 - Product Filter is not working/");
    	 Assert.assertTrue(heEducational.SelectNode("Feldman_Product_TOC_25Feb-1", logger), "TC_51 - Select Node is not working/");
    	 Assert.assertTrue(heEducational.clickChildNodeForEditinHEProduct("US DNealian 2008 Student Edition", logger), "TC_51 - Selecting Child Node is not working/"); 
		 Assert.assertTrue(heEducational.clickEditinHEProduct(logger), "TC_51 - Edit button was not clicked");
		 Assert.assertTrue(heEducational.updateValueinEditHEProduct("Updated", logger), "TC_51 - Edit and Save is not working");
		 reports.endTest(logger);
		 
		 }catch(AssertionError e){
        	 logger.log(LogStatus.WARNING, "Test scripts got failed");
        	 reports.endTest(logger);
        	 String[] getFailedTC = e.getLocalizedMessage().split("-");
        	 for(int i=0;i<tcCovered.length;i++){
        		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
        			 i++;
        			 while(i<tcCovered.length){
        				 logger = reports.startTest("Smoke "+tcCovered[i]);
        				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
        				 reports.endTest(logger);
        				 i++;
        			 }
        			 break;
        		 }
        	 }			
        	 Assert.fail();
         }catch(Exception e){
        	 reports.endTest(logger);
        	 e.printStackTrace();
         }
	}	

// Scenario 14

@Test(priority =14)
public void englishProductIngestion(){
	   String[] tcCovered = {"TC_52","TC_53"};
	   try{
		   logger = reports.startTest("Smoke_TC_52_Ingest English Product");
		   Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_52 - Login Failed");		   
		   Assert.assertTrue(homePage.selectEnglishLOB(logger), "TC_52 - English LOB was not clicked");
		   Assert.assertTrue(engHomePage.clickManageIngestion(logger),"TC_52 - Manage Ingestion was not clicked");		
		   Assert.assertTrue(engHomePage.selectEnglishLOBRadio(logger),"TC_52 - English LOB Radio was not clicked");
		   Assert.assertTrue(engHomePage.selectEngProductRadio(logger), "TC_52 - English Product Radio was not clicked");
		   Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_52 - Next button was not clicked");
		   Assert.assertTrue(engHomePage.setEnglishProductMetadata(EnglishTestData.Eng_Ingest_EFL_Status, EnglishTestData.Eng_Ingest_Product_Draft_Status, logger),"TC_52 - Product Metadata was not set ");
		   Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_52 - Next button was not clicked");		   
		   String productUpdatedValue = engHomePage.updateExcelValue(EnglishTestData.Product_Ingest_File,"Sheet1",2,1,"EngProduct_", logger);
		   Assert.assertNotNull(productUpdatedValue,"TC_52 - Product value was not updated");
		   Assert.assertTrue(engHomePage.uploadFile(EnglishTestData.Product_Ingest_File, logger),"TC_52 - Ingestion of English product got failed");
		   reports.endTest(logger);
		   logger = reports.startTest("Smoke_TC_53_Edit the Ingested Product");
		   Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"TC_53 - LOMT logo was not clicked");
		   Assert.assertTrue(homePage.selectEnglishLOB(logger), "TC_53 - English LOB was not clicked");
		   Assert.assertTrue(engHomePage.clickProduct(logger),"TC_53 - English Product was not clicked");
		   Assert.assertTrue(engProduct.BrowseData(productUpdatedValue, "", logger),"TC_53 - Product was not filtered");
		   Assert.assertTrue(engProduct.clickEnglishProduct(productUpdatedValue, logger),"TC_53 - Product is not clickable");
		   String productNodeValue = engProduct.getValuesExcel("src/main/java/lomt/pearson/fileupload/Ingestion/Files/EngProduct.xlsx", "Sheet1", 12, 3, logger);
		   Assert.assertNotNull(productNodeValue,"TC_53 - Product node was not found");
		   Assert.assertTrue(engProduct.clickParentNode(productNodeValue, logger),"TC_53 - Parent node was not clicked");
		   Assert.assertTrue(engProduct.clickEditButton(logger),"TC_53 - Edit button was not clicked");
		   Assert.assertTrue(engProduct.updateLevelTitle(EnglishTestData.Eng_Product_UpdatedValue, logger),"TC_53 - Level title was not updated");
		   Assert.assertEquals(engProduct.getLevelTitle(logger), EnglishTestData.Eng_Product_UpdatedValue,"TC_53 - Updated value doesn't match");
		   reports.endTest(logger);
	   }catch(AssertionError e){
      	 logger.log(LogStatus.WARNING, "Test scripts got failed");
      	 reports.endTest(logger);
      	 String[] getFailedTC = e.getLocalizedMessage().split("-");
      	 for(int i=0;i<tcCovered.length;i++){
      		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
      			 i++;
      			 while(i<tcCovered.length){
      				 logger = reports.startTest("Smoke "+tcCovered[i]);
      				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
      				 reports.endTest(logger);
      				 i++;
      			 }
      			 break;
      		 }
      	 }			
      	 Assert.fail();
       }catch(Exception e){
      	 reports.endTest(logger);
      	 e.printStackTrace();
       }
}

// Scenario 15

@Test(priority=15)
public void reingestSchoolProduct(){
	   String[] tcCovered = {"TC_54"};
	   try{
		   logger = reports.startTest("Smoke_TC_54_Reingest School Product");
		   Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_54 - Login Failed");		  	 
		  	Assert.assertTrue(homePage.selectSchoolLOB(logger), "TC_54 - School LOB was not clicked");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger),"TC_54 - Manage Ingestion was not clicked");			
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_54 - Select School LOB radio was not clicked");
			Assert.assertTrue(schoolHomePage.selectProductRadio(logger),"TC_54 - Select Product radio was not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_54 - Click Next button was not clicked");
			Assert.assertTrue(schoolHomePage.setSchoolProductMetadata(SchoolConstant.Ingestion_Subject, SchoolConstant.Approved_Status, logger),"TC_54 - School Product Metadata was not set");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_54 - Click Next button was not clicked");
			String productUpdatedValue = schoolHomePage.updateExcelValue(SchoolConstant.SchoolProductXLSXFile,"Sheet1",2,1,"SchoolProduct_", logger);
			Assert.assertNotNull(productUpdatedValue,"TC_54 - Product value was not updated");
			Assert.assertTrue(schoolHomePage.uploadFile(SchoolConstant.SchoolProductXLSXFile, logger), "TC_54 - Ingestion of Product xlsx file got failed");	
			Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_54 - LOMT logo was not clicked");
			Assert.assertTrue(homePage.selectSchoolLOB(logger), "TC_54 - School LOB was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolProduct(logger),"TC_54 - School Product was not clicked");
			Assert.assertTrue(schoolProduct.BrowseData(productUpdatedValue, "", logger), "TC_54 - School product is not available");
			Assert.assertTrue(schoolProduct.clickActions(logger),"TC_54 - Actions was not clicked");
			Assert.assertTrue(schoolProduct.exportActions(logger),"TC_54 - Click on Exports was not clicked");
			String fileName = HEEducationalObjective.getTheNewestFile();
			Assert.assertNotNull(fileName,"TC_54 - Downloaded file is not found");
			String updatedValue = schoolProduct.updateExcelValueFromDownload(fileName, "Sheet1", 12, 3,SchoolConstant.P_NODE_DES_1 , logger);
			Assert.assertNotNull(updatedValue,"TC_54 - Updated value is null");
			Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_54 - LOMT logo was not clicked");
			Assert.assertTrue(homePage.selectSchoolLOB(logger), "TC_54 - School LOB was not clicked");
			Assert.assertTrue(schoolHomePage.clickManageIngestion(logger),"TC_54 - Manage Ingestion was not clicked");
			Assert.assertTrue(schoolHomePage.selectSchoolLOBRadio(logger),"TC_54 - Select School LOB radio was not clicked");
			Assert.assertTrue(schoolHomePage.selectProductRadio(logger),"TC_54 - Select Product radio was not clicked");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_54 - Click Next button was not clicked");
			Assert.assertTrue(schoolHomePage.setSchoolProductMetadata(SchoolConstant.Ingestion_Subject, SchoolConstant.Approved_Status, logger),"TC_54 - School Product Metadata was not set");
			Assert.assertTrue(schoolHomePage.clickNextButton(logger),"TC_54 - Click Next button was not clicked");
			Assert.assertTrue(schoolHomePage.uploadFileFromDownloadAfterExcelUpdate(fileName, logger),"TC_54 - Reingestion got failed");
			Assert.assertTrue(schoolHomePage.clickLOMTLogo(logger), "TC_54 - LOMT logo was not clicked");
			Assert.assertTrue(homePage.selectSchoolLOB(logger), "TC_54 - School LOB was not clicked");
			Assert.assertTrue(schoolHomePage.clickSchoolProduct(logger),"TC_54 - School Product was not clicked");
			Assert.assertTrue(schoolProduct.BrowseData(productUpdatedValue, "", logger), "TC_54 - School product is not available");
			Assert.assertTrue(schoolProduct.clickSchoolProduct(productUpdatedValue, logger),"TC_54 - School product was not clicked");
			Assert.assertTrue(schoolProduct.clickParentNode(updatedValue, logger),"TC_54 - Updated Parent node was not clicked");
			reports.endTest(logger);
		  	 
	   }catch(AssertionError e){
	      	 logger.log(LogStatus.WARNING, "Test scripts got failed");
	      	 reports.endTest(logger);
	      	 String[] getFailedTC = e.getLocalizedMessage().split("-");
	      	 for(int i=0;i<tcCovered.length;i++){
	      		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
	      			 i++;
	      			 while(i<tcCovered.length){
	      				 logger = reports.startTest("Smoke "+tcCovered[i]);
	      				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
	      				 reports.endTest(logger);
	      				 i++;
	      			 }
	      			 break;
	      		 }
	      	 }			
	      	 Assert.fail();
	       }catch(Exception e){
	      	 reports.endTest(logger);
	      	 e.printStackTrace();
	       }
}

//Scenario-16
@Test(priority =16)
public void reingestionHEProduct(){
	String getEducationalObjectiveValue;
  String getEducationalObjectiveValueafterdownload;
  String Filename;
  String[] tcCovered = {"TC_55"};
   try {
	   logger = reports.startTest("Smoke_TC_55_Reingestion of HE Product");
     Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_55 - Login Failed");  	 
  	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_55 - Higher Education link is not clickable");
  	 Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_55 - Higher Education Manage Ingestion link was not clicked");
  	 Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_55 - Higher Education LOB Radio is not clicked");
  	 Assert.assertTrue(heHomePage.selectProductRadio(logger),"TC_55 - Product Radio is not clicked");
  	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_55 - Next button was not clicked");
  	 Assert.assertTrue(heHomePage.setCurriculumMetaData(logger, HEConstant.Title, HEConstant.A, HEConstant.AWAITING_APPROVAL), "TC_55 - Curriculum Meta data was not set");
  	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_55 - Next button was not clicked");
  	 Assert.assertTrue(heHomePage.uploadFile(HEConstant.ProductExcelFile, logger), "TC_55 - Ingestion of excel file got failed");
  	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_55 - LOMT logo was not clicked");
  	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_55 - Higher Education link is not clickable");
  	 Assert.assertTrue(heHomePage.clickHeProduct(logger), "TC_55 - Product was not clicked");
  	 Assert.assertTrue(heHomePage.BrowseDataHEProduct(HEConstant.Title, "Anatomy & Physiology", logger), "TC_55 - Product Filter is not working/");
  	 Assert.assertTrue(heEducational.clickAction(logger), "TC_55 - Actions is not working/");
  	 Assert.assertTrue(heEducational.exportClickHEProduct(logger), "TC_55 - Export button is not working");
  	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_55 - LOMT logo was not clicked");
  	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_55 - Higher Education link is not clickable");
  	 Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_55 - Higher Education Manage Ingestion link was not clicked");
  	 Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_55 - Higher Education LOB Radio is not clicked");
  	 Assert.assertTrue(heHomePage.selectProductRadio(logger),"TC_55 - Product Radio is not clicked");
  	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_55 - Next button was not clicked");
  	 Assert.assertTrue(heHomePage.setCurriculumMetaData(logger, HEConstant.Title, HEConstant.A, HEConstant.AWAITING_APPROVAL), "TC_55 - Curriculum Meta data was not set");
  	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_55 - Next button was not clicked");
  	 Filename = HEEducationalObjective.getTheNewestFile();
  	 Assert.assertNotNull(Filename,"TC_55 - Getting Latest File is not working");
      getEducationalObjectiveValueafterdownload = heHomePage.updateExcelValueFromDownload(Filename,"Sheet1",12,3,"Analyze",logger);
  	 Assert.assertNotNull(getEducationalObjectiveValueafterdownload,"TC_55 - Excel update is not working");
  	 Assert.assertTrue(heHomePage.uploadFileFromDownloadAfterExcelUpdate(Filename, logger), "TC_55 - Ingestion of excel file got failed");
  	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_55 - LOMT logo was not clicked");
  	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_55 - Higher Education link is not clickable");
  	 Assert.assertTrue(heHomePage.clickHeProduct(logger), "TC_55 - Product was not clicked");
  	 Assert.assertTrue(heHomePage.BrowseDataHEProduct(HEConstant.Title, "Anatomy & Physiology", logger), "TC_55 - Product Filter is not working/");
  	 Assert.assertTrue(heEducational.SelectNode("Feldman_Product_TOC_25Feb-1", logger), "TC_55 - Select Node is not working/");
  	 Assert.assertTrue(heEducational.BrowseDataEO(getEducationalObjectiveValueafterdownload,"", logger), "TC_55 - Product Search Result is not working/");
  	 reports.endTest(logger);
  	}catch(AssertionError e){
      	 logger.log(LogStatus.WARNING, "Test scripts got failed");
      	 reports.endTest(logger);
      	 String[] getFailedTC = e.getLocalizedMessage().split("-");
      	 for(int i=0;i<tcCovered.length;i++){
      		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
      			 i++;
      			 while(i<tcCovered.length){
      				 logger = reports.startTest("Smoke "+tcCovered[i]);
      				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
      				 reports.endTest(logger);
      				 i++;
      			 }
      			 break;
      		 }
      	 }			
      	 Assert.fail();
       }catch(Exception e){
      	 reports.endTest(logger);
      	 e.printStackTrace();
       }
	}	
// Scenario - 17
@Test(priority = 17)
public void reingestEngProduct(){
	String[] tcCovered = {"TC_56"};
	try{
		logger = reports.startTest("Smoke_TC_56_Reingest English Product");
		 Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_56 - Login Failed");	  
		 
		 Assert.assertTrue(homePage.selectEnglishLOB(logger), "TC_56 - English LOB was not clicked");
		   Assert.assertTrue(engHomePage.clickManageIngestion(logger),"TC_56 - Manage Ingestion was not clicked");		
		   Assert.assertTrue(engHomePage.selectEnglishLOBRadio(logger),"TC_56 - English LOB Radio was not clicked");
		   Assert.assertTrue(engHomePage.selectEngProductRadio(logger), "TC_56 - English Product Radio was not clicked");
		   Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_56 - Next button was not clicked");
		   Assert.assertTrue(engHomePage.setEnglishProductMetadata(EnglishTestData.Eng_Ingest_EFL_Status, EnglishTestData.Eng_Ingest_Product_Draft_Status, logger),"TC_56 - Product Metadata was not set ");
		   Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_56 - Next button was not clicked");		   
		   String productUpdatedValue = engHomePage.updateExcelValue(EnglishTestData.Product_Ingest_File,"Sheet1",2,1,"EngProduct_", logger);
		   Assert.assertNotNull(productUpdatedValue,"TC_56 - Product value was not updated");
		   Assert.assertTrue(engHomePage.uploadFile(EnglishTestData.Product_Ingest_File, logger),"TC_56 - Ingestion of English product got failed");
		   Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"TC_56 - LOMT logo was not clicked");
		   Assert.assertTrue(homePage.selectEnglishLOB(logger), "TC_56 - English LOB was not clicked");
		   Assert.assertTrue(engHomePage.clickProduct(logger),"TC_56 - English Product was not clicked");
		   Assert.assertTrue(engProduct.BrowseData(productUpdatedValue, "", logger),"TC_56 - Product was not filtered");
		   Assert.assertTrue(engProduct.clickActions(logger),"TC_56 - Actions was not clicked");
		   Assert.assertTrue(engProduct.exportActions(logger),"TC_56 - Click on Exports was not clicked");
		   String fileName = HEEducationalObjective.getTheNewestFile();
			Assert.assertNotNull(fileName,"TC_56 - Downloaded file is not found");
			String updatedValue = engProduct.updateExcelValueFromDownload(fileName, "Sheet1", 12, 3,EnglishTestData.Eng_Product_Reingested_Value , logger);
			Assert.assertNotNull(updatedValue,"TC_56 - Updated value is null");
			 Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"TC_56 - LOMT logo was not clicked");
			   Assert.assertTrue(homePage.selectEnglishLOB(logger), "TC_56 - English LOB was not clicked");
			   Assert.assertTrue(engHomePage.clickManageIngestion(logger),"TC_56 - Manage Ingestion was not clicked");		
			   Assert.assertTrue(engHomePage.selectEnglishLOBRadio(logger),"TC_56 - English LOB Radio was not clicked");
			   Assert.assertTrue(engHomePage.selectEngProductRadio(logger), "TC_56 - English Product Radio was not clicked");
			   Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_56 - Next button was not clicked");
			   Assert.assertTrue(engHomePage.setEnglishProductMetadata(EnglishTestData.Eng_Ingest_EFL_Status, EnglishTestData.Eng_Ingest_Product_Draft_Status, logger),"TC_56 - Product Metadata was not set ");
			   Assert.assertTrue(engHomePage.clickNextButton(logger),"TC_56 - Next button was not clicked");				   
			Assert.assertTrue(engHomePage.uploadFileFromDownloadAfterExcelUpdate(fileName, logger),"TC_56 - Reingestion of English is failed");
			  Assert.assertTrue(engHomePage.clickLOMTLogo(logger),"TC_56 - LOMT logo was not clicked");
			   Assert.assertTrue(homePage.selectEnglishLOB(logger), "TC_56 - English LOB was not clicked");
			   Assert.assertTrue(engHomePage.clickProduct(logger),"TC_56 - English Product was not clicked");
			   Assert.assertTrue(engProduct.BrowseData(productUpdatedValue, "", logger),"TC_56 - Product was not filtered");
			   Assert.assertTrue(engProduct.clickEnglishProduct(productUpdatedValue, logger), "TC_56 - Product is not clickable");
			Assert.assertTrue(engProduct.clickParentNode(updatedValue, logger),"TC_56 - Updated Parent node was not clicked");		   
		   reports.endTest(logger);
	}catch(AssertionError e){
   	 logger.log(LogStatus.WARNING, "Test scripts got failed");
   	 reports.endTest(logger);
   	 String[] getFailedTC = e.getLocalizedMessage().split("-");
   	 for(int i=0;i<tcCovered.length;i++){
   		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
   			 i++;
   			 while(i<tcCovered.length){
   				 logger = reports.startTest("Smoke "+tcCovered[i]);
   				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
   				 reports.endTest(logger);
   				 i++;
   			 }
   			 break;
   		 }
   	 }			
   	 Assert.fail();
    }catch(Exception e){
   	 reports.endTest(logger);
   	 e.printStackTrace();
    }
	
}
//Scenario-18
@Test(priority =18)
public void HEEFIngestionEdit(){
String[] tcCovered = {"TC_57","TC_58","TC_59"};
try {
	 logger = reports.startTest("Smoke_TC_57_Ingestion of HE External framework and search for the ingested framework using filter");
   Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_57 - Login Failed");
	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_57 - Higher Education link is not clickable");
	 Assert.assertTrue(heHomePage.clickManageIngestion(logger), "TC_57 - Higher Education Manage Ingestion link was not clicked");
	 Assert.assertTrue(heHomePage.selectHeLOBRadio(logger),"TC_57 - Higher Education LOB Radio is not clicked");
	 Assert.assertTrue(heHomePage.selectExternalFrameworkRadio(logger),"TC_57 - EF Radio is not clicked");
	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_57 - Next button was not clicked");
	 Assert.assertTrue(heHomePage.setCurriculumMetaDataHEEF(logger, HEConstant.Title, HEConstant.A), "TC_57 - Curriculum Meta data was not set");
	 Assert.assertTrue(heHomePage.clickNextButton(logger),"TC_57 - Next button was not clicked");
	 Assert.assertTrue(heHomePage.uploadFile(HEConstant.ExternalFrameworkExcelFile, logger), "TC_57 - Ingestion of excel file got failed");
	 Assert.assertTrue(heHomePage.clickLOMTLogo(logger), "TC_57 - LOMT logo was not clicked");
	 Assert.assertTrue(homePage.selectHigherEducationLOB(logger),"TC_57 - Higher Education link is not clickable");
	 Assert.assertTrue(heHomePage.clickExternalFramework(logger), "TC_57 - Product was not clicked");
	 Assert.assertTrue(heHomePage.BrowseDataHEExtermalFramework(HEConstant.Title, "Anatomy & Physiology", logger), "TC_57 - Product Filter is not working/");
	 Assert.assertTrue(heEducational.SelectNode("Demo for LOMT EF", logger), "TC_57 - Select Node is not working/");
	 reports.endTest(logger);
	 logger = reports.startTest("Smoke_TC_58_Exapnd and collapse HE External framework");
	 Assert.assertTrue(heHomePage.expandCollapseParentCurriculumNode(true, logger), "TC_58 - Node was not expanded");		
	 Assert.assertTrue(heHomePage.expandCollapseParentCurriculumNode(false, logger), "TC_58 - Node was not collapsed");
	 reports.endTest(logger);
	 logger = reports.startTest("Smoke_TC_59_Edit the HE External framework");
	 Assert.assertTrue(heEducational.clickChildNodeForEditinHEProduct("Core Sequence Example A", logger), "TC_59 - Selecting Child Node is not working/"); 
	 Assert.assertTrue(heEducational.clickEditinHEProduct(logger), "TC_59 - Edit button was not clicked");
	 Assert.assertTrue(heEducational.updateValueinEditHEEF("Updated", logger), "TC_59 - Edit and Save is not working");	
	 Assert.assertTrue(heEducational.VerifyupdateValueinEdit("Updated", logger), "TC_59 - Verify the saved text");
	 reports.endTest(logger);
	}catch(AssertionError e){
  	 logger.log(LogStatus.WARNING, "Test scripts got failed");
  	 reports.endTest(logger);
  	 String[] getFailedTC = e.getLocalizedMessage().split("-");
  	 for(int i=0;i<tcCovered.length;i++){
  		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
  			 i++;
  			 while(i<tcCovered.length){
  				 logger = reports.startTest("Smoke "+tcCovered[i]);
  				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
  				 reports.endTest(logger);
  				 i++;
  			 }
  			 break;
  		 }
  	 }			
  	 Assert.fail();
   }catch(Exception e){
  	 reports.endTest(logger);
  	 e.printStackTrace();
   }
	}	

//Scenario 19

@Test(priority =19)
public void EnglishEFIngestionEdit(){
String[] tcCovered = {"TC_60","TC_61","TC_62"};
try {
	 logger = reports.startTest("Smoke_TC_60_Ingestion of English External framework and search the ingested framework using filter");
   Assert.assertTrue(loginPage.login(common.userName,common.pwd,logger), "TC_60 - Login Failed");
	 Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_60 - English LOB link is not clickable");
	 Assert.assertTrue(engExternal.clickManageIngestion(logger), "TC_60 - English Manage Ingestion link was not clicked");
	 Assert.assertTrue(engExternal.selectEnglishLOBRadio(logger),"TC_60 - English LOB Radio is not clicked");
	 Assert.assertTrue(engExternal.selectExternalFrameworkRadio(logger),"TC_60 - EF Radio is not clicked");
	 Assert.assertTrue(engExternal.clickNextButton(logger),"TC_60 - Next button was not clicked");
	 Assert.assertTrue(engExternal.setCurriculumMetaDataHEEF(logger, HEConstant.Title, HEConstant.A), "TC_60 - Curriculum Meta data was not set");
	 Assert.assertTrue(engExternal.clickNextButton(logger),"TC_60 - Next button was not clicked");
	 Assert.assertTrue(engExternal.uploadFile(HEConstant.ExternalFrameworkExcelFile, logger), "TC_60 - Ingestion of excel file got failed");
	 Assert.assertTrue(engExternal.clickLOMTLogo(logger), "TC_60 - LOMT logo was not clicked");
	 Assert.assertTrue(homePage.selectEnglishLOB(logger),"TC_60 - English link is not clickable");
	 Assert.assertTrue(engExternal.clickExternalFramework(logger), "TC_60 - Product was not clicked");
	 Assert.assertTrue(engExternal.BrowseDataHEExtermalFramework(HEConstant.Title, "", logger), "TC_60 - Product Filter is not working/");
	 Assert.assertTrue(engExternal.SelectNode("Demo for LOMT EF", logger), "TC_60 - Select Node is not working/");
	 reports.endTest(logger);
	 logger = reports.startTest("Smoke_TC_61_Expand and collapse english external framework");
	 Assert.assertTrue(engExternal.expandCollapseParentCurriculumNode(true, logger), "TC_61 - Node was not expanded");		
	 Assert.assertTrue(engExternal.expandCollapseParentCurriculumNode(false, logger), "TC_61 - Node was not collapsed");
	 reports.endTest(logger);
	 logger = reports.startTest("Smoke_TC_62_Edit the ingested english external framework");
	 Assert.assertTrue(engExternal.clickChildNodeForEditinEnglishProduct("Core Sequence Example A", logger), "TC_62 - Selecting Child Node is not working/"); 
	 Assert.assertTrue(engExternal.clickEditinHEProduct(logger), "TC_62 - Edit button was not clicked");
	 Assert.assertTrue(engExternal.updateValueinEditHEEF("Updated", logger), "TC_62 - Edit and Save is not working");
	 Assert.assertTrue(engExternal.VerifyupdateValueinEdit("Updated", logger), "TC_62 - Verify the saved text");
	 reports.endTest(logger);
	}catch(AssertionError e){
  	 logger.log(LogStatus.WARNING, "Test scripts got failed");
  	 reports.endTest(logger);
  	 String[] getFailedTC = e.getLocalizedMessage().split("-");
  	 for(int i=0;i<tcCovered.length;i++){
  		 if((tcCovered[i].trim()).equalsIgnoreCase(getFailedTC[0].trim())){
  			 i++;
  			 while(i<tcCovered.length){
  				 logger = reports.startTest("Smoke "+tcCovered[i]);
  				 logger.log(LogStatus.SKIP, tcCovered[i]+ " got skipped");
  				 reports.endTest(logger);
  				 i++;
  			 }
  			 break;
  		 }
  	 }			
  	 Assert.fail();  	 
   }catch(Exception e){
  	 reports.endTest(logger);
  	 e.printStackTrace();
   }
	}		

	@AfterMethod
	public void goToHomePage(){
		try{
			Assert.assertTrue(homePage.logout(logger), "Logout was not clicked");
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}
