package resources;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import io.restassured.specification.RequestSpecification;

public class RESTConnections extends Base{

	private RequestSpecification headers;
	private String serviceURI;
	
	
	public RESTConnections(String serviceURI) {
		
		this.serviceURI=serviceURI;
		
		RestAssured.baseURI = "http://localhost:3030";
				
		headers = RestAssured.given()
					.contentType(ContentType.JSON);
	}
	
	
	public Response ExecuteAPIwithQueryparams(String HTTPmethod, Map<String, String>search,List<String>filters) {
		
		if(!search.isEmpty()&& !filters.isEmpty()) {
			
			headers.given().params("$select[]", filters).queryParams(search);
			return ExecuteAPIOperation(HTTPmethod);
					 
	}
		
		else if(search.isEmpty()&& !filters.isEmpty()){
			
			headers.given().param("[]$select", filters);
			return ExecuteAPIOperation(HTTPmethod);
					  
		}
		
		else if(!search.isEmpty()&& filters.isEmpty()){
			
			headers.given().queryParams(search);
			return ExecuteAPIOperation(HTTPmethod);
			
		}
		
		else {
			return ExecuteAPIOperation(HTTPmethod);
			
		}
		
		
		
	}
	
	
	public Response ExecuteAPIwithPathparamAndBody(String HTTPmethod, File jasonFile) {
		
		headers.given().body(jasonFile);
		return ExecuteAPIOperation(HTTPmethod);
		
	}
	
	
	public Response ExecuteAPIwithPathparam(String HTTPmethod, String pathparam ) {
		
		headers.given().pathParam("id", pathparam);
		return ExecuteAPIOperation(HTTPmethod);
		
	}
	
	private Response ExecuteAPIOperation(String HTTPmethod) {
		
		if(HTTPmethod.equalsIgnoreCase("GET"))
			return headers.get(serviceURI);
		else if (HTTPmethod.equalsIgnoreCase("POST"))
		return headers.post(serviceURI);
		
		else if (HTTPmethod.equalsIgnoreCase("PUT"))
			return headers.put(serviceURI);
		
		else if (HTTPmethod.equalsIgnoreCase("DELETE"))
			return headers.delete(serviceURI);
		else
		return null;
	}
	
}
