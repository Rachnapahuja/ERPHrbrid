package driverfactory;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonfunctions.FunctionLibrary;
import utilities.ExcelFileUtil;


public class DriverScript {
String inputpath = "./FileInput/DataEngine.xlsx";
String outputppath = "./FileOutput/DataEngineResults.xlsx";
ExtentReports report;
ExtentTest logger;
//test case execute based on master sheet store it into variable "Sheet"
String Sheet = "MasterTestCases";
 WebDriver driver;
 
 //make it void as we don't use it anywhere

public void startTest() throws Throwable
{
String Modulestatus="";
//create object for excelfileutil class
//above we have stored data file into inputpath variable 
ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in sheet (masterclasssheet)
for (int i=1;i<= xl.rowCount(Sheet);i++)
{
	 if(xl.getCellData(Sheet,i, 2).equalsIgnoreCase("y"))
{
	//store all corresponding sheet into TCModule so all test go one after each
	String TCModule = xl.getCellData(Sheet, i, 1);
	//gentrate report 
	report = new ExtentReports("./target/Reports/\"+TCModule+FunctionLibrary.generateDate()+\".html");
	logger = report.startTest(TCModule);
	logger.assignAuthor("Rachna");
	
	
	//iterate all rows in TCModule
	for(int j=1; j<=xl.rowCount(TCModule);j++ )
	{ 
		//read each cell from TCModule
	//store first into variable
		String Description = xl.getCellData(TCModule, i, 0);
		String Object_Type = xl.getCellData(TCModule, j, 1);
		String Locator_Type = xl.getCellData(TCModule, j, 2);
		String Locator_Value = xl.getCellData(TCModule, j, 3);
		String Test_data = xl.getCellData(TCModule, j, 4);
		//in try will call one by one keyword. selenium pls try these keyword
		//if every keyword passing success fully then write pass
		//in catch - any exception we receive catch them and write into satus column as fail
		try {
			if(Object_Type.equalsIgnoreCase("startBrowser"))
			{
				// return method used in function library is webdriver stored in driver variable
				driver = FunctionLibrary.startBrowser();
				logger.log(LogStatus.INFO,Description);
				
			}
			if(Object_Type.equalsIgnoreCase("openUrl"))
			{
				//we don't need variable as we gave void type. also no argument required.
				FunctionLibrary.openUrl();
				logger.log(LogStatus.INFO,Description);
			}
			if(Object_Type.equalsIgnoreCase("waitForElement"))
			{
			 FunctionLibrary.waitForElement(Locator_Type, Locator_Value, Test_data);
			 logger.log(LogStatus.INFO,Description);
			}
			if(Object_Type.equalsIgnoreCase("typeAction"))
			{
				FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_data);
				logger.log(LogStatus.INFO,Description);
			}
			if(Object_Type.equalsIgnoreCase("clickAction"))
			{
				FunctionLibrary.clickAction(Locator_Type, Locator_Value);
				logger.log(LogStatus.INFO,Description);
				
			}
			if(Object_Type.equalsIgnoreCase("validateTitle"))
			{
				FunctionLibrary.validateTitle(Test_data);
				logger.log(LogStatus.INFO,Description);
				
			}
			if(Object_Type.equalsIgnoreCase("closeBrowser"))
			{
				FunctionLibrary.closeBrowser();
				logger.log(LogStatus.INFO,Description);
				
			}
			// write new methods here which are added in stockitems
			if(Object_Type.equalsIgnoreCase("dropDownAction"))
			{
				FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_data);
				logger.log(LogStatus.PASS,Description);
			}
			if(Object_Type.equalsIgnoreCase("capturestock"))
			{
				FunctionLibrary.capturestock(Locator_Type, Locator_Value);
				logger.log(LogStatus.PASS,Description);
			}
			if(Object_Type.equalsIgnoreCase("stockTable"))
			{
				FunctionLibrary.stockTable();
				logger.log(LogStatus.PASS,Description);
			}
			if(Object_Type.equalsIgnoreCase("capturesup"))
			{
				FunctionLibrary.capturesup(Locator_Type, Locator_Value);
				logger.log(LogStatus.INFO, Description);
			}
			if(Object_Type.equalsIgnoreCase("supplierTable"))
			{
				FunctionLibrary.supplierTable();
				logger.log(LogStatus.INFO, Description);
			}
			if(Object_Type.equalsIgnoreCase("capturecus"))
			{
				FunctionLibrary.capturecus(Locator_Type, Locator_Value);
				logger.log(LogStatus.INFO, Description);
			}
			if(Object_Type.equalsIgnoreCase("customerTable"))
			{
				FunctionLibrary.customerTable();
				logger.log(LogStatus.INFO, Description);
			}
			
		//write as pass into status cell in TCModule sheet
			xl.setCellData(TCModule, j, 5, "Pass", outputppath);
			logger.log(LogStatus.PASS,Description);
			Modulestatus = "True";
				
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			//write as a fail into status cell in TCModule
		xl.setCellData(TCModule, j, 5, "Fail", outputppath);
		logger.log(LogStatus.FAIL,Description);
		Modulestatus = "False";
		
		}
		
		if (Modulestatus.equalsIgnoreCase("True"))
		{
			//write as pass into sheet status cell
			xl.setCellData(Sheet, i, 3, "Pass", outputppath);
		}
		if(Modulestatus.equalsIgnoreCase("False"))
		{
			xl.setCellData(Sheet, i, 3, "Fail", outputppath);
		}
			
		report.endTest(logger);
		report.flush();
		
	}
	
	  
}
else
{
	//write as blocked into satus cell for Testcases flag to N
	xl.setCellData(Sheet, i, 3, "Blocked", outputppath);
}
}
}
}
