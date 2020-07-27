package stepDefinitions;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import resources.Base;
import resources.RESTConnections;

public class Hooks extends Base {
	
	@Before
	public void beforeSceanrio(Scenario scenario) throws Exception {
		
		TSName = scenario.getName();
		System.out.println("****"+TSName+"****");	
		
	    //Reading data for Test case
	   	readDataFromXlSheet();
	   	
			   	
		//Jason File for POST/PUT request
		
				if (DataSheet.get("HTTPRequestType").equals("POST")||
						DataSheet.get("HTTPRequestType").equals("PUT")) {
					
				}
				
		//Create Node for Scenario in ExtentRepot 
				
			ScenarioName = Feature.createNode(TSName);
	
	}	

	@After
	public void afterScenario(Scenario scenario) {
		
		DataSheet.clear();
		searchCriteria.clear();
		filters.clear();
		
		if (scenario.isFailed())
			ScenarioName.log(Status.FAIL, MarkupHelper.createLabel(scenario.getName()+" is not successfuly executed", ExtentColor.RED));
		
	}
}
