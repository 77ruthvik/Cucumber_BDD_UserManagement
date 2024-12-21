package zoho.pages;

import zoho.managers.WebDriverManager;

public class LoginPage {
	public WebDriverManager app;
	
	public LoginPage(WebDriverManager app) {
		this.app = app;
	}
	
	public void doLogin() {
		app.log("Logging into Zoho");
		
		app.type("username_id", app.getProperty("username"));
		app.click("submit_but_id");
		
		app.type("password_id", app.getProperty("password"));
		app.click("submit_but_id");
		
		app.click("CRM_xpath");
	}
	
}
