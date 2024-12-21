package zoho.pages;

import java.util.List;

import teststeps.data.LeadData;
import zoho.managers.WebDriverManager;

public class CreateLeadPage {
	WebDriverManager app;
	
	public CreateLeadPage(WebDriverManager app) {
		this.app = app;
	}
	
	public void submitDetails(List<LeadData> data) {
		app.type("firstname_xpath", data.get(0).firstName);
		app.type("lastname_xpath", data.get(0).lastName);
		app.type("company_css", data.get(0).company);
		app.type("email_xpath", data.get(0).email);
		
		app.click("create_but_xpath");

	}
}
