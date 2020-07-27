package runners;

import org.junit.runner.RunWith;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.gherkin.model.Feature;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.junit.Cucumber;
import resources.Base;
import utilities.ExtentReportManager;

@RunWith(Cucumber.class)
@CucumberOptions(
 features = "src/test/java/features",
 glue= {"stepDefinitions"},
 //plugin = { "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
 monochrome = true,
 dryRun =false
 )
public class TestRunner extends Base {
	
	 
	@org.junit.BeforeClass
	public static void beforeCalssSetup() throws Exception {
		
		//Get sheet name for Service Class included in testng xml file
		serviceName ="Products";
		System.out.println("The Class Name/Sheet Name "+serviceName+" execution started");
		getDataSheet();
		
		//Initialize the Extent report
		reportManager= new ExtentReportManager();
		Feature = reportManager.extent.createTest(Feature.class,serviceName,"Product Serice API Test");
	}
	
	@org.junit.AfterClass
	public static void teardown(){
		System.out.println(serviceName + " Execution finished");
		System.out.println("");
		
		reportManager.FlushReport();
		
		
	}

}
