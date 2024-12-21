package zoho.managers;

import java.io.FileNotFoundException;
import java.io.IOException;

import zoho.pages.CreateLeadPage;
import zoho.pages.HomePage;
import zoho.pages.LeadsDescriptionPage;
import zoho.pages.LeadsDetailPage;
import zoho.pages.LoginPage;
import zoho.pages.TopMenuComponent;

public class PageObjectManager {
	public HomePage homePage;
	public WebDriverManager webDriverManager;
	public LoginPage loginPage;
	TopMenuComponent topmenu;
	LeadsDescriptionPage leadspage;
	CreateLeadPage createLeadPage;
	LeadsDetailPage leadDetailPage;

	
	public PageObjectManager () throws FileNotFoundException, IOException {
		this.webDriverManager = new WebDriverManager();
	}
	
	public WebDriverManager getWebDriverManager() {
		return webDriverManager;
	}
	
	public HomePage getHomePage() {
		if(homePage == null) {
			homePage = new HomePage(webDriverManager);
		}
		
		return homePage;
	}
	
	public LoginPage getLoginPage() {
		if(loginPage == null) {
			loginPage = new LoginPage(webDriverManager);
		}
		
		return loginPage;
	}
	
	public TopMenuComponent getTopMenu() {
		if(topmenu == null) {
			topmenu = new TopMenuComponent(webDriverManager);
		}
		
		return topmenu;
	}
	
	public LeadsDescriptionPage getLeadsPage() {
		if(leadspage == null) {
			leadspage = new LeadsDescriptionPage(webDriverManager);
		}
		
		return leadspage;
	}
	
	public CreateLeadPage getCreateLeadPage() {
		if(createLeadPage == null) {
			createLeadPage = new CreateLeadPage(webDriverManager);
		}
		
		return createLeadPage;
	}
	
	public LeadsDetailPage getLeadDetailPage() {
		if(leadDetailPage == null) {
			leadDetailPage = new LeadsDetailPage(webDriverManager);
		}
		
		return leadDetailPage;
	}
	
}
