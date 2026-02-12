package com.framework.pages.demoqa.bookstore;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;
    private final ActionComponent action;

    // Locators using CSS selectors
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator addNewUserButton;
    private final Locator errorMessage;
    private final Locator invalidFields;

    public LoginPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        // Initializing locators
        this.usernameField = page.locator("#userName");
        this.passwordField = page.locator("#password");
        this.loginButton = page.locator("#login");
        this.addNewUserButton = page.locator("#newUser");
        this.errorMessage = page.locator("#name");
        // Matches fields with red borders when invalid
        this.invalidFields = page.locator(".mr-sm-2.is-invalid.form-control");
    }

    public void inputUsername(String username) {
        // safeFill handles clear() and sendKeys() automatically
        action.safeFillWithScreenshot(usernameField, username, "Username Field");
    }

    public void inputPassword(String password) {
        action.safeFillWithScreenshot(passwordField, password, "Password Field");
    }

    public void clickOnLoginButton() {
        action.safeClickWithScreenshot(loginButton, "Login Button");
    }

    public void clickOnAddNewUserButton() {
        action.safeClickWithScreenshot(addNewUserButton, "Add New User Button");
    }

    public String getErrorMessage() {
        return action.safeGetText(errorMessage, "Login Error Message");
    }

    public int getInvalidFieldsCount() {
        // Returns the number of fields currently highlighted in red
        return invalidFields.count();
    }

    public Locator getErrorLocator() {
        return errorMessage;
    }
}
