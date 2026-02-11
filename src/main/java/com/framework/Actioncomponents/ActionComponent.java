package com.framework.Actioncomponents;

import com.aventstack.extentreports.ExtentTest;
import com.framework.utils.ReportUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ActionComponent {
    private Page page;
    private ExtentTest extentTest;
    private static final Logger log = LogManager.getLogger(ActionComponent.class);

    public ActionComponent(Page page, ExtentTest extentTest) {
        this.page = page;
        this.extentTest = extentTest;
    }

    // --- Basic Actions ---

    public void safeClickWithScreenshot(Locator locator, String description) {
        log.info("Clicking on: " + description);
        locator.click();
        ReportUtils.logStepWithScreenshot(page, extentTest, "Clicked: " + description);
    }

    public void safeFillWithScreenshot(Locator locator, String text, String description) {
        log.info("Filling " + description + " with: " + text);
        locator.fill(text);
        ReportUtils.logStepWithScreenshot(page, extentTest, "Filled " + description);
    }

    /**
     * Gets text from an element and logs it to the report
     */
    public String safeGetText(Locator locator, String description) {
        try {
            String text = locator.innerText();
            ReportUtils.logInfo(extentTest, "Retrieved text from " + description + ": " + text);
            return text;
        } catch (Exception e) {
            log.error("Failed to get text from " + description + " : " + e.getMessage());
            return "";
        }
    }

    // --- Dropdown Methods ---

    /**
     * Selects an option from a dropdown by value, label, or index.
     */
    public void selectFromDropdown(Locator locator, String option, String description) {
        log.info("Selecting option '" + option + "' from " + description);
        locator.selectOption(option);
        ReportUtils.logStepWithScreenshot(page, extentTest, "Selected '" + option + "' from " + description);
    }

    /**
     * Retrieves all text options from a select dropdown.
     */
    public List<String> getAllDropdownOptions(Locator locator, String description) {
        log.info("Getting all options from " + description);
        List<String> options = locator.locator("option").allInnerTexts();
        ReportUtils.logInfo(extentTest, "Options in " + description + ": " + options);
        return options;
    }

    // --- Tab Management Methods ---

    /**
     * Clicks an element that opens a new tab and switches the current 'page' reference to it.
     */
    public Page openAndSwitchToNewTab(Locator triggerLocator, String description) {
        log.info("Opening new tab via: " + description);
        Page newPage = page.context().waitForPage(() -> {
            triggerLocator.click();
        });
        newPage.waitForLoadState();
        this.page = newPage; // Update internal reference
        ReportUtils.logStepWithScreenshot(page, extentTest, "Switched to new tab: " + page.title());
        return page;
    }

    /**
     * Switches to a tab based on its index in the browser context.
     */
    public void switchToTabByIndex(int index) {
        List<Page> allPages = page.context().pages();
        if (index < allPages.size()) {
            this.page = allPages.get(index);
            this.page.bringToFront();
            log.info("Switched to tab at index: " + index);
            ReportUtils.logStepWithScreenshot(page, extentTest, "Switched to tab index " + index);
        } else {
            log.error("Tab index " + index + " out of bounds.");
        }
    }

    /**
     * Switches to the next available tab in the list.
     */
    public void switchToNextTab() {
        List<Page> allPages = page.context().pages();
        int currentIndex = allPages.indexOf(page);
        int nextIndex = (currentIndex + 1) % allPages.size();
        switchToTabByIndex(nextIndex);
    }

    /**
     * Switches to the previous tab in the list.
     */
    public void switchToPreviousTab() {
        List<Page> allPages = page.context().pages();
        int currentIndex = allPages.indexOf(page);
        int prevIndex = (currentIndex - 1 + allPages.size()) % allPages.size();
        switchToTabByIndex(prevIndex);
    }

    // Getter to retrieve the current active page after a switch
    public Page getPage() {
        return this.page;
    }
}