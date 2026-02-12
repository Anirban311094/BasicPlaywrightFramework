package com.framework.pages.demoqa.bookstore;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.Random;

public class BookStorePage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator sidebarItems;
    private final Locator bookLinks;
    private final Locator bookTitleValue;
    private final Locator actionButtons;

    public BookStorePage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        this.sidebarItems = page.locator(".text");
        this.bookLinks = page.locator(".mr-2");
        this.bookTitleValue = page.locator("#title-wrapper").locator("#userName-value");
        this.actionButtons = page.locator("#addNewRecordButton");
    }

    // Sidebar Logic
    public void navigateTo(String itemName) {
        Locator target = sidebarItems.filter(new Locator.FilterOptions().setHasText(itemName)).first();
        action.safeClickWithScreenshot(target, "Sidebar Item: " + itemName);
    }

    // Book List Logic
    public void clickOnAnyBook() {
        int count = bookLinks.count();
        if (count > 0) {
            int index = new Random().nextInt(count);
            action.safeClickWithScreenshot(bookLinks.nth(index), "Random Book Selection");
        }
    }

    // Individual Book Details Logic
    public String getSelectedBookTitle() {
        return action.safeGetText(bookTitleValue, "Selected Book Title");
    }

    public void clickOnAddToCollection() {
        // Playwright handles scrolling automatically for buttons outside the viewport
        Locator btn = actionButtons.filter(new Locator.FilterOptions().setHasText("Add To Your Collection")).first();
        action.safeClickWithScreenshot(btn, "Add To Collection Button");
    }

    public void clickOnBackToStore() {
        Locator btn = actionButtons.filter(new Locator.FilterOptions().setHasText("Back To Book Store")).first();
        action.safeClickWithScreenshot(btn, "Back To Book Store Button");
    }
}
