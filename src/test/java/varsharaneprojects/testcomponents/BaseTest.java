package varsharaneprojects.testcomponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import varsharaneprojects.pageobjects.LandingPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
//    public static WebDriver driver;
//    public static LandingPage landingPage;
    public  WebDriver driver;
    public  LandingPage landingPage;
    public WebDriver initializeDriver() throws IOException {
        //properties class
        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//varsharaneprojects//resources//GlobalData.properties");
        properties.load(fileInputStream);

        //take from terminal if given else get from global properties file
        String browserName =System.getProperty("browser")!=null ? System.getProperty("browser") :
                properties.getProperty("browser");

       // String browserName = properties.getProperty("browser");
        if(browserName.contains("chrome")){
            //chrome
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            if(browserName.contains("headless")){
                options.addArguments("headless");
            }
            this.driver = new ChromeDriver(options);
            //full screen dimension
            driver.manage().window().setSize(new Dimension(1440,900));

        } else if (browserName.equalsIgnoreCase("firefox")) {
            //firefox
            WebDriverManager.firefoxdriver().setup();
             driver =new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            //edge
            WebDriverManager.edgedriver().setup();
             driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    //send screenshot for failed test case with testcase name
    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {
        TakesScreenshot ts=(TakesScreenshot)driver;
        File source =ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir")+"//reports//"+ testCaseName + ".png");
        FileUtils.copyFile(source,file);
        //after taking ss returning path of local system where it is stored
        return System.getProperty("user.dir")+ "//reports//" + testCaseName + ".png";

    }

    //to call it without creating obj and directly can be accessible in its child class
    public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
        //to read json to string
        String jsonContent = FileUtils.readFileToString(new File(filePath),
                StandardCharsets.UTF_8);
        //string to hashmap jackson databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        //list-> of hashmaps returned using object mapper read value method
        return data;
    }

    @BeforeMethod(alwaysRun = true)
    public LandingPage launchApplication()throws IOException{
        this.driver = initializeDriver();
        landingPage = new LandingPage(driver);
        landingPage.goTo();
        return landingPage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.close();

    }

}
