package varsharaneprojects.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import varsharaneprojects.pageobjects.*;
import varsharaneprojects.testcomponents.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/*
 *@className-SubmitOrderTest
 *@autherName- Varsha Rane
 *@Objective- The objective of test is to submit order by adding product to cart
 *            after successfull login(for standalone test implemnt in framework)
 */
public class SubmitOrderTest extends BaseTest {
    String productName = "ZARA COAT 3";

    @Test(dataProvider = "getData",groups = {"Purchase"})
    //paramerization-> //public void submitOrder(String email,String password,String productName) throws IOException {

    public void submitOrder(HashMap<String,String> input) throws IOException {

            //products list return
       //parameterization-> ProductCatalogue productCatalogue = landingPage.loginApplication(email, password);
        ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(input.get("productName"));
        CartPage cartPage= productCatalogue.goToCartPage();

        Boolean match=cartPage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);
        CheckoutPage checkoutPage =cartPage.goToCheckout();
        checkoutPage.selectCountry("india");
        ConfirmationPage confirmationPage = checkoutPage.submitOrder();
        String confirmMessage = confirmationPage.getConfirmationMessage();
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
    }

    @Test(dependsOnMethods ={"submitOrder"} )
    public void orderHistory() {
        ProductCatalogue productCatalogue = landingPage.loginApplication("varsharane99@gmail.com", "Test@123");
        OrderPage orderPage = productCatalogue.goToOrderPage();
        Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
    }

    //using paramiterization
//    @DataProvider
//    public Object[][] getData(){
//        return new Object[][]{ {"varsharane99@gmail.com","Test@123","ZARA COAT 3"},{"shetty@gmail.com","Iamking000","ADIDAS ORIGINAL"}};
//    }
    //using hashmap to data provider to send data as one hash map
    @DataProvider
    public Object[][] getData() throws IOException {
//        //using hashmap directly
//       HashMap<String,String> map = new HashMap<String,String>();
//        map.put("email","varsharane99@gmail.com");
//        map.put("password","Test@123");
//        map.put("productName","ZARA COAT 3");
//
//        HashMap<String,String> map1 = new HashMap<String, String>();
//        map1.put("email","varsharane99@gmail.com");
//        map1.put("password","Test@123");
//        map1.put("productName","ADIDAS ORIGINAL");
            List<HashMap<String,String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java//varsharaneprojects//data//PurchaseOrder.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
    }

}
