package Pages;

import Base.PagesBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CompleteCheckoutPage extends PagesBase {

    public CompleteCheckoutPage(WebDriver driver) {

        super(driver);
    }

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div/p/strong")
    public WebElement orderID;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div/h4")
    public WebElement successMessage;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div/div[2]/div[2]/span[2]")
    public WebElement orderStatus;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div/div[3]/p")
    public WebElement emailAddress;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div/div[3]/div/div[1]/p[1]")
    public WebElement shippingDetails;

    @FindBy(xpath = "/html/body/div[2]/div/div[1]/div/div/div/div[3]/div/div[1]/p[2]")
    public WebElement phoneNumber;


    @FindBy(css = "p.font-bold.word-break")
    public WebElement productName;

    @FindBy(css = "div.font-semibold.text-sm.text-right.shrink-0")
    public WebElement productPrice;

    @FindBy(css = "p.text-xs")
    public WebElement productColorAndSize;

    private String[] splitProductColorAndSize() {

        String text = productColorAndSize.getText();

        return text.split(",");
    }

    public String getProductColor() {

        String color = splitProductColorAndSize()[0];
        color = color.replace("Color:", "").trim();
        return color;
    }

    public String getProductSize() {

        String size = splitProductColorAndSize()[1];
        size = size.replace("Size:", "").trim();
        return size;
    }
}
