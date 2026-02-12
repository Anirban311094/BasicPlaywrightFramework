package com.framework.tests.demoqa.bookstore;

import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.demoqa.bookstore.LoginPage;
import com.framework.pages.demoqa.bookstore.ProfilePage;
import com.framework.pages.demoqa.bookstore.RegisterPage;
import com.framework.utils.ExcelReader;
import com.framework.utils.NavigationUtils;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class AccountManagementTest extends BaseTest {
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private RegisterPage registerPage;
    private ExcelReader reader;

    private final String loginUrl = "https://demoqa.com/login";
    private final String profileUrl = "https://demoqa.com/profile";
    private final String registerUrl = "https://demoqa.com/register";

    @BeforeMethod
    public void pageSetUp() throws IOException {
        // Shared Excel Reader and Page Object initialization
        reader = new ExcelReader("src/test/resources/testdata/AppData.xlsx");
        loginPage = new LoginPage(page, TestListener.getExtentTest());
        profilePage = new ProfilePage(page, TestListener.getExtentTest());
        registerPage = new RegisterPage(page, TestListener.getExtentTest());
    }

    // --- REGISTRATION TESTS ---

    @Test(description = "Verify registration fails without reCaptcha verification")
    public void userCannotRegisterWhenRecaptchaIsNotVerified() {
        NavigationUtils.navigateWithRetry(page, registerUrl, 5);

        String firstName = reader.getStringData("NewUserRegistration", 1, 0);
        String lastName = reader.getStringData("NewUserRegistration", 1, 1);
        String userName = reader.getStringData("NewUserRegistration", 1, 2);
        String password = reader.getStringData("NewUserRegistration", 1, 3);

        registerPage.registerUser(firstName, lastName, userName, password);

        // Web-first assertion for the error locator [cite: 52]
        PlaywrightAssertions.assertThat(registerPage.getRecaptchaErrorLocator()).isVisible();
        Assert.assertEquals(registerPage.getRecaptchaErrorMessage(), "Please verify reCaptcha to register!");
    }

    // --- LOGIN TESTS ---

    @Test(description = "Verify successful login with valid credentials")
    public void userCanLoginWithValidCredentials() {
        NavigationUtils.navigateWithRetry(page, loginUrl, 5);

        String username = reader.getStringData("Login", 1, 0);
        String password = reader.getStringData("Login", 1, 1);

        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickOnLoginButton();

        page.waitForURL(profileUrl);
        Assert.assertEquals(page.url(), profileUrl);
        Assert.assertEquals(profilePage.userNameValue(), username);
    }

    @Test(description = "Verify login failure with invalid username")
    public void userCannotLogInWithInvalidUsername() {
        NavigationUtils.navigateWithRetry(page, loginUrl, 5);
        String validPassword = reader.getStringData("Login", 1, 1);

        for (int i = 1; i <= reader.getLastRow("Login"); i++) {
            String invalidUsername = reader.getStringData("Login", i, 2);
            loginPage.inputUsername(invalidUsername);
            loginPage.inputPassword(validPassword);
            loginPage.clickOnLoginButton();

            PlaywrightAssertions.assertThat(loginPage.getErrorLocator()).isVisible();
            Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password!");
        }
    }

    @Test(description = "Verify error styling when both fields are empty")
    public void userCannotLoginWithEmptyFields() {
        loginPage.inputUsername("");
        loginPage.inputPassword("");
        loginPage.clickOnLoginButton();

        // Verify the number of fields with 'is-invalid' class
        Assert.assertEquals(loginPage.getInvalidFieldsCount(), 2);
        Assert.assertEquals(page.url(), loginUrl);
    }

    // --- SESSION & ACCOUNT LIFECYCLE TESTS ---

    @Test(description = "Verify user can logout successfully")
    public void userCanLogOut() {
        // Reuse login logic for setup
        userCanLoginWithValidCredentials();

        profilePage.clickOnLogout();
        Assert.assertEquals(page.url(), loginUrl);
    }

    @Test(description = "Verify user can delete their account")
    public void userCanDeleteAccount() {
        // Use row 3 for deletion tests as per original Selenium logic
        NavigationUtils.navigateWithRetry(page, loginUrl, 5);
        String username = reader.getStringData("Login", 3, 0);
        String password = reader.getStringData("Login", 3, 1);

        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickOnLoginButton();

        // Dialog handler for browser alerts [cite: 42, 56]
        page.onceDialog(dialog -> dialog.accept());

        profilePage.clickOnButton("Delete Account");
        profilePage.clickOnOk();

        page.waitForURL(loginUrl);
        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickOnLoginButton();

        PlaywrightAssertions.assertThat(loginPage.getErrorLocator()).isVisible();
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid username or password!");
    }
}