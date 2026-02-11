package com.framework.pages.demoqa;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.Random;

public class RadioButtonPage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator radioLabels;
    private final Locator successMessage;

    public RadioButtonPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        // custom-control-label matches the labels for 'Yes' and 'Impressive'
        this.radioLabels = page.locator(".custom-control-label");
        this.successMessage = page.locator(".text-success");
    }

    public String clickOnAnyRadioButton() {
        // Get total count of enabled radio button labels
        int count = radioLabels.count();

        // DemoQA has 'No' disabled, we want to pick from the clickable ones
        // In your Selenium code, you did size-1, here we'll pick a random index
        int randomIndex = new Random().nextInt(count - 1); // Avoiding the last one if it's 'No'

        Locator target = radioLabels.nth(randomIndex);
        String text = target.innerText();

        action.safeClickWithScreenshot(target, "Radio Button: " + text);
        return text;
    }

    public String getSuccessMessageText() {
        return action.safeGetText(successMessage, "Success Message");
    }
}
