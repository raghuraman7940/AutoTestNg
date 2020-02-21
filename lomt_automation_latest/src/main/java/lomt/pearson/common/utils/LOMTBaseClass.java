package lomt.pearson.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import lomt.pearson.api.SmokeTesting.CommonAppReusable;
import lomt.pearson.constant.LOMTConstant;
import lomt.pearson.pagefunctions.HomePage;
import lomt.pearson.pagefunctions.LoginPage;
import lomt.pearson.pagefunctions.english.EnglishExternalFramework;
import lomt.pearson.pagefunctions.english.EnglishGSE;
import lomt.pearson.pagefunctions.english.EnglishLobHomePage;
import lomt.pearson.pagefunctions.english.EnglishProduct;
import lomt.pearson.pagefunctions.he.HEEducationalObjective;
import lomt.pearson.pagefunctions.he.HEExternalFramework;
import lomt.pearson.pagefunctions.he.HELobHomePage;
import lomt.pearson.pagefunctions.he.HEProduct;
import lomt.pearson.pagefunctions.school.SchoolCurriculum;
import lomt.pearson.pagefunctions.school.SchoolIntermediaries;
import lomt.pearson.pagefunctions.school.SchoolLobHomePage;
import lomt.pearson.pagefunctions.school.SchoolProduct;

public class LOMTBaseClass {
	
	protected static CommonAppReusable common; 
	protected static LoginPage loginPage;
	protected static HomePage homePage;
	protected static ExtentTest logger;
	protected static ExtentReports reports ;
	protected static SchoolLobHomePage schoolHomePage;
	protected static SchoolIntermediaries SchoolIntermediaries;
	protected static SchoolCurriculum schoolCurriculum;
	protected static SchoolIntermediaries schoolInter;
	protected static SchoolProduct schoolProduct;
	protected static HELobHomePage heHomePage;
	protected static HEEducationalObjective heEducational;
	protected static HEExternalFramework heExternal;
	protected static HEProduct heProduct;
	protected static EnglishLobHomePage engHomePage;
	protected static EnglishGSE engGse;
	protected static EnglishExternalFramework engExternal;
	protected static EnglishProduct engProduct;

	
	public static  WebDriver driver;
	
	//protected WebDriverWait wait = new WebDriverWait(driver, 120);
	
	@BeforeSuite(alwaysRun=true)
	public void setup(){
		 if(CreateReportFolder()){
		reports = new ExtentReports(LOMTConstant.reportPath+"/SummaryReport.html", true);
		 
		 common = new CommonAppReusable();
		 loginPage = new LoginPage();
		 homePage = new HomePage();
		 schoolHomePage = new SchoolLobHomePage();
		 SchoolIntermediaries = new SchoolIntermediaries();
		 schoolCurriculum = new SchoolCurriculum();
		 schoolInter = new SchoolIntermediaries();
		 schoolProduct = new SchoolProduct();
		 heHomePage = new HELobHomePage();
		 heEducational = new HEEducationalObjective();
		 heExternal = new HEExternalFramework();
		 heProduct = new HEProduct();
		 engHomePage = new EnglishLobHomePage();
		 engGse = new EnglishGSE();
		 engExternal = new EnglishExternalFramework();
		 engProduct = new EnglishProduct();
		logger = reports.startTest("Browser is launched");
		common.openBrowser(logger);
		reports.endTest(logger);
		 }
		 		 else
		 		 {
		 			  throw new SkipException("Before Suite");
		 		 }
	}	
public WebDriver getChromeDriver(){
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
		String downloadFilepath = (new File(System.getProperty("user.dir")).getAbsolutePath())+System.getProperty("file.separator")+"src/test/resources/download";
		System.out.println("Downloaded file path : "+downloadFilepath);
		
		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", chromePrefs);
		
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		return new ChromeDriver(cap);
		
	}

public boolean CreateReportFolder()
{	boolean successful=false;
	try{
	Date dNow = new Date( );
	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd-hh-mm-ss"); 
	String date = ft.format(dNow);
	LOMTConstant.reportPath = (new File(System.getProperty("user.dir")).getAbsolutePath())+System.getProperty("file.separator")+"src/main/java/lomt/pearson/reports/Test_Run_"+date;
	File dir = new File(LOMTConstant.reportPath);
    
    // attempt to create the directory here
	successful= dir.mkdir();
    if (successful)
    {
      // creating the directory succeeded
      System.out.println("Report directory was created successfully");
      LOMTConstant.screenShotPath=LOMTConstant.reportPath+"/Screenshot";
      File screenDir = new File(LOMTConstant.screenShotPath);
      successful= screenDir.mkdir();
      if (successful)
      {
    	  System.out.println("ScreenShot directory was created successfully");
      }
      else
      {
        // creating the directory failed
        System.out.println("ScreenShot - failed trying to create the directory");
      }
    }
    else
    {
      // creating the directory failed
      System.out.println("Report - failed trying to create the directory");
    }
	}
	catch(Exception e)
	{
		successful=false;
	}
    return successful;
}

@AfterSuite(alwaysRun=true)
public void cleanup(){
	reports.flush();
	common.killBrowsers();
}	

}
