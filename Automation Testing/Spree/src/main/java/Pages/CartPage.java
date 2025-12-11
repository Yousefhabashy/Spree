package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Objects;

public class CartPage extends PagesBase {

    public CartPage(WebDriver driver) {

        super(driver);
    }

    @FindBy(id = "line-items")
    public WebElement cartProductsContainer;

    @FindBy(css = "p.mb-8.mt-4.uppercase.text-sm")
    public WebElement emptyCartText;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[1]/a")
    public WebElement cartFirstProductTitle;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[2]")
    public WebElement cartFirstProductPrice;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[4]/div/form/div/input")
    public WebElement cartFirstProductQuantity;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[4]/div/form/div/button[3]")
    public WebElement cartFirstProductIncreaseQuantity;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[4]/div/form/div/button[2]")
    public WebElement cartFirstProductDecreaseQuantity;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[3]/div[1]/div")
    public WebElement cartFirstProductColor;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame/li/div[3]/div[3]/div[2]/div")
    public WebElement cartFirstProductSize;

    public int getProduct1Quantity() {

        return Integer.parseInt(Objects.requireNonNull(cartFirstProductQuantity.getAttribute("value")));
    }




    @FindBy(xpath = "//*[@id=\"line_item_18473\"]/li/div[3]/div[1]/a")
    public WebElement cartSecondProductTitle;

    @FindBy(xpath = "//*[@id=\"line_item_18473\"]/li/div[3]/div[2]")
    public WebElement cartSecondProductPrice;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame[2]/li/div[3]/div[4]/div/form/div/input")
    public WebElement cartSecondProductQuantity;

    @FindBy(xpath = "//*[@id=\"edit_line_item_18473\"]/div/button[3]")
    public WebElement cartSecondProductIncreaseQuantity;

    @FindBy(xpath = "//*[@id=\"edit_line_item_18473\"]/div/button[2]")
    public WebElement cartSecondProductDecreaseQuantity;

    @FindBy(xpath = "//*[@id=\"line_item_18473\"]/li/div[3]/div[3]/div[1]/div/div[2]")
    public WebElement cartSecondProductColor;

    @FindBy(xpath = "//*[@id=\"line_item_18473\"]/li/div[3]/div[3]/div[2]/div")
    public WebElement cartSecondProductSize;



    @FindBy(xpath = "//*[@id=\"line_item_18474\"]/li/div[3]/div[1]/a")
    public WebElement cartThirdProductTitle;

    @FindBy(xpath = "//*[@id=\"line_item_18474\"]/li/div[3]/div[2]")
    public WebElement cartThirdProductPrice;

    @FindBy(xpath = "/html/body/div[2]/header/div[2]/div[2]/div[2]/turbo-frame/div/div[1]/ul/turbo-frame[3]/li/div[3]/div[4]/div/form/div/input")
    public WebElement cartThirdProductQuantity;

    @FindBy(xpath = "//*[@id=\"edit_line_item_18474\"]/div/button[3]")
    public WebElement cartThirdProductIncreaseQuantity;

    @FindBy(xpath = "//*[@id=\"edit_line_item_18474\"]/div/button[2]")
    public WebElement cartThirdProductDecreaseQuantity;


    @FindBy(xpath = "//*[@id=\"line_item_18474\"]/li/div[3]/div[3]/div[1]/div/div[2]")
    public WebElement cartThirdProductColor;

    @FindBy(xpath = "//*[@id=\"line_item_18474\"]/li/div[3]/div[3]/div[2]/div")
    public WebElement cartThirdProductSize;


    @FindBy(css = "span.shopping-cart-total-amount")
    public WebElement totalPrice;


    @FindBy(xpath = "//*[@id=\"cart_summary\"]/div/div[2]/div[2]/a")
    public WebElement checkoutButton;

    public void openCheckoutPage() {

        checkoutButton.click();
    }

    @FindBy(id = "gpay-button-online-api-id")
    WebElement checkoutWithGooglePay;

    public void checkoutWithGooglePay() {

        checkoutWithGooglePay.click();
    }

    @FindBy(css = "button.LinkButton.LinkButton--showFocusIndicator")
    WebElement checkoutWithLink;

    public void checkoutWithLink() {

        checkoutWithLink.click();
    }

}