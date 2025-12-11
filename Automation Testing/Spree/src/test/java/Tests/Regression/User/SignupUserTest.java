package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.SignupPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Objects;

public class SignupUserTest extends TestBase {

    SignupPage signupPage;
    HeaderComponent header;
    String email = TestData.generateEmail();
    String password = TestData.generatePassword();


    @Test(priority = 1)
    public void signUpUser(){

        signupPage = new SignupPage(driver);

        driver.navigate().to("https://demo.spreecommerce.org/user/sign_up");

        waitFor().until(ExpectedConditions.urlContains("user/sign_up"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("user/sign_up"));
        signupPage.signUpUser(email, password);

        waitFor().until(ExpectedConditions.urlContains("https://demo.spreecommerce.org"));

        header = new HeaderComponent(driver);
        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));

        Assert.assertEquals(header.successMessage.getText(), "WELCOME! YOU HAVE SIGNED UP SUCCESSFULLY.");

        System.out.println(email);
        System.out.println(password);
    }

}
