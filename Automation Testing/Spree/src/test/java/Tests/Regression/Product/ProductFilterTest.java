package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponent;
import Pages.FilterPage;
import Pages.HomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductFilterTest extends TestBase {

    HeaderComponent header;
    HomePage homePage;
    FilterPage filterPage;

    @Test(priority = 1)
    public void openAlProducts() {

        header = new HeaderComponent(driver);

        header.openShopAll();

        waitFor().until(ExpectedConditions.urlContains("products"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("products"));
    }

    @Test(dependsOnMethods = {"openAlProducts"})
    public void selectAvailabilityInStock() {

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(homePage.filterLink));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.filterLink));

        homePage.openFilterLink();

        filterPage = new FilterPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.availability));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.availability));

        filterPage.selectAvailability();


        filterPage.selectInStock();
    }

    @Test(dependsOnMethods = {"selectAvailabilityInStock"})
    public void selectPrice() {

        filterPage = new FilterPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.price));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.price));

        filterPage.selectPrice();

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.priceFromBox));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.priceFromBox));

        filterPage.setPriceFilter("50", "200");
    }

    @Test(dependsOnMethods = {"selectPrice"})
    public void selectCategory() {

        filterPage = new FilterPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.fashion));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.fashion));

        filterPage.selectFashion();
    }

    @Test(dependsOnMethods = {"selectCategory"})
    public void selectSize() {

        filterPage = new FilterPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.size));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.size));

        filterPage.selectSize();

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.mediumSize));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.mediumSize));

        filterPage.selectMediumSize();
    }

    @Test(dependsOnMethods = {"selectSize"})
    public void applyFilter() throws InterruptedException {

        filterPage = new FilterPage(driver);

        waitFor().until(ExpectedConditions.visibilityOf(filterPage.applyFilter));
        waitFor().until(ExpectedConditions.elementToBeClickable(filterPage.applyFilter));

        filterPage.applyFilter();

        Thread.sleep(2500);
    }
}
