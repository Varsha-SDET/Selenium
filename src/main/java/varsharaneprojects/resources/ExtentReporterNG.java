package varsharaneprojects.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    public static ExtentReports getReportObject(){
        //providing path where file is created and config
        String path = System.getProperty("user.dir")+ "//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        //(main) providing reporter(path and config deatils) for test execution
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester","Varsha Rane");
        //entry for created test (reporter test)
        extent.createTest(path);
        return extent;
    }
}
