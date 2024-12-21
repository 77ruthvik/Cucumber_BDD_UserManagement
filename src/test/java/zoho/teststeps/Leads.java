package zoho.teststeps;

import java.util.List;
import java.util.Map;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import teststeps.data.LeadData;
import zoho.context.TestContext;
import zoho.pages.CreateLeadPage;
import zoho.pages.LeadsDescriptionPage;
import zoho.pages.LeadsDetailPage;
import zoho.pages.TopMenuComponent;

public class Leads {
	
	public TestContext context;
	LeadsDescriptionPage leadspage;
	CreateLeadPage createLeadPage;
	LeadsDetailPage leadDetailPage;
	TopMenuComponent topMenuComponent;
	
	public Leads(TestContext context) {
		System.out.println("Leads Constructor");
		this.context = context;
		this.leadspage = this.context.getPageObjectManager().getLeadsPage();
		this.createLeadPage = this.context.getPageObjectManager().getCreateLeadPage();
		this.leadDetailPage = this.context.getPageObjectManager().getLeadDetailPage();
		this.topMenuComponent = this.context.getPageObjectManager().getTopMenu();
	}
	
	@Before
	public void before(Scenario scenario) {
		System.out.println("Starting scenario: "+scenario.getName());
		context.createScenario(scenario.getName());
	}
	
	@After
	public void after(Scenario scenario) {
		System.out.println("Ending scenario: "+scenario.getName());
		context.endScenario();
		context.getPageObjectManager().getWebDriverManager().quit();
	}
	
	@When("I go to {string} page")
	public void verifyLeadsPage(String action) {
		context.log("I go to "+action+" page");
		leadDetailPage.goToPage(action);
	}
	
	@And("I enter and submit lead details")
	public void submitDetails(List <LeadData> leadData) {
		context.log("I enter and submit lead details: "+leadData);
		createLeadPage.submitDetails(leadData);
	}
	
	@DataTableType
	public LeadData leadData(Map<String, String> entry) {
        return new LeadData(entry.get("FirstName"),entry.get("LastName"),entry.get("Email"),entry.get("Company"));
	}
	
	@And("I verify lead details")
	public void verify_lead_det() {
		context.log("I verify lead details");
	}
	
	@Then("Lead should be present inside the Grid")
	public void verify_lead_creation() {
		context.log("Lead should be present inside the Grid");
		topMenuComponent.load("Leads");
	}
	
	@When("I select the lead {string}")
	public void select_name(String val) {
		context.log("I select the lead "+val);
		leadDetailPage.selectLead(val);
		
	}
	
    @And("I click on {string} button")
    public void action(String val) {
		context.log("I click on the "+val+" button");
		leadDetailPage.perform(val);
    }
    
    @Then("lead should be {string}")
    public void check_lead_delete(String action) {
    	context.log("lead should be "+action);
    	leadDetailPage.confirm(action);
    }

}
