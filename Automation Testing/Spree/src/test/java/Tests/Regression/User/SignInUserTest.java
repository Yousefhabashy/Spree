package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.SignInPage;
import Pages.SignupPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class SignInUserTest extends TestBase {


    SignInPage signInPage;
    HeaderComponent header;
    String email = "werner.yost@hotmail.com";
    String password = "Len733390";


    @Test(priority = 1)
    public void loginRegisteredUser() {

        signInPage = new SignInPage(driver);

        driver.navigate().to("https://demo.spreecommerce.org/user/sign_in");

        signInPage.loginUser(email, password, false);

        header = new HeaderComponent(driver);
        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));

        waitFor().until(ExpectedConditions.urlContains("https://demo.spreecommerce.org"));

        Assert.assertEquals(header.successMessage.getText(), "SIGNED IN SUCCESSFULLY.");

    }
}
