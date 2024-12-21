package zoho.pages;

import zoho.managers.WebDriverManager;

public class TopMenuComponent {
	WebDriverManager app;
	
	public TopMenuComponent(WebDriverManager app) {
		this.app = app;
	}
	
	public void load(String menuOp) {
		if(menuOp.equals("Leads")) {
			app.click("Leads_xpath");
		}
	}
}
