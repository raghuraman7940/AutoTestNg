package lomt.pearson.test_script.somkeTesting;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import lomt.pearson.common.utils.LOMTBaseClass;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;

public class DemoTestNG extends LOMTBaseClass{
	
  @Test(priority=1)
  public void Ademotest1() throws InterruptedException {
	  logger = reports.startTest("My ADemotest1");
	  //ExtentTest test=
			  
			  logger.log(LogStatus.PASS, "Test scripts Passed");
	  SoftAssert softAssertion= new SoftAssert();
	  System.out.println("@@@Ademotest1");
	  
	  Assert.assertEquals("1","1","My ADemotest1 scenario");
	  logger.log(LogStatus.PASS, "ADemotest1 Step 1");
	  
	  
//	  softAssertion.assertEquals("1","2","My ADemotest scenario");
	  System.out.println("@@@Ademotest1 - Continue");
	  logger.log(LogStatus.PASS, "ADemotest1 Step 2");
	  //softAssertion.assertAll();
	  reports.endTest(logger);


  }
  
  
  @Test( priority=2)
  public void Bdemotest2() throws InterruptedException {
	  System.out.println("@@@Bdemotest2");
	  logger = reports.startTest("My Bdemotest2");
	  logger.log(LogStatus.PASS, "Bdemotest2 Step 1");
	  logger.log(LogStatus.PASS, "Bdemotest2 Step 2");
	  Assert.assertEquals("1","1","My Bdemotest2 scenario");
	  logger.log(LogStatus.PASS, "Bdemotest2 Step 3");
	  reports.endTest(logger);
	  


  }
  
//  @Test(dataProvider = "dp",groups = { "regression" }, priority=1)
//  public void Cdemotestdataprov1(Integer n, String s) {
//	  System.out.println("@@@Cdemotestdataprov1: "+n+" : "+s);
//  }
//  
//  
//  
//  @DataProvider(parallel=true)
//  public Object[][] dp() {
//	  System.out.println("@@DataProvider");
//    return new Object[][] {
//      new Object[] { 1, "a" },
//      new Object[] { 2, "b" },
//      new Object[] { 3, "c" },
//      new Object[] { 4, "d" },
//      new Object[] { 5, "e" },
//      new Object[] { 6, "f" },
//      new Object[] { 7, "g" },
//      new Object[] { 8, "h" },
//    };
//  }
  


}