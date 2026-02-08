package com.framework.tests;

import com.framework.api.APIClient;
import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.swaglabs.LoginPage;
import com.framework.utils.ConfigReader;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;


public class LoginTest extends BaseTest {

    @Test(description = "Verify that the user can login successfully with valid credentials")
    public void testSuccessfulLogin() {
        // Navigate to the URL from our config file
        page.navigate(ConfigReader.getProperty("web_url"));

        // Initialize Page Object
        LoginPage loginPage = new LoginPage(page, TestListener.getExtentTest());
        // Perform Action
        loginPage.login("standard_user", "secret_sauce");

        // Assertion: Verify we reached the inventory page
        Assert.assertTrue(page.url().contains("inventory.html"), "Login failed or URL mismatch!");
    }

}