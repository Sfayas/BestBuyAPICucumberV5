package stepDefinitions;

import java.util.List;

import com.aventstack.extentreports.GherkinKeyword;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.collect.Ordering;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import groovy.xml.MarkupBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import junit.framework.Assert;
import pojo.Data;
import pojo.ResponsePayLoad;
import resources.Base;
import resources.RESTConnections;

public class StepsDef extends Base{
	
	RESTConnections restConnection;
	private static Response response;
	ResponsePayLoad respPayload;
	
	@Given("^BaseURI and Headers$")
	public void baseuri_and_Headers() throws Throwable {
		ScenarioName.createNode(new GherkinKeyword("Given"),"BaseURI and Headers");
		 
		//logger.log(Status.INFO,"RESTConnection");
		//Initialize REST Connection
		restConnection = new RESTConnections(DataSheet.get("Service"));
	}

/*	@Given("^ReadTestData$")
	public void readtestdata() throws Throwable {
		
	     
	}*/

	@Given("^User serach products with \"([^\"]*)\"$")
	public void user_serach_products_with(String arg1) throws Throwable {
		logger=ScenarioName.createNode(new GherkinKeyword("Given"),"User serach products with filters and SearchCriteria");
		logger.log(Status.INFO,"Execution Started");
		response =restConnection.ExecuteAPIwithQueryparams("GET", searchCriteria, filters);
		respPayload =response.getBody().as(ResponsePayLoad.class);
	}

	
	
	@Then("^System returns success code \"([^\"]*)\"$")
	public void system_returns_success_code(String respcode) throws Throwable {
		logger=ScenarioName.createNode(new GherkinKeyword("Then"),"System returns success code");
		logger.log(Status.INFO, MarkupHelper.createLabel("ReurtnCode is "+respcode, ExtentColor.BLUE));
		Assert.assertEquals((Integer.parseInt(respcode)),response.getStatusCode());
	}

	@Then("^limit should be given limit$")
	public void limit_should_be_given() throws Throwable {
		
		//Assert.assertEquals(Integer.parseInt(DataSheet.get("SearchLimit")), response.body().path("limit"));
		//Assert.assertEquals(50, response.body().path("limit"));
		ScenarioName.createNode(new GherkinKeyword("Then"),"limit should be given limit");
		respPayload =response.getBody().as(ResponsePayLoad.class);
		System.out.println("Limit is "+respPayload.getLimit());
		//Assert.assertEquals(Integer.parseInt(DataSheet.get("SearchLimit")), respPayload.getLimit());
		Assert.assertEquals(50, respPayload.getLimit());
	}
	
	@Then("^verify system returns only selected columns$")
	public void verify_system_returns_only_selected_columns() throws Throwable {
	     
		/*JsonPath Jsonresponse= response.jsonPath();
		List <String> manufacturer = Jsonresponse.getList("data.manufacturer");
		Assert.assertTrue(!(manufacturer.isEmpty()));
		System.out.println(manufacturer);*/
		ScenarioName.createNode(new GherkinKeyword("Then"),"verify system returns only selected columns");
		List<Data> data = respPayload.getData();
		
		Assert.assertTrue(!data.get(0).getManufacturer().isEmpty());
		
		
	}

	@Then("^Verify the results matching with SearchCriteria$")
	public void verify_the_results_matching_with_SearchCriteria() throws Throwable {

		ScenarioName.createNode(new GherkinKeyword("Then"),"Verify the results matching with SearchCriteria");
		Assert.assertEquals(2698105, response.path("data.id[0]"));
		System.out.println(response.path("data.id[0]"));
		
		//To verify the order of the result by ID and no duplicates in ID
		JsonPath Jsonresponse= response.jsonPath();
		List <Integer> id = Jsonresponse.getList("data.id");
		//Assert.assertTrue(Ordering.natural().isOrdered(id));
		
		Assert.assertTrue(Ordering.natural().isStrictlyOrdered(id));
	}

	
}
