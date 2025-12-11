package Tests.Regression.Product;

import Base.TestBase;
import Components.HeaderComponent;
import Pages.HomePage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ProductSortingTest extends TestBase {

    HeaderComponent header;
    HomePage homePage;

    @Test(priority = 1)
    public void openAlProducts() {

        header = new HeaderComponent(driver);

        header.openShopAll();

        waitFor().until(ExpectedConditions.urlContains("products"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("products"));
    }

    @Test(dependsOnMethods = {"openAlProducts"})
    public void sortProductsByPriceLowToHigh() {

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortList));

        homePage.openSortList();

        waitFor().until(ExpectedConditions.visibilityOf(homePage.sortByPriceLowToHigh));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortByPriceLowToHigh));

        homePage.sortByPriceLowToHigh();

        waitFor().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(homePage.sortMethod, "Relevance")));

        Assert.assertEquals(homePage.sortMethod.getText(), "Price (low-high)");

        boolean isSorted = homePage.isSortedByPriceLowToHigh();

        Assert.assertTrue(isSorted);
    }

    @Test(dependsOnMethods = {"sortProductsByPriceLowToHigh"})
    public void sortProductsByPriceHighToLow() {

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortList));

        homePage.openSortList();

        waitFor().until(ExpectedConditions.visibilityOf(homePage.sortByPriceHighToLow));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortByPriceHighToLow));

        homePage.sortByPriceHighToLow();

        waitFor().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(homePage.sortMethod, "Price (low-high)")));

        Assert.assertEquals(homePage.sortMethod.getText(), "Price (high-low)");

        boolean isSorted = homePage.isSortedByPriceHighToLow();

        Assert.assertTrue(isSorted);
    }


    @Test(dependsOnMethods = {"sortProductsByPriceHighToLow"})
    public void sortProductsAToZ() {

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortList));

        homePage.openSortList();

        waitFor().until(ExpectedConditions.visibilityOf(homePage.sortByAToZ));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortByAToZ));

        homePage.sortByAToZ();

        waitFor().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(homePage.sortMethod, "Price (high-low)")));

        Assert.assertEquals(homePage.sortMethod.getText(), "Alphabetically, A-Z");

        boolean isSorted  = homePage.isSortedAToZ();

        Assert.assertTrue(isSorted);
    }

    @Test(dependsOnMethods = {"sortProductsAToZ"})
    public void sortProductsZToA() {

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortList));

        homePage.openSortList();

        waitFor().until(ExpectedConditions.visibilityOf(homePage.sortByZToA));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortByZToA));

        homePage.sortByZToA();

        waitFor().until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(homePage.sortMethod, "Alphabetically, A-Z")));

        Assert.assertEquals(homePage.sortMethod.getText(), "Alphabetically, Z-A");

        boolean isReversed  = homePage.isSortedZToA();

        Assert.assertTrue(isReversed);
    }

    @Test(dependsOnMethods = {"sortProductsZToA"})
    public void sotByBestSelling() {

        homePage = new HomePage(driver);

        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortList));

        homePage.openSortList();

        waitFor().until(ExpectedConditions.visibilityOf(homePage.sortByBestSelling));
        waitFor().until(ExpectedConditions.elementToBeClickable(homePage.sortByBestSelling));

        homePage.sortByBestSelling();
    }
}



