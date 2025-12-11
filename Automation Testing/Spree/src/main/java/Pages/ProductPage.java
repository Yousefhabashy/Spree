package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Objects;

public class ProductPage extends PagesBase {

    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        super(driver);
        PagesBase.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @FindBy(css = "h1.text-2xl.uppercase.tracking-tight.font-medium")
    public WebElement productTitle;

    @FindBy(id = "block-40849")
    public WebElement productPrice;

    @FindBy(css = "span.text-sm.leading-4.uppercase.tracking-widest")
    public  WebElement productColor;

    @FindBy(id = "quantity")
    WebElement quantity;

    public int getQuantity() {

        return Integer.parseInt(Objects.requireNonNull(quantity.getAttribute("value")));
    }

    @FindBy(css = "button.increase-quantity")
    WebElement increaseQuantity;

    public void increaseQuantity() {

        increaseQuantity.click();
    }

    @FindBy(css = "button.decrease-quantity")
    WebElement decreaseQuantity;

    public void decreaseQuantity() {

        decreaseQuantity.click();
    }

    @FindBy(id = "option-23-value")
    public WebElement chooseSizeButton;

    @FindBy(xpath = "//*[@id=\"product-variant-picker\"]/fieldset[2]/div/div[2]/div/label[1]")
    WebElement smallSize;

    @FindBy(xpath = "//*[@id=\"product-variant-picker\"]/fieldset[2]/div/div[2]/div/label[2]")
    WebElement mediumSize;

    @FindBy(xpath = "//*[@id=\"product-variant-picker\"]/fieldset[2]/div/div[2]/div/label[3]")
    WebElement largeSize;


    public void chooseSize(String size) {

        chooseSizeButton.click();

        if(size.equalsIgnoreCase("s")) {
            smallSize.click();
        } else if (size.equalsIgnoreCase("m")) {
            mediumSize.click();
        } else if (size.equalsIgnoreCase("l")) {
            largeSize.click();
        }else System.out.println("SIZE IS NOT AVAILABLE");
    }

    @FindBy(xpath = "//*[@id=\"block-40852\"]/div/div[3]/div/button[1]")
    public WebElement addToWishlist;

    public void addToWishlist() {

        clickElementJS(addToWishlist);
    }
    @FindBy(css = "button.btn-primary.btn-icon.w-full.h-12.add-to-cart-button")
    public WebElement addToCartButton;

    public void addToCart() {

        clickElementJS(addToCartButton);
    }
}
