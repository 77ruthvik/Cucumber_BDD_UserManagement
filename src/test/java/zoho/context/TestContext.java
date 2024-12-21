package zoho.context;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import zoho.managers.PageObjectManager;
import zoho.reports.ExtentManager;

public class TestContext {
	public ExtentReports rep;
	public ExtentTest test;
	public PageObjectManager pageObjectManager;
	
	public TestContext() throws FileNotFoundException, IOException {
		System.out.println("TestContext class Constructor");
		rep = ExtentManager.getReports(); //Initialize the reports
		pageObjectManager = new PageObjectManager();
	}
	
	public PageObjectManager getPageObjectManager() {
		return pageObjectManager;
	}
	
	public void createScenario(String name) {
		test = rep.createTest(name);
		this.pageObjectManager.getWebDriverManager().init(test);
	}
	
	public void endScenario() {
		rep.flush();
	}
	
	public void log(String text) {
		//test.log(Status.INFO, text);
		this.getPageObjectManager().getWebDriverManager().log(text);
		System.out.println(text);
	}
}
