package com.framework.pages.demoqa.bookstore;

import com.aventstack.extentreports.ExtentTest;
import com.framework.Actioncomponents.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import java.util.List;
import java.util.Random;

public class ProfilePage {
    private final Page page;
    private final ActionComponent action;

    // Locators
    private final Locator loggedInUserValue;
    private final Locator booksInProfile;
    private final Locator profileButtons;
    private final Locator logoutButton;
    private final Locator okButton;
    private final Locator deleteButtons;
    private final Locator searchBoxField;

    public ProfilePage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);

        this.loggedInUserValue = page.locator("#userName-value");
        this.booksInProfile = page.locator(".mr-2");
        this.profileButtons = page.locator("#submit");
        this.logoutButton = page.locator("text=Log out");
        this.okButton = page.locator("#closeSmallModal-ok");
        this.deleteButtons = page.locator("#delete-record-undefined");
        this.searchBoxField = page.locator("#searchBox");
    }

    public String userNameValue() {
        return action.safeGetText(loggedInUserValue, "Logged-in User Value");
    }

    public void inputSearch(String str) {
        action.safeFillWithScreenshot(searchBoxField, str, "Search Box");
    }

    /**
     * Extracts all book titles currently displayed in the profile table.
     * Fixes the error in BookStoreTest.java.
     */
    public List<String> getBooksTextList() {
        // allInnerTexts() returns a List<String> of all matching elements
        return booksInProfile.allInnerTexts();
    }

    /**
     * Instead of a for-loop, Playwright allows filtering by text.
     * Use this to click "Log out", "Delete Account", or "Delete All Books".
     */
    public void clickOnButton(String buttonText) {
        Locator target = profileButtons.filter(new Locator.FilterOptions().setHasText(buttonText)).first();
        action.safeClickWithScreenshot(target, "Profile Button: " + buttonText);
    }

    /**
     * Targets the specific 'Delete All Books' button using text filtering.
     * Fixes the error in BookStoreTest.java.
     */
    public void clickOnDeleteAllBooks() {
        // Filters the #submit buttons to find the one explicitly for deletion
        Locator deleteBtn = profileButtons.filter(new Locator.FilterOptions().setHasText("Delete All Books")).first();
        action.safeClickWithScreenshot(deleteBtn, "Delete All Books Button");
    }

    public void clickOnOk() {
        action.safeClickWithScreenshot(okButton, "Modal OK Button");
    }

    public void clickOnLogout() {
        action.safeClickWithScreenshot(logoutButton, "Logout Link");
    }

    /**
     * Deletes a random book from the list and returns the index.
     */
    public int deleteAnyBook() {
        int count = deleteButtons.count();
        if (count == 0) return -1;

        int randomNum = new Random().nextInt(count);
        Locator target = deleteButtons.nth(randomNum);

        action.safeClickWithScreenshot(target, "Delete Book at index: " + randomNum);
        return randomNum;
    }

    public int getBooksCount() {
        return booksInProfile.count();
    }
}