package Tests.Regression.Checkout;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class OrderIDTest extends TestBase {

    SignupPage signupPage;
    HeaderComponent header;
    HomePage homePage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CompleteCheckoutPage completeCheckoutPage;
    OrderPage orderPage;


    String homeProductTitle;
    String homeProductPrice;
    String productColor;
    String productSize = "L";
    int quantity;



    String product2Title;
    String product2Price;
    String product2Color;
    String product2Size = "L";
    int product2Quantity;

    String order1Id;
    String order2Id;

    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String countryName = "Egypt";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();


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
    public void addProduct1ToCart() {

        productPage = new ProductPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(productPage.chooseSizeButton));
        productPage.chooseSize(productSize);

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToCartButton));
        Assert.assertTrue(productPage.addToCartButton.isDisplayed());

        quantity = productPage.getQuantity();

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToCartButton));
        waitFor().until(ExpectedConditions.elementToBeClickable(productPage.addToCartButton));
        productPage.addToCart();
    }

    @Test(dependsOnMethods = {"addProduct1ToCart"})
    public void checkAddedToCart() {

        cartPage = new CartPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(cartPage.cartProductsContainer));

        waitFor().until(ExpectedConditions.visibilityOf(cartPage.cartFirstProductTitle));
        Assert.assertEquals(cartPage.cartFirstProductTitle.getText().toLowerCase() ,homeProductTitle.toLowerCase());
        Assert.assertEquals(cartPage.cartFirstProductPrice.getText() ,homeProductPrice);
        Assert.assertEquals(cartPage.getProduct1Quantity() ,quantity);

        productColor = cartPage.cartFirstProductColor.getText();
    }

    @Test(dependsOnMethods = {"checkAddedToCart"})
    public void order1FillCheckoutShippingAddress() {

        cartPage = new CartPage(driver);

        cartPage.openCheckoutPage();

        waitFor().until(ExpectedConditions.urlContains("/address"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/address"));

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.emailMeButton));

        Assert.assertTrue(checkoutPage.emailMeButton.isDisplayed());

        checkoutPage.fillAddress(countryName, firstName, lastName,
                address, apartment, city, postalCode, phoneNumber);

        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.saveAndContinueButton));

        checkoutPage.clickSaveAndContinueButton();

        waitFor().until(ExpectedConditions.urlContains("/delivery"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/delivery"));
    }

    @Test(dependsOnMethods = {"order1FillCheckoutShippingAddress"})
    public void order1SelectDeliveryMethod() {

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

    @Test(dependsOnMethods = {"order1SelectDeliveryMethod"})
    public void order1FillPaymentDetails() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliveryMethod));

        Assert.assertTrue(checkoutPage.deliveryMethod.getText().contains("Standard"));

        checkoutPage.enterCardDetails(cardNumber, expiry, CVV);
        checkoutPage.clickPayNow();

        waitFor().until(ExpectedConditions.urlContains("/complete"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/complete"));
    }

    @Test(dependsOnMethods = {"order1FillPaymentDetails"})
    public void order1CompletedCheckout() {

        completeCheckoutPage = new CompleteCheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(completeCheckoutPage.orderID));

        order1Id = completeCheckoutPage.orderID.getText();

        Assert.assertEquals(completeCheckoutPage.successMessage.getText(), "Thanks "+ firstName +" for your order!");
        Assert.assertEquals(completeCheckoutPage.orderStatus.getText(), "Paid");

        Assert.assertEquals(completeCheckoutPage.emailAddress.getText(), email);
        Assert.assertTrue(completeCheckoutPage.shippingDetails.getText().contains(lastName));
        Assert.assertTrue(completeCheckoutPage.shippingDetails.getText().contains(address));
        Assert.assertEquals(completeCheckoutPage.phoneNumber.getText(), phoneNumber);
    }

    @Test(dependsOnMethods = {"order1CompletedCheckout"})
    public void openProduct2Page() {

        header = new HeaderComponent(driver);

        driver.navigate().to("https://demo.spreecommerce.org/");
        waitFor().until(ExpectedConditions.urlToBe("https://demo.spreecommerce.org/"));

        Assert.assertEquals(Objects.requireNonNull(driver.getCurrentUrl()), "https://demo.spreecommerce.org/");

        header = new HeaderComponent(driver);

        header.openMenFashion();

        homePage = new HomePage(driver);
        waitFor().until(ExpectedConditions.urlContains("categories/fashion/men"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("categories/fashion/men"));

        WebElement product = homePage.clickRandomProduct();

        waitFor().until(ExpectedConditions.elementToBeClickable(product));

        product.click();
    }

    @Test(dependsOnMethods = {"openProduct2Page"})
    public void addProduct2ToCart() {

        productPage = new ProductPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(productPage.chooseSizeButton));
        productPage.chooseSize(product2Size);

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToCartButton));
        Assert.assertTrue(productPage.addToCartButton.isDisplayed());

        product2Title = productPage.productTitle.getText();
        product2Price = productPage.productPrice.getText();
        product2Color = productPage.productColor.getText();
        product2Quantity = productPage.getQuantity();

        waitFor().until(ExpectedConditions.visibilityOf(productPage.addToCartButton));
        waitFor().until(ExpectedConditions.elementToBeClickable(productPage.addToCartButton));
        productPage.addToCart();
    }

    @Test(dependsOnMethods = {"addProduct2ToCart"})
    public void checkProduct2AddedToCart() {

        cartPage = new CartPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(cartPage.cartProductsContainer));
        waitFor().until(ExpectedConditions.visibilityOf(cartPage.cartFirstProductTitle));


        Assert.assertEquals(cartPage.cartFirstProductTitle.getText().toUpperCase() ,product2Title);
        Assert.assertEquals(cartPage.cartFirstProductPrice.getText() ,product2Price);
        Assert.assertEquals(cartPage.getProduct1Quantity() ,quantity);

        cartPage.openCheckoutPage();
        waitFor().until(ExpectedConditions.urlContains("/address"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/address"));
    }

    @Test(dependsOnMethods = {"checkProduct2AddedToCart"})
    public void useAddedAddress() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.address));

        Assert.assertTrue(checkoutPage.address.getText().contains(address));

        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.addedAddress));
        checkoutPage.chooseAddedAddress();

        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.saveAndContinueButton));

        checkoutPage.clickSaveAndContinueButton();

        waitFor().until(ExpectedConditions.urlContains("/delivery"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/delivery"));
    }

    @Test(dependsOnMethods = {"useAddedAddress"})
    public void order2SelectDeliveryMethod() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliverySaveAndContinueButton));
        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.deliverySaveAndContinueButton));

        checkoutPage.selectStandard();

        waitFor().until(ExpectedConditions.urlContains("/payment"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/payment"));

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliveryMethod));

        Assert.assertTrue(checkoutPage.deliveryMethod.getText().contains("Standard"));
    }

    @Test(dependsOnMethods = {"order2SelectDeliveryMethod"})
    public void order2FillPaymentDetails() {

        checkoutPage = new CheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.deliveryMethod));

        Assert.assertTrue(checkoutPage.deliveryMethod.getText().contains("Standard"));

        waitFor().until(ExpectedConditions.visibilityOf(checkoutPage.addedPayment));
        waitFor().until(ExpectedConditions.elementToBeClickable(checkoutPage.addedPayment));

        checkoutPage.clickPayNow();

        waitFor().until(ExpectedConditions.urlContains("/complete"));

        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/complete"));
    }

    @Test(dependsOnMethods = {"order2FillPaymentDetails"})
    public void order2CompletedCheckout() {

        completeCheckoutPage = new CompleteCheckoutPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(completeCheckoutPage.orderID));

        order2Id = completeCheckoutPage.orderID.getText();

        Assert.assertEquals(completeCheckoutPage.successMessage.getText(), "Thanks "+ firstName +" for your order!");
        Assert.assertEquals(completeCheckoutPage.orderStatus.getText(), "Paid");

        Assert.assertEquals(completeCheckoutPage.emailAddress.getText(), email);
        Assert.assertTrue(completeCheckoutPage.shippingDetails.getText().contains(lastName));
        Assert.assertTrue(completeCheckoutPage.shippingDetails.getText().contains(address));
        Assert.assertEquals(completeCheckoutPage.phoneNumber.getText(), phoneNumber);
    }



    @Test(dependsOnMethods = {"order2CompletedCheckout"})
    public void checkOrdersHistory() {

        header = new HeaderComponent(driver);

        driver.navigate().to("https://demo.spreecommerce.org/");
        waitFor().until(ExpectedConditions.urlToBe("https://demo.spreecommerce.org/"));
        Assert.assertEquals(Objects.requireNonNull(driver.getCurrentUrl()), "https://demo.spreecommerce.org/");

        header.openAccount();
        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        WebElement order1Link = driver.findElement(By.linkText("#" + order1Id));

        order1Link.click();

        waitFor().until(ExpectedConditions.urlContains(order1Id));

        Assert.assertTrue(driver.getCurrentUrl().contains(order1Id));
    }

    @Test(dependsOnMethods = {"checkOrdersHistory"})
    public void checkOrder1Details() {

        String quantityText = String.valueOf(quantity);

        orderPage = new OrderPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(orderPage.productTitle));

        Assert.assertEquals(homeProductTitle.toUpperCase(), orderPage.productTitle.getText());
        Assert.assertEquals(productColor, orderPage.productColor.getText());
        Assert.assertTrue(orderPage.productQuantity.getText().contains(quantityText));
        Assert.assertEquals(homeProductPrice, orderPage.productPrice.getText());
        Assert.assertTrue(orderPage.productSize.getText().contains(productSize));
    }


    @Test(dependsOnMethods = {"checkOrder1Details"})
    public void checkOrder2Details() {

        driver.navigate().back();

        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        WebElement order2Link = driver.findElement(By.linkText("#" + order2Id));

        order2Link.click();

        waitFor().until(ExpectedConditions.urlContains(order2Id));

        Assert.assertTrue(driver.getCurrentUrl().contains(order2Id));

        String quantityText = String.valueOf(quantity);

        orderPage = new OrderPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(orderPage.productTitle));

        Assert.assertEquals(product2Title.toUpperCase(), orderPage.productTitle.getText());
        Assert.assertTrue(orderPage.productQuantity.getText().contains(quantityText));
        Assert.assertEquals(product2Price, orderPage.productPrice.getText());
        Assert.assertTrue(orderPage.productSize.getText().contains(product2Size));
    }
}
