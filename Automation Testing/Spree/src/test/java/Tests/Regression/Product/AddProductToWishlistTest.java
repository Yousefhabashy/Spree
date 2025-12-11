package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddProductToWishlistTest extends TestBase {

    SignupPage signupPage;
    HeaderComponent header;
    HomePage homePage;
    ProductPage productPage;
    AccountPage accountPage;


    String homeProductTitle;
    String homeProductPrice;

    String email = TestData.generateEmail();
    String password = TestData.generatePassword();


    @Test(priority = 1)
    public void signUpUser(){

        signupPage = new SignupPage(driver);

        driver.navigate().to("https://demo.spreecommerce.org/user/sign_up");

        waitFor().until(ExpectedConditions.urlContains("user/sign_up"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("user/sign_up"));
        signupPage.signUpUser(email, password);

        waitFor().until(ExpectedConditions.urlContains("https://demo.spreecommerce.org"));

        header = new HeaderComponent(driver);
        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));

        Assert.assertEquals(header.successMessage.getText(), "WELCOME! YOU HAVE SIGNED UP SUCCESSFULLY.");
    }

    @Test(dependsOnMethods = {"signUpUser"})
    public void openProductPage() {

        header = new HeaderComponent(driver);

        header.openWomanFashion();

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(homePage.productTitle));
        Actions actions = new Actions(driver);
        actions.moveToElement(homePage.productTitle).perform();

        homeProductTitle = homePage.productTitle.getText();
        homeProductPrice = homePage.productPrice.getText();

        homePage.openProductPage();
    }

    @Test(dependsOnMethods = {"openProductPage"})
    public void checkRightProductOpens() {

        productPage = new ProductPage(driver);

        waitFor().until(ExpectedConditions.urlContains("products/crew-neck-tee"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("products/crew-neck-tee"));

        Assert.assertEquals(productPage.productTitle.getText().toLowerCase(), homeProductTitle.toLowerCase());
        Assert.assertEquals(productPage.productPrice.getText(), homeProductPrice);
    }

    @Test(dependsOnMethods = {"checkRightProductOpens"})
    public void addProductToWishlist() {

        productPage = new ProductPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToWishlist));
        waitFor().until(ExpectedConditions.elementToBeClickable(productPage.addToWishlist));

        productPage.addToWishlist();
    }

    @Test(dependsOnMethods = {"addProductToWishlist"})
    public void checkAddedToWishlist() {

        header = new HeaderComponent(driver);

        header.openAccount();

        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        accountPage = new AccountPage(driver);

        accountPage.openWishlist();

        waitFor().until(ExpectedConditions.urlContains("account/wishlist"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/wishlist"));

        waitFor().until(ExpectedConditions.visibilityOf(accountPage.wishlistProductTitle));

        Assert.assertEquals(homeProductTitle.toUpperCase(), accountPage.wishlistProductTitle.getText());
        Assert.assertEquals(homeProductPrice, accountPage.wishlistProductPrice.getText());
    }
}
