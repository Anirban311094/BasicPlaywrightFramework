package com.framework.pages.demoqa;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AlertsPage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator alertButton;
    private final Locator timerAlertButton;
    private final Locator confirmButton;
    private final Locator promptButton;
    private final Locator successMessage;

    public AlertsPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        this.alertButton = page.locator("#alertButton");
        this.timerAlertButton = page.locator("#timerAlertButton");
        this.confirmButton = page.locator("#confirmButton");
        this.promptButton = page.locator("#promtButton");
        this.successMessage = page.locator(".text-success");
    }

    public void clickOnAlertButton() {
        action.safeClickWithScreenshot(alertButton, "Alert Button");
    }

    public void clickOnTimerAlertButton() {
        action.safeClickWithScreenshot(timerAlertButton, "Timer Alert Button");
    }

    public void clickOnConfirmButton() {
        action.safeClickWithScreenshot(confirmButton, "Confirm Button");
    }

    public void clickOnPromptButton() {
        action.safeClickWithScreenshot(promptButton, "Prompt Button");
    }

    public String getSuccessMessageText() {
        return action.safeGetText(successMessage, "Success Message");
    }
}
