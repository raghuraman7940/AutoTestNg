package lomt.pearson.pagefunctions;
import com.relevantcodes.extentreports.ExtentTest;
import lomt.pearson.api.SmokeTesting.CommonAppReusable;

public class LoginPage extends CommonAppReusable{
	
	public boolean login(String UserName, String Password,ExtentTest test) {
		boolean flag = false;
		try {			
			rc.CreateScenarioHeader("Login into LOMT", test);
			LaunchApp(test);
			if (rc.SetText(driver, login.userName, "UserName", UserName, test)
					&& rc.SetText(driver, login.password, "Password", Password, test)
					&& rc.click(driver, login.loginButton, "Login button", test)) {
				waitforLoadIcon(test);
				if(rc.VerifyObjectPresent(driver, commonPOM.getLblWelcomeTitle(), "Home Page", test))
				{
				flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();		
		}
		return flag;
	}
	
}
