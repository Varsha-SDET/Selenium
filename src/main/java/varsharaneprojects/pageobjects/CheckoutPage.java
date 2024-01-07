package varsharaneprojects.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import varsharaneprojects.abstractcomponents.AbstractComponent;

import javax.swing.*;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css=".action__submit")
    private WebElement submit;
    @FindBy(css ="[placeholder='Select Country']")
    private WebElement country;
    @FindBy(xpath="//button[contains(@class,'ta-item')]")
    private WebElement selectCountry;

    //not web element so written directly
    By results = By.cssSelector(".ta-results");

    public void selectCountry(String countryName){
        Actions actions= new Actions(driver);
        actions.sendKeys(country,countryName).build().perform();
        waitForElementToAppear(results);
        selectCountry.click();
    }
    public ConfirmationPage submitOrder(){
        submit.click();
        return new ConfirmationPage(driver);
    }

}
