package utilities;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import resources.Base;

public class ExtentReportManager extends Base {
	
	
	    private static String fileSeperator = System.getProperty("file.separator");
	    private static String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "TestReport";
	    private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
	
	
	public ExtentReportManager() {
		
		extent = new ExtentReports();
		
		String fileName = getReportPath(reportFilepath);
	      
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
 
        
        extent.attachReporter(htmlReporter);
        //Set environment details
		extent.setSystemInfo("OS", "Windows");
		extent.setSystemInfo("AUT", "QA");
	}
	
	public void FlushReport() {
		extent.flush();
	}
	
	
	private  String getReportPath (String path) {
    	File testDirectory = new File(path);
        if (!testDirectory.exists()) {
        	if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
		return reportFileLocation;
    }

}
