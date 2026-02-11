package com.framework.pages.demoqa;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ModalPage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator smallModalButton;
    private final Locator largeModalButton;
    private final Locator modalContainer;
    private final Locator modalTitle;
    private final Locator closeSmallModalButton;
    private final Locator closeLargeModalButton;

    public ModalPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        this.smallModalButton = page.locator("#showSmallModal");
        this.largeModalButton = page.locator("#showLargeModal");
        this.modalContainer = page.locator("div[role='dialog']");
        this.modalTitle = page.locator(".modal-title.h4");
        this.closeSmallModalButton = page.locator("#closeSmallModal");
        this.closeLargeModalButton = page.locator("#closeLargeModal");
    }

    public void clickOnSmallModal() {
        action.safeClickWithScreenshot(smallModalButton, "Small Modal Button");
    }

    public void clickOnLargeModal() {
        action.safeClickWithScreenshot(largeModalButton, "Large Modal Button");
    }

    public void closeSmallModal() {
        action.safeClickWithScreenshot(closeSmallModalButton, "Close Small Modal Button");
    }

    public void closeLargeModal() {
        action.safeClickWithScreenshot(closeLargeModalButton, "Close Large Modal Button");
    }

    public String getModalTitle() {
        return action.safeGetText(modalTitle, "Modal Title");
    }

    public boolean isModalVisible() {
        return modalContainer.isVisible();
    }
}
