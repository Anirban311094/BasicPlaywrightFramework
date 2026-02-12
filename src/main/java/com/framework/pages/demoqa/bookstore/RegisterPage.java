package com.framework.pages.demoqa.bookstore;


import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class RegisterPage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator firstnameField;
    private final Locator lastnameField;
    private final Locator usernameField;
    private final Locator passwordField;
    private final Locator registerButton;
    private final Locator recaptchaError;

    public RegisterPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        this.firstnameField = page.locator("#firstname");
        this.lastnameField = page.locator("#lastname");
        this.usernameField = page.locator("#userName");
        this.passwordField = page.locator("#password");
        this.registerButton = page.locator("#register");
        this.recaptchaError = page.locator("#name");
    }

    public void registerUser(String fName, String lName, String uName, String pwd) {
        action.safeFillWithScreenshot(firstnameField, fName, "First Name");
        action.safeFillWithScreenshot(lastnameField, lName, "Last Name");
        action.safeFillWithScreenshot(usernameField, uName, "Username");
        action.safeFillWithScreenshot(passwordField, pwd, "Password");
        action.safeClickWithScreenshot(registerButton, "Register Button");
    }

    public String getRecaptchaErrorMessage() {
        return action.safeGetText(recaptchaError, "reCaptcha Error Message");
    }

    public Locator getRecaptchaErrorLocator() {
        return recaptchaError;
    }
}