package Components;

import Base.PagesBase;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class HeaderComponent extends PagesBase {

    public HeaderComponent(WebDriver driver) {

        super(driver);
        PagesBase.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"flashes\"]/div/div/div/p")
    public WebElement successMessage;

    @FindBy(id = "open-search")
    public WebElement openSearchButton;

    public void openSearch() {

        clickElementJS(openSearchButton);
    }

    @FindBy(id = "q")
    public WebElement searchBar;

    @FindBy(css = "button.text-sm.font-semibold.absolute.top-2.right-2.uppercase.h-6.tracking-widest")
    WebElement clearSearch;

    public void searchProduct(String productName) {

        setElementText(searchBar, productName);
        searchBar.sendKeys(Keys.ENTER);
    }

    public void clearSearch() {

        clearSearch.click();
    }


    @FindBy(id = "block-37886")
    WebElement shopAll;

    public void openShopAll() {

        shopAll.click();
    }

    @FindBy(id = "block-37887")
    WebElement fashion;

    public void openFashion() {

        fashion.click();
    }

    @FindBy(xpath = "//*[@id=\"block-37887\"]/div/div/div/div[1]/a")
    WebElement womanFashion;

    @FindBy(xpath = "//*[@id=\"block-37887\"]/div/div/div/div[2]/a")
    WebElement menFashion;

    public void openWomanFashion() {

        Actions  actions = new Actions(driver);
        actions.moveToElement(fashion).moveToElement(womanFashion).click().perform();
    }

    public void openMenFashion() {

        Actions  actions = new Actions(driver);
        actions.moveToElement(fashion).moveToElement(menFashion).click().perform();
    }


    @FindBy(id = "block-37888")
    WebElement wellness;

    public void openWellness() {

        wellness.click();
    }

    @FindBy(id = "block-37889")
    WebElement newArrivals;

    public void openNewArrivals() {

        newArrivals.click();
    }

    @FindBy(id = "block-37890")
    WebElement sale;

    public void openSale() {

        sale.click();
    }

    @FindBy(xpath = "//*[@id=\"section-21057\"]/header/nav/div/div/div[3]/div[1]/button")
    WebElement currencyAndLanguageContainer;

    public void openCurrencyAndLanguageContainer() {

        currencyAndLanguageContainer.click();
    }

    @FindBy(id = "switch_to_currency")
    WebElement selectCurrency;

    public void selectDollar() {

        Select select = new Select(selectCurrency);
        select.selectByVisibleText("Euro (EUR)");
    }

    public void selectEuro() {

        Select select = new Select(selectCurrency);
        select.selectByVisibleText("United States Dollar (USD)");
    }

    @FindBy(id = "switch_to_locale")
    WebElement selectLanguage;

    public void selectEnglish() {

        Select select = new Select(selectLanguage);
        select.selectByVisibleText("English (US)");
    }

    public void selectDeutsch() {

        Select select = new Select(selectLanguage);
        select.selectByVisibleText("Deutsch (DE)");
    }

    @FindBy(css = "input.btn-primary")
    WebElement saveButton;

    public void save() {

        saveButton.click();
    }

    @FindBy(xpath = "//*[@id=\"section-21057\"]/header/nav/div/div/div[3]/div[2]/a")
    WebElement myAccount;

    public void openAccount() {

        myAccount.click();
    }

    @FindBy(id = "cart-pane-link")
    public WebElement cartPage;

    public void openCart() {

        driver.navigate().to("https://demo.spreecommerce.org/cart");
    }

    @FindBy(id = "site-logo")
    WebElement siteLogo;

    public void backHome() {

        clickElementJS(siteLogo);
    }
}
