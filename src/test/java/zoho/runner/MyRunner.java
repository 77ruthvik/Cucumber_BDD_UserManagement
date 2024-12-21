package zoho.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        //features = "@rerun/failed_scenarios.txt",
		features = "src/test/resources/zoho/",
        glue = {"zoho.teststeps"},
        plugin = { "html:target/cucumber-reports.html", "rerun:rerun/failed_scenarios.txt"},
        //1st scenario: @Buycomputer and @BuyingTab //Execute BuyingTab in BuyComputer feature
        //2nd scenario: @Buycomputer and @BuyingDesktop //Execute BuyingDesktop in BuyComputer feature
        //3rd scenario: @BuyCar
        //or means execute 1st and 2nd scenario (It's a scenario separator)
        //or not //means don't execute the scenario
        //tags = "@Buycomputer and @BuyingTab or @Buycomputer and @BuyingDesktop or not @BuyCar",
        tags = "@Leads",
        monochrome = false,
        dryRun = false
)
public class MyRunner extends AbstractTestNGCucumberTests {

}
