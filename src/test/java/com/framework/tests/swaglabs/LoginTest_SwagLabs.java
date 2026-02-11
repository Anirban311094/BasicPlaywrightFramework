package com.framework.tests.swaglabs;

import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.swaglabs.LoginPage_SwagLabs;
import com.framework.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginTest_SwagLabs extends BaseTest {

    @Test(description = "Verify that the user can login successfully with valid credentials")
    public void testSuccessfulLogin() {
        // Navigate to the URL from our config file
        page.navigate(ConfigReader.getProperty("web_url_swaglabs"));

        // Initialize Page Object
        LoginPage_SwagLabs loginPageSwagLabs = new LoginPage_SwagLabs(page, TestListener.getExtentTest());
        // Perform Action
        loginPageSwagLabs.login("standard_user", "secret_sauce");

        // Assertion: Verify we reached the inventory page
        Assert.assertTrue(page.url().contains("inventory.html"), "Login failed or URL mismatch!");
    }

}