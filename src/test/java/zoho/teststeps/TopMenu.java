package zoho.teststeps;

import io.cucumber.java.en.And;
import zoho.context.TestContext;
import zoho.pages.TopMenuComponent;

public class TopMenu {
	public TestContext context;
	public TopMenuComponent topmenu;
	
	public TopMenu(TestContext context) {
		System.out.println("TopMenu Constructor");
		this.context = context;
		this.topmenu = this.context.getPageObjectManager().getTopMenu();
	}
	
	@And("I click on {string} in top menu")
	public void load_page(String menu_op) {
		context.log("I click on "+menu_op+" in top menu");
		topmenu.load(menu_op);
	}

}
