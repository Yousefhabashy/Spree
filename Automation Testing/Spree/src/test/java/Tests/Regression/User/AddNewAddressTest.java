package Tests.Regression.User;

import Base.TestBase;
import Components.HeaderComponent;
import Data.TestData;
import Pages.AccountPage;
import Pages.SignupPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

public class AddNewAddressTest extends TestBase {

    SignupPage signupPage;
    HeaderComponent header;
    AccountPage accountPage;

    String email = TestData.generateEmail();
    String password = TestData.generatePassword();

    String countryName = "Egypt";
    String firstName = TestData.generateFirstName();
    String lastName = TestData.generateLastName();
    String address = TestData.generateAddress();
    String apartment = TestData.generateApartment();
    String city = TestData.generateCity();
//    String state = TestData.generateState();
    String postalCode = TestData.generatePostalCode();
    String phoneNumber = TestData.generatePhoneNumber();


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
    public void addAddress() {

        header = new HeaderComponent(driver);
        header.openAccount();

        waitFor().until(ExpectedConditions.urlContains("account/orders"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/orders"));

        accountPage = new AccountPage(driver);
        accountPage.openAddresses();

        waitFor().until(ExpectedConditions.urlContains("account/addresses"));
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("account/addresses"));

        accountPage.addAddress();

        waitFor().until(ExpectedConditions.visibilityOf(accountPage.selectCountry));

        accountPage.addNewAddress(countryName, firstName, lastName,
                address, apartment, city, postalCode, phoneNumber);

        waitFor().until(ExpectedConditions.visibilityOf(header.successMessage));
        Assert.assertEquals(header.successMessage.getText(), "ADDRESS HAS BEEN SUCCESSFULLY CREATED.");
    }
}
