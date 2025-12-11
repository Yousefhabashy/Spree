package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.AccountPage;
import Pages.SignInPage;
import Pages.SignupPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class ChangePasswordTest extends TestBase {

    SignupPage signupPage;
    SignInPage signInPage;
    HeaderComponent header;
    AccountPage accountPage;

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
    }

    @Test(dependsOnMethods = {"signUpUser"})
    public void changePassword() {

        header = new HeaderComponent(driver);

        header.openAccount();

        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        accountPage = new AccountPage(driver);
        accountPage.openPersonalDetails();

        waitFor().until(ExpectedConditions.urlContains("account/profile/edit"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/profile/edit"));

        String newPassword = TestData.generatePassword();

        accountPage.openChangePassword();

        waitFor().until(ExpectedConditions.urlContains("user/edit"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("user/edit"));

        accountPage.changePassword(password, newPassword);

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));
        Assert.assertEquals(header.successMessage.getText(), "YOU UPDATED YOUR ACCOUNT SUCCESSFULLY.");

        password = newPassword;
    }

    @Test(dependsOnMethods = {"changePassword"})
    public void logoutUser() {

        header = new HeaderComponent(driver);
        header.openAccount();

        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        accountPage = new AccountPage(driver);
        accountPage.logoutUser();

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));
        Assert.assertEquals(header.successMessage.getText(), "SIGNED OUT SUCCESSFULLY.");
    }

    @Test(dependsOnMethods = {"logoutUser"})
    public void loginWithNewPassword() {

        signInPage = new SignInPage(driver);

        driver.navigate().to("https://demo.spreecommerce.org/user/sign_in");

        signInPage.loginUser(email, password, false);

        header = new HeaderComponent(driver);
        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));

        waitFor().until(ExpectedConditions.urlContains("https://demo.spreecommerce.org"));

        Assert.assertEquals(header.successMessage.getText(), "SIGNED IN SUCCESSFULLY.");
    }
}
