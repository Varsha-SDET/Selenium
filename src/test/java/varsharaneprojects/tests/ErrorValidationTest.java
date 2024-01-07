package varsharaneprojects.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import varsharaneprojects.pageobjects.CartPage;
import varsharaneprojects.pageobjects.ProductCatalogue;
import varsharaneprojects.testcomponents.BaseTest;
import varsharaneprojects.testcomponents.Retry;

import java.io.IOException;
import java.util.List;

/*
 *@className-ErrorValidationTest
 *@autherName- Varsha Rane
 *@Objective- The objective of test is to check errors after passing invalid credentials
 */
public class ErrorValidationTest extends BaseTest {
    @Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class)
    public void loginErrorValidation() throws IOException,InterruptedException {
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue = landingPage.loginApplication("varsharane99@gmail.com", "Tes@123");
        Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());

    }

    @Test
    public void productErrorValidation() throws IOException,InterruptedException {
        String productName = "ZARA COAT 3";
        ProductCatalogue productCatalogue = landingPage.loginApplication("varsharane99@gmail.com", "Test@123");
        List<WebElement> products = productCatalogue.getProductList();
        productCatalogue.addProductToCart(productName);
        CartPage cartPage= productCatalogue.goToCartPage();
        Boolean match=cartPage.verifyProductDisplay("I phon");
        Assert.assertFalse(match);

    }

}
