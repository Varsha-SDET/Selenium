package varsharaneprojects.testcomponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
    int count =0;
    //re-run count
    int maxTry=1;
    //how many times we want to rerun test

    @Override
    //test metadata present in result variable
    public boolean retry(ITestResult result ) {
        if(count<maxTry){
            count++;
            return true;
        }
        return false;
    }

}
