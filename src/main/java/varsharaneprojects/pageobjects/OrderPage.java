package varsharaneprojects.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import varsharaneprojects.abstractcomponents.AbstractComponent;

import java.util.List;

public class OrderPage extends AbstractComponent {
    WebDriver driver;
    @FindBy(css=".totalRow button")
    private WebElement checkoutEle;
    @FindBy(css="tr td:nth-child(3)")
    private List<WebElement> productNames;

    @FindBy(css=".cartSection h3")
    private List<WebElement> cartProducts;
    public OrderPage(WebDriver driver){
        super(driver);
        this.driver =driver;
        PageFactory.initElements(driver,this);
    }
    //check product web element displayed in orders or not.
    public Boolean verifyOrderDisplay(String productName){
        Boolean match = productNames.stream().anyMatch(product->
                product.getText().equalsIgnoreCase(productName));
                return match;
    }
    public CheckoutPage goToCheckout(){
        checkoutEle.click();
        return new CheckoutPage(driver);
    }
}
