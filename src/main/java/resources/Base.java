package resources;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utilities.ExcelFunctions;
import utilities.ExtentReportManager;

public class Base {

	public static RequestSpecification headers;
	public static String Datasheetfilepath,TSName,serviceName;
	
	public static Map<String, String> DataSheet = new HashMap<String, String>();
	public static Map<String, String> searchCriteria = new HashMap<String, String>();
	public static List<String> filters =new ArrayList<String>();
	
	public static File JsonInputFile;
	
	public ExtentReports extent;
	public static ExtentTest Feature,ScenarioName,logger;
	public static ExtentReportManager reportManager;
	public static String reportFileName = "API-SwaggerAuotmationCucumber"+".html";
	
	public static ExcelFunctions xl;
	private static int rowcount,columncount,columnno=0;
	

	public static void getDataSheet() throws Exception {
		
		//get Data sheet  
		
		Datasheetfilepath=System.getProperty("user.dir")+File.separator+"TestData"+File.separator+"DataSheet.xlsx";
		xl= new ExcelFunctions(Datasheetfilepath);
		
	}
	
	
	 public static void readDataFromXlSheet() throws Exception{
		
		//Reading data for Test case
	   
		rowcount =xl.getRowCount(serviceName);
		columncount =xl.getColumnCount();
		columnno = xl.findColumnNo(columncount, TSName);
		 
		if(!(columnno==0)) {
		for (int row=2;row<rowcount;row++) {
			
			DataSheet.put(xl.readCellData(row, 0),xl.readCellData(row, columnno));
			
			}
		}
		
		else {
			System.out.println(TSName +" is not Found in the data sheet");
			
		}
		
		//set Search Limit 
		if(!(DataSheet.get("SearchLimit").trim().equals("") || DataSheet.get("SearchLimit").isEmpty() || Integer.parseInt(DataSheet.get("SearchLimit"))==0 )) {
			
			searchCriteria.put("$limit",DataSheet.get("SearchLimit"));
			
		}
		
		System.out.println(DataSheet.get("Shipping"));
		//Set Search Criteria
		if(DataSheet.get("Search_Criteria").equals("Y")) {
			
			setSearchCriteria();
			
		}
		
		if(DataSheet.get("Result_Filters").equals("Y")) {
			
			setResultFilter();
			
			
		}
		
	 }
	
	/* public void initiateRESTConnection() {
		 
		RestAssured.baseURI = "http://localhost:3030";
		//RestAssured.authentication= RestAssured.basic("admin", "04Mf1286$");
		
		headers = RestAssured.given()
					.contentType(ContentType.JSON);
		
	 }*/
	
	
	public static void setSearchCriteria() {
		
		int temprow =xl.findRowNo(rowcount, "Search_Criteria");
		int temprowcount =xl.findRowNo(rowcount, "Result_Filters");
		for(int i=temprow+1;i<temprowcount;i++) {
			
			String searchKey =xl.readCellData(i, 0);
			String searchValue =xl.readCellData(i, columnno);
			
			if(!searchValue.equals("")) {
				searchCriteria.put(setRealSearchKey(searchKey), searchValue);
			}
			
			else {
				continue;
			}
		}
	}
	
	public static String setRealSearchKey(String key) {
		
		String realKey="";
		
		switch (key){
			
			case "ProductID":
				realKey ="id";
				break;
						
			case "ProductType":
				realKey = "type";
				break;
				
			case "ProductName":
				realKey = "name";
				break;
			
			case "Price_Equals":
				
				realKey="price[$eq]";
				break;
			
			case "Price_LTE":
				realKey = "price[$lte]";
				break;
				
			case "Price_GTE":
				realKey = "price[$gte]";
				break;
				
			case "Shipping":
				realKey ="shipping[$eq]";
				break;
				
			case "Manufacturer":
				realKey= "manufacturer";
				break;
				
			case "Model":
				realKey ="model";
				break;
				
			case "CategoryID":
				realKey ="category.id";
				break;
				
			case "CategoryName":
				realKey ="category.name";
				break;
				
				
		}
		
		return realKey;
		
	}
	
	public static void setResultFilter() {
		
		int temprow =xl.findRowNo(rowcount, "Result_Filters");
		
		for(int i=temprow+1;i<rowcount;i++) {
			
			String filterName =xl.readCellData(i, 0);
			String filterValue =xl.readCellData(i, columnno);
			
			
			if(filterValue.equalsIgnoreCase("Y")) {
				filters.add(getFilterValue(filterName));
				
				
			}
			
			else {
				continue;
			}
		}
	}
	
	private static String getFilterValue(String name) {
			
		String realfilterName="";
		
			
		switch (name){
			
			case "Product_ID":
				realfilterName ="id";
				break;
						
			case "Product_Type":
				realfilterName = "type";
				break;
				
			case "Product_Name":
				realfilterName = "name";
				break;
			
			case "Price":
				realfilterName="price";
				break;
			
			case "Shipping_Filter":
				realfilterName ="shipping";
				break;
				
			case "Manufacturer_Filter":
				realfilterName= "manufacturer";
				break;
				
			case "Model_Filter":
				realfilterName ="model";
				break;
				
		}
		
		return realfilterName;
		
		
		
	}
	
	public static Object dataFromJasonFile(String key) throws Exception {
		Object obj =new JSONParser().parse((new FileReader(".//TestData/Data1.json")));
		JSONObject jObj = (JSONObject) obj;
		Object value = jObj.get(key);
		System.out.println(value);
		
		return value;
	}
}
