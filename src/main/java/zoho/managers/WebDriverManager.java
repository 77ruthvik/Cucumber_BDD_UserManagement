package zoho.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import zoho.reports.ExtentManager;

public class WebDriverManager {
	
	public WebDriver driver;
	public ExtentTest test;
	public Properties prop;
	SoftAssert softAssert;
	
	public WebDriverManager() throws FileNotFoundException, IOException {
		String path = System.getProperty("user.dir")+"//src//test//resources//project.properties";
		prop = new Properties();
		prop.load(new FileInputStream(path));
		
		//System.out.println(prop.get("url"));
		
		softAssert = new SoftAssert();
	}
	
	public void init(ExtentTest test) {
		this.test = test;
	}
	
	public String getProperty(String property) {
		return prop.getProperty(property);
	}

	public void openBrowser(String browserName) {
		log("Opening the browser: "+browserName);
		
		if(browserName.equals("Mozilla")) {
			FirefoxOptions op = new FirefoxOptions();
			op.setPageLoadStrategy(PageLoadStrategy.EAGER);
			FirefoxProfile prof = new FirefoxProfile(); //allProf.getProfile(""); //Can get your profile of choice
			prof.setPreference("dom.webnotifications.enabled", false); //Manage notifications
			
			op.setProfile(prof);
			driver = new FirefoxDriver(op);
		}
		else if(browserName.equals("Chrome")) {
			//System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "C:\\Users\\rvarm\\Selenium Course\\SeleniumWebDriverMaven\\logs\\chrome.log");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

			ChromeOptions options = new ChromeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--disable-notifications"); //Handles Notifications
			options.addArguments("--start-maximized"); //Opens the browser maximized
			options.addArguments("ignore-certificate-errors"); //Handles Certificate errors

			driver = new ChromeDriver(options);
		}
		else if(browserName.equals("Edge")) {
			System.setProperty(EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY, "true");

			EdgeOptions options = new EdgeOptions();
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--disable-notifications"); //Handles Notifications
			options.addArguments("--start-maximized"); //Opens the browser maximized
			//options.addArguments("ignore-certificate-errors"); //Handles Certificate errors

			driver = new EdgeDriver(options);
		}
				
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); //Dynamic wait. It uses 20s to find the element. If it's not found in 10s, it then throws an exception
	}
	
	public void navigate(String url) {
		log("Navigating to url: "+url);
		driver.get(getProperty(url));
	}
	
	public void click(String locator) {
		log("Clicking on Locator: "+locator);
		WebElement el = findElement(locator);
		
		if(el != null) {
			el.click();
		}
	}
	
	public void type(String locator, String text) {
		log("Typing at Locator: "+locator+", and entering text: "+text);

		WebElement el = findElement(locator);
		
		if(el != null) {
			el.sendKeys(text);
		}
	}
	
	public void reportFailure(String msg, boolean failTest) {
		System.out.println(msg);
		test.log(Status.FAIL, msg);
		takeScreenshot();
		softAssert.fail(msg);
		;
		if(failTest==true) {
			softAssert.assertAll();
		}
	}
	
	public WebElement findElement (String locatorKey) {
		By locator = getLocator(locatorKey);
		WebElement el = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			//reportFailure("Some failure: "+locator, false);

			el = driver.findElement(locator);
		}
		catch(Exception e) {
			e.printStackTrace();
			reportFailure("Element Not found or visible: "+locator, true);
		}
		
		return el;
	}
	
	public void log(String text) {
		test.log(Status.INFO, text);
	}
	
	public void quit() {
		if(softAssert != null) {
			softAssert.assertAll();
		}
		
		driver.quit();
	}
	
	public By getLocator(String locatorkey) {
		
		if(locatorkey.endsWith("_id")) {
			return By.id(getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_xpath")) {
			return By.xpath(getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_name")) {
			return By.name(getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_class")) {
			return By.className(getProperty(locatorkey));
		}
		else if(locatorkey.endsWith("_css")) {
			return By.cssSelector(getProperty(locatorkey));
		}
		
		return null;
	}
	
	public void takeScreenshot() {
		Date d = new Date();
		String screenshotFile = d.toString().replace(":", "_").replace(" ", "_")+".png";
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//System.out.println(ExtentManager.screenshotFolderPath);
		//System.out.println(screenshotFile);
		try {
			FileUtils.copyFile(src, new File(ExtentManager.screenshotFolderPath+screenshotFile));
			//test.addScreenCaptureFromPath("path of image", "");
			test.log(Status.INFO, "Screenshot -> "+test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+screenshotFile));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isElementPresent(String locatorKey) {
		By locator = getLocator(locatorKey);
		
		WebElement el = null;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			
			return true;
		}
		catch(Exception e) {
			return false;
		}
		
	}
	
	public int getLeadRowNumberWithCellData(String leadName) {
		
		for(int i=0;i<driver.findElements(getLocator("leadname_css")).size();i++) {
			List<WebElement> names = driver.findElements(getLocator("leadname_css"));

			System.out.println("Here");
			//System.out.println(names.get(i).getText());
			System.out.println(leadName);
			//System.out.println(leadName.equalsIgnoreCase(names.get(i).getText()));
			
			if(leadName.equalsIgnoreCase(names.get(i).getText()))
				return (i+1);
		}
		
		return -1;// not found
	}
	
	public void selectLeadCheckBox(int rowNum) {
		driver.findElement(By.cssSelector("lyte-exptable-tr:nth-child("+rowNum+") > lyte-exptable-td:nth-child(2) label")).click();
	}
	
	public void press_enter() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		findElement("delete_confirm_css").sendKeys(Keys.ENTER);
	}
	
}
