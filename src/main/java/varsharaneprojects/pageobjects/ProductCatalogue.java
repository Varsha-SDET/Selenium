package varsharaneprojects.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import varsharaneprojects.abstractcomponents.AbstractComponent;

import java.time.Duration;
import java.util.List;
/*
 *@className - ProductCatalogue
 * @classObjective- To define all the webelements for product catalogue page
 */
public class ProductCatalogue extends AbstractComponent {
    WebDriver driver;
    public ProductCatalogue(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    @FindBy(css = ".mb-3")
    private List<WebElement> products;
    @FindBy(css= ".ng-animating")
    private WebElement spinner;

    By productBy = By.cssSelector(".mb-3");
    By addToCart = By.cssSelector(".card-body button:last-of-type");
    By toastMessage =By.cssSelector("#toast-container");
    public List<WebElement> getProductList(){
        waitForElementToAppear(productBy);
        return products;
    }
    public WebElement getProductByName(String productName){
        WebElement prod = getProductList().stream().filter(product->
                product.findElement(By.cssSelector("b")).getText().equals(productName))
                .findFirst().orElse(null);
        return prod;
    }
    public void addProductToCart(String productName){
        WebElement prod =getProductByName(productName);
        prod.findElement(addToCart).click();
        waitForElementToAppear(toastMessage);
        waitForElementToDisappear(spinner);

    }

}
