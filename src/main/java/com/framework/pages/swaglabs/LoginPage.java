package com.framework.pages.swaglabs;

import com.framework.components.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.framework.utils.ReportUtils;
import com.aventstack.extentreports.ExtentTest;


public class LoginPage {
    private final Page page;
    private static final Logger log = LogManager.getLogger(LoginPage.class);
    private ActionComponent action;
    private final ExtentTest extentTest;

    // 1. Define Locators using Playwright's 'Locator' instead of raw Strings
    // This allows for auto-waiting and retry-ability
    private final Locator usernameInput;
    private final Locator passwordInput;
    private final Locator loginButton;


    // 2. Constructor: Receives the 'page' object from the test
    public LoginPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);
        this.extentTest = extentTest;

        this.usernameInput = page.locator("#user-name");
        this.passwordInput = page.locator("#password");
        this.loginButton = page.locator("#login-button");
    }

    // 3. Page Actions: High-level methods for the test to call
    public void login(String user, String pass) {
        usernameInput.fill(user);
        passwordInput.fill(pass);
        ReportUtils.logInfo(extentTest, "Entering username: " + user);
        log.info("Entered credentials for user: " + user);
        ReportUtils.logStepWithScreenshot(page, extentTest, "Username entered");
        action.safeClickWithScreenshot(loginButton, "Clicked the login button");
        log.info("Clicked the login button");
    }

    public String getTitle() {
        return page.title();
    }
}