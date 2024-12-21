package zoho.pages;

import com.aventstack.extentreports.ExtentTest;

import zoho.managers.WebDriverManager;

public class HomePage {
	WebDriverManager app;
	ExtentTest test;
	
	public HomePage(WebDriverManager app) {
		this.app = app;
	}
	
	public void load(String browserName) {
		//Open Browser and Navigate to URL
		app.log("Trying to Load the Website");
		app.openBrowser(browserName);
		app.navigate("url");
		
	}
	
	public void goToLoginPage() {
		app.log("Trying to Login");
		app.click("signin_link_xpath");
		
		if(!app.isElementPresent("username_id")) {
			app.reportFailure("Login Page did not load!!", true);
		}
		
		//return new LoginPage();

	}
}
