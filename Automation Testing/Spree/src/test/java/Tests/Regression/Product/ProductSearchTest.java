package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponent;
import Pages.HomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Objects;

public class ProductSearchTest extends TestBase {

    HeaderComponent header;
    HomePage homePage;

    String productName = "Black Gym Pants";

    @Test(priority = 1)
    public void searchProduct() {

        header = new HeaderComponent(driver);

        waitFor().until(ExpectedConditions.visibilityOf(header.openSearchButton));

        Assert.assertTrue(header.openSearchButton.isDisplayed());

        waitFor().until(ExpectedConditions.elementToBeClickable(header.openSearchButton));

        header.openSearch();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(header.searchBar));

        header.searchProduct(productName);

        waitFor().until(ExpectedConditions.urlContains("Black+Gym+Pants"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("Black+Gym+Pants"));
    }

    @Test(dependsOnMethods = {"searchProduct"})
    public void findProductAtSearchProducts() {

        homePage = new HomePage(driver);

        boolean isFound =  homePage.isProductInSearchResult(productName);

        Assert.assertTrue(isFound);
        waitFor().until(ExpectedConditions.urlContains("products/black-gym-pants"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("products/black-gym-pants"));
    }
}
