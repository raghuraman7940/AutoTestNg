package lomt.pearson.pagefunctions.he;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.openqa.selenium.By;

import com.relevantcodes.extentreports.ExtentTest;

import lomt.pearson.api.SmokeTesting.CommonAppReusable;
import lomt.pearson.constant.LOMTConstant;

public class HEEducationalObjective extends CommonAppReusable {
	// Clicking on Search Node
	public boolean SelectNode (String getEducationalObjectiveValue, ExtentTest test) {
		boolean flag=false;
		try {
			rc.clickOnInvisible(driver, returnElement(By.linkText(getEducationalObjectiveValue)),  getEducationalObjectiveValue+" Link", test);
			Thread.sleep(5000);
			waitforLoadIcon(test);
				flag=true;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//Select Select All
	public boolean clickSelectAll(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, hePom.selectAll, "Select All Link", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	//Select Select None
	public boolean clickSelectNone(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, hePom.selectAll, "Select None Link", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}	
	
	//Click Action
	public boolean clickAction(ExtentTest test){
		boolean flag = false;
		try{
			flag = rc.click(driver, commonPOM.btn_Actions, "Click Action", test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}	
	
	//Clicking Export Button
	public boolean exportClick(ExtentTest test){
		boolean flag =false;
		try{
			flag = rc.click(driver, commonPOM.exportButton, "Export Button", test);
			waitforLoadIcon(test);
		}catch(Exception e){
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//To search Goal framework Child Node in EducationalObjective
	public boolean BrowseDataEO(String SearchText,String ApplyFilter,ExtentTest test) {
		boolean flag=false;
		try {
			rc.CreateScenarioHeader("Browse Data "+SearchText, test);
			if(!SearchText.isEmpty()){
			rc.SetText(driver, driver.findElement(By.className("form-control")), "Search Terms TextBox",SearchText , test);
		}
			if(!ApplyFilter.isEmpty())
			{
			Browse(ApplyFilter,test);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			}
			if(rc.click(driver,commonPOM.get_btn_UpdateResults(), "Update Results button", test))
			{
				waitforLoadIcon(test);
				flag=rc.VerifyObjectPresent(driver, returnElement(By.xpath("//a[contains(@text,'"+SearchText+"')] | //span[@class='item-desc']")), "Search Result - "+SearchText, test);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//Get the newest file from download to re-ingest
		public static String getTheNewestFile() {		
		String filepath = LOMTConstant.EXPORTED_FILE_PATH;
		//	String filepath = "src/main/java/lomt/pearson/downloads";
		    File theNewestFile = null;
		    File dir = new File(filepath);
		    FileFilter fileFilter = new WildcardFileFilter("*.xlsx");
		    File[] files = dir.listFiles(fileFilter); 
	    if (files.length > 1) {
		        /** The newest file comes first **/
		        Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
		        theNewestFile = files[0];
		    }else{
		    	  theNewestFile = files[0];
		    }
		    String [] fileName = theNewestFile.toString().split("\\\\");
		   String Filename =  fileName[fileName.length-1];
			return Filename.toString();
		    
		} 
		
		//click child node for edit
		public boolean clickChildNodeForEdit(String SearchText,ExtentTest test){
			boolean flag =false;
			try{
				flag=rc.click(driver, returnElement(By.xpath("//a[contains(@text,'"+SearchText+"')] | //span[@class='item-desc']")), "Search Result - "+SearchText, test);
				waitforLoadIcon(test);
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			return flag;
		} 
		//Update Value inside Edit(Node)
		public boolean updateValueinEditHE(String UpdateText,ExtentTest test){
			boolean flag =false;
			try{
				rc.SetText(driver, hePom.descriptionid, "Description Text box", UpdateText , test);
				rc.click(driver, commonPOM.get_btn_save(), "Save button", test);
				waitforLoadIcon(test);
				flag=true;
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			return flag;
		}
		
		//click child node for edit
		public boolean clickChildNodeForEditinHEProduct(String SearchText,ExtentTest test){
			boolean flag =false;
			try{
				flag=rc.click(driver, returnElement(By.xpath("//a[contains(@text,'"+SearchText+"')] | //span[@class='node-name']")), "Search Result - "+SearchText, test);
				waitforLoadIcon(test);
			}catch(Exception e){
				e.printStackTrace();
				flag = false;
			}
			return flag;
		} 
		//Update Value inside Edit(Node)
				public boolean updateValueinEditHEProduct(String UpdateText,ExtentTest test){
					boolean flag =false;
					try{
						rc.SetText(driver, hePom.levelTitle, "Level Title Text box", UpdateText , test);
						rc.click(driver, commonPOM.get_btn_save(), "Save button", test);
						waitforLoadIcon(test);
						flag=true;
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
//Clicking Export Button Product
				public boolean exportClickHEProduct(ExtentTest test){
					boolean flag =false;
					try{
						flag = rc.click(driver, commonPOM.ExportHEProduct, "Export Button", test);
						waitforLoadIcon(test);
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
				
//Update Value inside Edit Higher Education-External Framework(Node)
				public boolean updateValueinEditHEEF(String UpdateText,ExtentTest test){
					boolean flag =false;
					try{
						hePom.officialstandardcode.clear();
						rc.SetText(driver, hePom.officialstandardcode, "Official Standard Code", UpdateText , test);
						rc.click(driver, commonPOM.get_btn_save(), "Save button", test);
						waitforLoadIcon(test);
						flag=true;
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}		
				
				
				public boolean VerifyupdateValueinEdit(String UpdateText,ExtentTest test){
					boolean flag =false;
					
					try{
						rc.CreateScenarioHeader("Verify the Updated Text", test);
						if(rc.compareContainsText(driver, commonPOM.HEEFEditUpdated,"Updated value - "+UpdateText,UpdateText, test))
						{
							flag=true;
						}
						
					}catch(Exception e){
						e.printStackTrace();
						flag = false;
					}
					return flag;
				}
}
