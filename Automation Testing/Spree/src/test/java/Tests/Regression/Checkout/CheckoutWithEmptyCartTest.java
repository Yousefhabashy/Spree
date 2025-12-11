package Tests.Regression.Checkout;

import Base.TestBase;
import Components.HeaderComponent;
import Pages.CartPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class CheckoutWithEmptyCartTest extends TestBase {

    HeaderComponent headerComponent;
    CartPage cartPage;

    @Test(priority = 1)
    public void checkEmptyCart() {

        headerComponent = new HeaderComponent(driver);

        headerComponent.openCart();

        waitFor().until(ExpectedConditions.urlContains("/cart"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/cart"));

        cartPage  = new CartPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(cartPage.emptyCartText));

        Assert.assertEquals(cartPage.emptyCartText.getText(), "YOUR CART IS EMPTY.");
    }

    @Test(dependsOnMethods = {"checkEmptyCart"})
    public void tryCheckoutWithEmptyCart() {

        cartPage = new CartPage(driver);

        boolean invisible = waitFor().until(ExpectedConditions.invisibilityOf(cartPage.checkoutButton));
        if (!invisible) {
            Assert.fail("Checkout button is visible but it should NOT be!");
        }
    }
}
