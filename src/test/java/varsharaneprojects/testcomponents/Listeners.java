package varsharaneprojects.testcomponents;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import varsharaneprojects.resources.ExtentReporterNG;

import java.io.IOException;

public class Listeners extends BaseTest implements ITestListener {
    ExtentTest test;
    ExtentReports extent = ExtentReporterNG.getReportObject();
    //thread safe
    ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); //expecting thread extent object in threadlocal



    public void onTestStart(ITestResult result) {
        //testcase name returned
       test= extent.createTest(result.getMethod().getMethodName());
       //setting non-thread safe object in thread safe
        extentTest.set(test);
        //(set by pushing test in thread local)unique thread id captures ->(creates map inside it internally ) test
    }

    public void onTestSuccess(ITestResult result) {
        //can be considered for not passing this line
        test.log(Status.PASS,"Test Passed");
    }

    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        //to get the same driver from browser and for that have to take screenshot and report
        try {
            //info of class-> test class -> real class-> get field name (becoz fields are present at class level not method level)
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
                    .get(result.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String filePath = null;
        try {
            filePath = getScreenshot(result.getMethod().getMethodName(),driver);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //extentTest.get() -> to get that thread uniue id
        extentTest.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
        //take screenshot  -> attach to file with test case name
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        this.onTestFailure(result);
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
        extent.flush();  // Ensure to flush the report to save it
    }
}
