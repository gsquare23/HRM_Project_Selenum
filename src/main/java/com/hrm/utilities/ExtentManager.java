package com.hrm.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	
	
	private static ExtentReports extentReport;
	
	private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); 

    private ExtentManager() {
    }
    
    public static synchronized ExtentReports getReporter() {
		if(extentReport == null)
		{
			String ReportPath  = System.getProperty("user.dir")+ "/src/test/resources/ExtentReports/ExtentReport.html";
			
			ExtentSparkReporter spark = new ExtentSparkReporter(ReportPath);

            spark.config().setDocumentTitle(
                    "HRM Automation Report");

            spark.config().setReportName(
                    "OrangeHRM Test Execution");
            
            extentReport = new ExtentReports();
            
            extentReport.attachReporter(spark);
            

            extentReport.setSystemInfo("Project", "OrangeHRM");
            extentReport.setSystemInfo("Framework", "Selenium");
            extentReport.setSystemInfo("Test Runner", "TestNG");
		}
		
		return extentReport;
    	
    }
    
    
    public static void createTest(String testName) {

        ExtentTest test = getReporter().createTest(testName);
        extentTest.set(test);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }

    public static void removeTest() {
        extentTest.remove();
    }

    public static synchronized void flushReport() {

        if (extentReport != null) {
        	extentReport.flush();
        }
    }
	
}
