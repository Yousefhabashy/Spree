package Pages;

import Base.PagesBase;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class FilterPage extends PagesBase {

    public FilterPage(WebDriver driver) {

        super(driver);
        PagesBase.driver = driver;
    }


    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[1]/div[1]/a")
    public WebElement availability;

    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[1]/div[1]/div/ul/li[1]")
    WebElement availabilityInStock;


    public void selectAvailability() {

        availability.click();
    }

    public void selectInStock() {

        availabilityInStock.click();
    }

    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[1]/div[2]/a")
    public WebElement price;

    @FindBy(id = "price_from")
    public WebElement priceFromBox;

    @FindBy(id = "price_to")
    WebElement priceToBox;

    public void selectPrice() {

        price.click();
    }

    public void setPriceFilter(String priceFrom, String priceTo) {

        setElementText(priceFromBox, priceFrom);
        setElementText(priceToBox, priceTo);
    }

    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[1]/div[3]/div/ul/li[1]")
    public WebElement fashion;

    public void selectFashion() {

        fashion.click();
    }


    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[1]/div[6]/a")
    public WebElement size;

    public void selectSize() {

        size.click();
    }

    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[1]/div[6]/div/ul[1]/li[2]")
    public WebElement mediumSize;

    public void selectMediumSize() {
        Actions actions = new Actions(driver);
        actions.moveToElement(mediumSize).click().perform();
    }

    @FindBy(css = "button.btn-primary.block.text-center.flex-grow")
    public WebElement applyFilter;

    public void applyFilter() {

        applyFilter.click();
    }

    @FindBy(xpath = "//*[@id=\"slideover-filters\"]/form/nav/div[2]/a")
    public WebElement clearFilter;

    public void clearFilter() {

        clearFilter.click();
    }
}

