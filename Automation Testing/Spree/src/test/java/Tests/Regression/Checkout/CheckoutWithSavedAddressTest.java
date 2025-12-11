package Tests.Regression.Checkout;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class CheckoutWithSavedAddressTest extends TestBase {

    SignupPage signupPage;
    HeaderComponent header;
    HomePage homePage;
    AccountPage accountPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CompleteCheckoutPage completeCheckoutPage;


    String homeProductTitle;
    String homeProductPrice;
    int quantity;


    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String countryName = "Egypt";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();

    String fullName = firstName + " " + lastName;

    String address = TestData.generateAddress();
    String apartment = TestData.generateApartment();
    String city = TestData.generateCity();
    String postalCode = TestData.generatePostalCode();
    String phoneNumber = TestData.generatePhoneNumber();

    String cardNumber = TestData.generateVisaCard();
    String expiry = TestData.generateExpiry();
    String CVV = TestData.generateCVV();


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
    public void addAddress() {

        header = new HeaderComponent(driver);
        header.openAccount();

        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        accountPage = new AccountPage(driver);
        accountPage.openAddresses();

        waitFor().until(ExpectedConditions.urlContains("account/addresses"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/addresses"));

        accountPage.addAddress();

        waitFor().until(ExpectedConditions.visibilityOf(accountPage.selectCountry));

        accountPage.addNewAddress(countryName, firstName, lastName,
                address, apartment, city, postalCode, phoneNumber);

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));
        Assert.assertEquals(header.successMessage.getText(), "ADDRESS HAS BEEN SUCCESSFULLY CREATED.");
    }

    @Test(dependsOnMethods = {"addAddress"})
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

        cartPage.openCheckoutPage();

        waitFor().until(ExpectedConditions.urlContains("/checkout/"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/checkout/"));
    }

    @Test(dependsOnMethods = {"addProductToCart"})
    public void useAddedAddress() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.address));

        Assert.assertTrue(checkoutPage.address.getText().contains(address));
        Assert.assertTrue(checkoutPage.fullName.getText().contains(fullName));

        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.addedAddress));
        checkoutPage.chooseAddedAddress();

        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.saveAndContinueButton));

        checkoutPage.clickSaveAndContinueButton();

        waitFor().until(ExpectedConditions.urlContains("/delivery"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/delivery"));
    }

    @Test(dependsOnMethods = {"useAddedAddress"})
    public void selectDeliveryMethod() {

        checkoutPage = new CheckoutPage(driver);

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

        checkoutPage.enterCardDetails(cardNumber, expiry, CVV);
        checkoutPage.clickPayNow();

        waitFor().until(ExpectedConditions.urlContains("/complete"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/complete"));
    }

    @Test(dependsOnMethods = {"fillPaymentDetails"})
    public void completedCheckout() {

        completeCheckoutPage = new CompleteCheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(completeCheckoutPage.orderID));

        Assert.assertEquals(completeCheckoutPage.successMessage.getText(), "Thanks "+ firstName +" for your order!");
        Assert.assertEquals(completeCheckoutPage.orderStatus.getText(), "Paid");

        Assert.assertEquals(completeCheckoutPage.emailAddress.getText(), email);
        Assert.assertTrue(completeCheckoutPage.shippingDetails.getText().contains(lastName));
        Assert.assertTrue(completeCheckoutPage.shippingDetails.getText().contains(address));
        Assert.assertEquals(completeCheckoutPage.phoneNumber.getText(), phoneNumber);
    }
}
