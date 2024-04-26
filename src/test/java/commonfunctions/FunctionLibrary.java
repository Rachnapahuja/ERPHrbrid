package commonfunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;

	// method for launching browser
	public static WebDriver startBrowser() throws Throwable
	// we are writing as WebDriver return type  as we want to return all webbrowser methods 
	// to each and every browser
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Envoirnment.properties"));
		//we need to load new property file and give condition if browser is matching 
		// to chrome or if not both cases. 
		if (conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if (conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser value is not matching", true);

		}
		return driver;

	}

	//methods for launching method

	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for to wait for any webElement

	public static void waitForElement(String LocatorType, String LocatorValue, String TestData)
	// we are creating object here for webdriver wait class and converting testdata type to int from string
	// now "mywait" is holding "int" type testdata

	{
		WebDriverWait mywait = new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));	
		if(LocatorType.equalsIgnoreCase("name"))
		{
			//wait until element is visible and give locator name, xpath and id 
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));	
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	//method for textboxes (type action)
	public static void typeAction(String LocatorType, String LocatorValue, String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}

	}
	//method for click button, link, image , checkbox etc
	public static void clickAction (String LocatorType, String LocatorValue)

	{	
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();

		}
		if (LocatorType.equalsIgnoreCase("id"))
			//write sendKeys(Keys.ENTRE) some buttons are unable to identify
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
	}
	// method to validate page title (get title)
	public static void validateTitle (String Expected_tittle)
	{
		String Actual_title = driver.getTitle();
		// along with printing below message it will print lot of exceptions so to avoid that we need
		// to use try catch exception 
		try {
			Assert.assertEquals(Actual_title, Expected_tittle, "Tittle is not Matching");
		}catch(AssertionError a)
		// so catch(AssertionError a) object a is class object holding detailed error message 
		{
			System.out.println(a.getMessage());
			// "a.getMessage will print detailed error message and try catch will avoid printing exception. 

		}

	}
	//method for click logout browser 
	public static void closeBrowser()
	{
		driver.quit();
	}
	//method for date generate
	public static String genrateDate()
	{
		//Date date = new Date(0);
		Date data = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd_hh_mm");
		return df.format(data);
	}
	//method for listboxes
	public static void dropDownAction(String LocatorType, String LocatorValue, String TestData)
	{
		if(LocatorType.equalsIgnoreCase("id"))
		
		{//convert textbox value to integer
		int value = Integer.parseInt(TestData);
		// if you want to access any select class methods for listbox first we want to store listbox in select class object
		Select element = new Select(driver.findElement(By.id(LocatorValue)));
		element.selectByIndex(value);
	}
		if(LocatorType.equalsIgnoreCase("name"))
			
		{//convert textbox value to integer
		int value = Integer.parseInt(TestData);
		// if you want to access any select class methods for listbox first we want to store listbox in select class object
		Select element = new Select(driver.findElement(By.name(LocatorValue)));
		element.selectByIndex(value);
	}
		if(LocatorType.equalsIgnoreCase("xpath"))
			
		{//convert textbox value to integer
		int value = Integer.parseInt(TestData);
		// if you want to access any select class methods for listbox first we want to store listbox in select class object
		Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
		element.selectByIndex(value);
	}
}
	//method for capturing stock number into notepad 
	public static void capturestock(String LocatorType, String LocatorValue) throws Throwable
	
	{
		 //we need to capture value so we use get attributes
		//its a local variable brown colour, also we are storing this 
		// so we can use more times within the method.
		// we shd give stock_Num as null as we are storing string type data
		
		String stock_Num= "";
		if(LocatorType.equalsIgnoreCase("id"))
		{
			//get attribute we use value why?
			stock_Num= driver.findElement(By.id(LocatorValue)).getAttribute("Value");
			
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			//get attribute we use value why?
			stock_Num= driver.findElement(By.name(LocatorValue)).getAttribute("Value");
			
		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			//get attribute we use value why?
			stock_Num= driver.findElement(By.xpath(LocatorValue)).getAttribute("Value");
			
		}
		//now want we want - selenuim go in captured data folder create one noted pad 
		//and write that text (stock_Num)into the notepad.
		//notepad methods belongs to Java not to webdriver methods
		// for this we use Filejava class to create folder
		//when you create any Flie add throw class exception 
		
		FileWriter fw = new FileWriter("./Capturedata/stockNumber.txt");
		// fw is holding path of the file , file not have any memory
		//in order to allocate memory we use BufferedWriter
		BufferedWriter bw = new BufferedWriter(fw);
		//bw buffered object holding buffered methods
		// now we want to write stock_Num into bw (notepad)
		// in order to write into notepad we need to use write method 
		
		bw.write(stock_Num);
		// flush means push text into notepad and if any text is 
		//there already it will over write thats text.
		bw.flush();
		// close will release the memory 
		bw.close();
		
		
	
	
	}
	
	//now read the stock_num from notepad, seleuinum should go 
			//and if search textbox is displayed click on test panel 
			//if textbox not displayed click on textbox 
			//entre the stock_Num
	//method for stock table validation 
	public static void stockTable() throws Throwable
	
	{
		//read data from notepad for reading we use FileReader
		FileReader fr = new FileReader("./Capturedata/stockNumber.txt");
		BufferedReader br = new BufferedReader(fr);
		// br holding memory methods 
		// read it and store into string type data as we need to compare it 
		//wherever we want to read line by line we use readline method 
		String Exp_Data = br.readLine();
		// Exp_Data, basically we are storing stock_Num into Exp_data.
		// now we should validate into table 
		// now we need to access methods from property file 
		//for that we had given variable as conpro to access property methods
		//! - means "Not"
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		// don't use any semi cloumn and don't open any 
		//flower braces because we don't have any else part
			
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		
	// selenium will go to stock item table and capture stock numebr 
	// we use get text as it is table text 
		
		String Act_Data = driver.findElement(By.xpath("//table[@class=\"table ewTable\"]/tbody/tr[1]/td[8]/div/span/span")).getText();
		Reporter.log(Act_Data+"=========="+Exp_Data,true);
		try {
		Assert.assertEquals(Act_Data, "Stock Number is not Matching");
		
		}catch(AssertionError a)
		{
			// get message will hold stock number is not matching message 
			System.out.println(a.getMessage());
		}
	}
	//method for capture supplier number into notepad, for method always check arguments
	
	public static void capturesup(String LocatorType, String LocatorValue) throws IOException
	{
		String supplierNum= "";
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			supplierNum= driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
			
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			supplierNum= driver.findElement(By.id(LocatorValue)).getAttribute("value");
			
		}
		
		if(LocatorType.equalsIgnoreCase("name"))
		{
			supplierNum= driver.findElement(By.name(LocatorValue)).getAttribute("value");
			
		}
		//write supplier number into notepad 
		FileWriter fw = new FileWriter("./Capturedata/Supplier.txt");
		// add supper class 
		//now we need to allocate memory to fw
		BufferedWriter bw = new BufferedWriter(fw);
		//bw is holding memory methods now we want to write supplier number
		bw.write(supplierNum);
		bw.flush();
		bw.close();
		
	}
	//method for supplier table 
	public static void supplierTable() throws Throwable
	{
		//read supplier number from notepad 
		FileReader fr = new FileReader("./Capturedata/Supplier.txt");
		// now we need to read fr
		BufferedReader br = new BufferedReader(fr);
		// now we need to read supplier number and store it into variable
		String Exp_data = br.readLine();
		//supplier number is stored in Exp_data
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			// click search panel if search textbox is not displayed 
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		//clear text in text box 
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_data);
		// just put thread.sleep for your observation 
		Thread.sleep(3000);
		//now click on search button 
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		// now we need to capture supplier number from supplier table 
		String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		//print both data
		Reporter.log(Act_data+"========="+Exp_data,true);
		try {
		Assert.assertEquals(Act_data, Exp_data, " Supplier number is not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}
		
}
	//method for capture customer number into notepad 
	
	public static void capturecus(String LocatorType,String LocatorValue) throws Throwable
	{
		String CustomerNum = "";
		if(LocatorType.equalsIgnoreCase("Xpath"))
		{
			CustomerNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("Value");
		}
		
	if(LocatorType.equalsIgnoreCase("name"))
	{
		CustomerNum = driver.findElement(By.name(LocatorValue)).getAttribute("Value");
	}
	
	if (LocatorType.equalsIgnoreCase("id"))
	{
		CustomerNum = driver.findElement(By.id(LocatorValue)).getAttribute("Value");
	}
	// Write customer number to notepad 
	FileWriter fw = new FileWriter("./Capturedata/Customer.txt");
	// now alloacte memory
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(CustomerNum);
	bw.flush();
	bw.close();
	
	}
// method for customerTable
	public static void customerTable() throws Throwable
	{
		// read customer number from notepad 
		FileReader fr = new FileReader("./Capturedata/Customer.txt");
		// now read fr and allocate memory and store it into variable 
		BufferedReader br = new BufferedReader(fr);
		String Exp_data = br.readLine();
		//customer number is stored in Exp_data
		// now we need to write condition for search box 
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		// if search textbox is displayed then click search panel 
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		// clear text box 
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		//enter customer number in search textbox 
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_data);
		Thread.sleep(3000);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		//now we need to capture customer number from customer table 
		
		String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[5]/div/span/span")).getText();
		Reporter.log(Act_data+"======="+Exp_data, true);
		try
		{
			Assert.assertEquals(Act_data, Exp_data, "Supplier number is not matching");
				
		}catch (AssertionError a)
		{
			System.out.print(a.getMessage());
			
		}
	}
	
	
}

