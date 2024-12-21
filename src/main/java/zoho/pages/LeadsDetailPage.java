package zoho.pages;

import zoho.managers.WebDriverManager;

public class LeadsDetailPage {
	WebDriverManager app;
	
	public LeadsDetailPage(WebDriverManager app) {
		this.app = app;
	}
	
	public void goToPage(String action) {
		if(action.equals("create lead")) {
			app.click("Create_leads_xpath");
		}
	}
	
	public void selectLead(String val) {
		int rowNum = app.getLeadRowNumberWithCellData(val);
		if(rowNum==-1) {
			app.reportFailure("Lead not found in lead list", true);
		}
		
		app.log(val+" lead Row Number is "+rowNum);
         
		// locator will be dynamic
		
		app.selectLeadCheckBox(rowNum);
	}
	
	public void perform(String action) {
		if(action.equals("delete")) {
			app.log("Performing delete action");
			app.click("delete_span_xpath");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			app.click("delete_but_xpath");
		}
	}
	
	public void confirm(String action) {
		if(action.equals("deleted")) {
			app.press_enter();
			app.log("Deleted the lead");
		}
	}
}
