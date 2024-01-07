package varsharaneprojects.abstractcomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import varsharaneprojects.pageobjects.CartPage;
import varsharaneprojects.pageobjects.OrderPage;

import java.time.Duration;

/*
 *@className - AbstractComponents
 * @classObjective- To define all the reusable components
 */
public class AbstractComponent {
    WebDriver driver;
    public AbstractComponent(WebDriver driver) {
        //taking life of driver from page object (call from child by super keyword)
        this.driver =driver;
        PageFactory.initElements(driver,this);
    }

    //header is common to all page and cart is present there so present in abstactComponent class
    @FindBy(css="[routerlink*='cart']")
    private WebElement cartHeader;
    @FindBy(css="[routerlink*=myorders]")
    private WebElement orderHeader;

    public CartPage goToCartPage(){
        cartHeader.click();
        CartPage cartPage= new CartPage(driver);
        return cartPage;
    }

    public OrderPage goToOrderPage(){
        orderHeader.click();
        OrderPage orderPage= new OrderPage(driver);
        return orderPage;
    }
    public void waitForElementToAppear(By findBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
    }
    public void waitForWebElementToAppear(WebElement findBy){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(findBy));
    }
    public void waitForElementToDisappear(WebElement ele){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.invisibilityOf(ele));

    }

}
