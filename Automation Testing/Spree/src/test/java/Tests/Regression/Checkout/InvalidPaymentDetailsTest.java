package Tests.Regression.Checkout;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class InvalidPaymentDetailsTest extends TestBase {


    HeaderComponent header;
    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    String homeProductTitle;
    String homeProductPrice;
    int quantity;

    String email = TestData.generateEmail();
    String countryName = "Egypt";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();

    String address = TestData.generateAddress();
    String apartment = TestData.generateApartment();
    String city = TestData.generateCity();
    String postalCode = TestData.generatePostalCode();
    String phoneNumber = TestData.generatePhoneNumber();

    String invalidCardNumber = TestData.generateDeclinedCardNumber();
    String expiry = TestData.generateExpiry();
    String CVV = TestData.generateCVV();


    @Test(priority = 1)
    public void openProductPage() {

        header = new HeaderComponent(driver);

        header.openWomanFashion();

        homePage = new HomePage(driver);
        waitFor().until(ExpectedConditions.urlContains("categories/fashion/women"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("categories/fashion/women"));

        waitFor().until(ExpectedConditions.visibilityOf(homePage.productTitle));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.productTitle));

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
    public void addProductToCart() {

        productPage = new ProductPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(productPage.chooseSizeButton));
        productPage.chooseSize("M");

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToCartButton));
        Assert.assertTrue(productPage.addToCartButton.isDisplayed());

        quantity = productPage.getQuantity();

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToCartButton));
        waitFor().until(ExpectedConditions.elementToBeClickable(productPage.addToCartButton));
        productPage.addToCart();
    }

    @Test(dependsOnMethods = {"addProductToCart"})
    public void checkAddedToCart() {

        cartPage = new CartPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(cartPage.cartProductsContainer));

        waitFor().until(ExpectedConditions.visibilityOf(cartPage.cartFirstProductTitle));
        Assert.assertEquals(cartPage.cartFirstProductTitle.getText().toLowerCase() ,homeProductTitle.toLowerCase());
        Assert.assertEquals(cartPage.cartFirstProductPrice.getText() ,homeProductPrice);
        Assert.assertEquals(cartPage.getProduct1Quantity() ,quantity);
    }

    @Test(dependsOnMethods = {"checkAddedToCart"})
    public void fillCheckoutShippingAddress() {

        cartPage = new CartPage(driver);

        cartPage.openCheckoutPage();

        waitFor().until(ExpectedConditions.urlContains("/address"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/address"));

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.emailMeButton));

        Assert.assertTrue(checkoutPage.emailMeButton.isDisplayed());

        checkoutPage.enterGuestEmail(email);

        checkoutPage.fillAddress(countryName, firstName, lastName,
                address, apartment, city, postalCode, phoneNumber);

        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.saveAndContinueButton));

        checkoutPage.clickSaveAndContinueButton();

        waitFor().until(ExpectedConditions.urlContains("/delivery"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/delivery"));
    }

    @Test(dependsOnMethods = {"fillCheckoutShippingAddress"})
    public void selectDeliveryMethod() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.urlContains("/delivery"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/delivery"));

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.shipEmail));

        Assert.assertTrue(checkoutPage.shipEmail.getText().contains(email));

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliverySaveAndContinueButton));
        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.deliverySaveAndContinueButton));

        checkoutPage.selectStandard();

        waitFor().until(ExpectedConditions.urlContains("/payment"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/payment"));

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliveryMethod));

        Assert.assertTrue(checkoutPage.deliveryMethod.getText().contains("Standard"));
    }

    @Test(dependsOnMethods = {"selectDeliveryMethod"})
    public void fillPaymentDetails() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliveryMethod));

        Assert.assertTrue(checkoutPage.deliveryMethod.getText().contains("Standard"));

        checkoutPage.enterCardDetails(invalidCardNumber, expiry, CVV);
        checkoutPage.clickPayNow();
    }

    @Test(dependsOnMethods = {"fillPaymentDetails"})
    public void checkErrorMessage() {

        header = new HeaderComponent(driver);

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));

        Assert.assertEquals(header.successMessage.getText(), "YOUR CARD NUMBER IS INVALID.");
    }
}
