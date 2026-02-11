package com.framework.pages.demoqa;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ProgressBarPage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator startStopButton;
    private final Locator resetButton;
    private final Locator progressBar;
    private final Locator successProgressBar;

    public ProgressBarPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        this.startStopButton = page.locator("#startStopButton");
        this.resetButton = page.locator("#resetButton");
        this.progressBar = page.locator(".progress-bar");
        // Specific locator for when it turns green/success
        this.successProgressBar = page.locator(".progress-bar.bg-success");
    }

    public void clickOnStartStopButton() {
        action.safeClickWithScreenshot(startStopButton, "Start/Stop Button");
    }

    public void clickOnResetButton() {
        action.safeClickWithScreenshot(resetButton, "Reset Button");
    }

    public Locator getProgressBar() {
        return progressBar;
    }

    public Locator getSuccessProgressBar() {
        return successProgressBar;
    }

    public String getStartStopButtonText() {
        return startStopButton.innerText();
    }
}
