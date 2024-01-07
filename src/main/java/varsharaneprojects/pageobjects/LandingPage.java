package varsharaneprojects.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import varsharaneprojects.abstractcomponents.AbstractComponent;

/*
 *@className - LandingPage
 * @classObjective- To define all the webelements for landing page
 */
public class LandingPage extends AbstractComponent {
   public WebDriver driver;
    public  LandingPage(WebDriver driver){
        super(driver);
        //initialize
        this.driver= driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(id="userEmail")
    private WebElement userEmail;
    @FindBy(id="userPassword")
    private WebElement passwordEle;
    @FindBy(id="login")
    private WebElement submit;
    @FindBy(css="[class*='flyInOut']")
    private WebElement errorMessage;

    public ProductCatalogue loginApplication(String email,String password){
        userEmail.sendKeys(email);
        passwordEle.sendKeys(password);
        submit.click();
        ProductCatalogue productCatalogue = new ProductCatalogue(driver);
        return productCatalogue;
    }

    public String getErrorMessage(){
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public void goTo() {
        //application landing page
        driver.get("https://rahulshettyacademy.com/client");
    }
}
