package varsharaneprojects.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import varsharaneprojects.pageobjects.*;

import javax.swing.*;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

/*
 *@className-StandAloneTest
 *@autherName- Varsha Rane
 *@Objective- The objective of test is to login and get products to productlist.
 */

public class StandAloneTest {
   // WebDriver driver;
//    @BeforeTest
//    public void setUp(){
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        driver.get("https://rahulshettyacademy.com/client");
//        driver.manage().window().maximize();
//    }

    public static WebDriver driver;
    @Test
    public static void addToCart() throws NullPointerException {
        //chrome invoke
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://rahulshettyacademy.com/client");
        driver.manage().window().maximize();
        String productName = "ZARA COAT 3";

//        LandingPage landingPage = new LandingPage(driver);
//        landingPage.goTo();
//        //products list return
//        ProductCatalogue productCatalogue = landingPage.loginApplication("varsharane99@gmail.com", "Test@123");
//        List<WebElement> products = productCatalogue.getProductList();
//        productCatalogue.addProductToCart(productName);
//        productCatalogue.goToCartPage();
//        CartPage cartPage= productCatalogue.goToCartPage();
//        Boolean match=cartPage.verifyProductDisplay(productName);
//        Assert.assertTrue(match);
//        CheckoutPage checkoutPage =cartPage.goToCheckout();
//        checkoutPage.selectCountry("india");
//        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
//        String confirmMessage = confirmationPage.getConfirmationMessage();
//        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
//        driver.close();

        //login with valid credential
        driver.findElement(By.id("userEmail")).sendKeys("varsharane99@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Test@123");
        driver.findElement(By.id("login")).click();
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));


       // productName = "T-Shirt";
        //get product list -> iterate -> get product title -> capture and display selected title product (text) -> add to cart
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> productList = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = productList.stream().filter(product ->
                        product.findElement(By.cssSelector("b")).getText().equals(productName.toUpperCase()))
                // .findFirst().orElse(null);
                .findFirst().orElseThrow(() -> new NoSuchElementException("No matching element found"));
        // click on prod's add to cart button (which is last button so last of type )
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
        // click on prod's add to cart button (which is last button so last of type )

        //explicit wait to handle application synchronously on loading
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        //animating icon disappear wait until
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating"))); ->performance issues little bit
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));


           driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
             Assert.assertTrue(match);
      //  checkout click
        driver.findElement(By.cssSelector(".totalRow button")).click();
        Actions actions = new Actions(driver);
        //select country
        actions.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "India").build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
        driver.findElement(By.cssSelector(".action__submit")).click();

        String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
        driver.close();


    }
}
