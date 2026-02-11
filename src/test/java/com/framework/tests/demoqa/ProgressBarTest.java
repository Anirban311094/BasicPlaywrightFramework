package com.framework.tests.demoqa;

import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.demoqa.ProgressBarPage;
import com.framework.utils.NavigationUtils;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

public class ProgressBarTest extends BaseTest {
    private ProgressBarPage progressBarPage;

    @BeforeMethod
    public void pageSetUp() {
        NavigationUtils.navigateWithRetry(page, "https://demoqa.com/progress-bar", 5);
        progressBarPage = new ProgressBarPage(page, TestListener.getExtentTest());
    }

    @Test(description = "Verify the progress bar reaches 100% and turns green")
    public void userCanRunProgressBar() {
        progressBarPage.clickOnStartStopButton();

        // Playwright auto-waits up to 30s for this locator to appear
        PlaywrightAssertions.assertThat(progressBarPage.getSuccessProgressBar()).isVisible();
        Assert.assertEquals(progressBarPage.getProgressBar().innerText(), "100%");
    }

    @Test(description = "Verify the progress bar can be reset after completion")
    public void userCanResetProgressBar() {
        progressBarPage.clickOnStartStopButton();

        // Wait for reset button to become visible
        progressBarPage.getSuccessProgressBar().waitFor();
        progressBarPage.clickOnResetButton();

        Assert.assertEquals(progressBarPage.getProgressBar().innerText(), "0%");
    }

    @Test(description = "Verify the progress bar can be stopped mid-way")
    public void userCanStopProgressBar() {
        progressBarPage.clickOnStartStopButton();

        page.waitForTimeout(3000); // Wait for 3 seconds of progress
        progressBarPage.clickOnStartStopButton();

        Assert.assertEquals(progressBarPage.getStartStopButtonText(), "Start");
        // Verify style attribute contains the current percentage text
        String currentProgress = progressBarPage.getProgressBar().innerText();
        Assert.assertTrue(progressBarPage.getProgressBar().getAttribute("style").contains(currentProgress));
    }

    @Test(description = "Verify progress bar can be paused and then resumed to completion")
    public void userCanContinueRunningProgressBar() {
        progressBarPage.clickOnStartStopButton();
        page.waitForTimeout(2000);

        progressBarPage.clickOnStartStopButton();
        String pausedProgress = progressBarPage.getProgressBar().innerText();

        progressBarPage.clickOnStartStopButton(); // Resume

        // Wait for completion
        PlaywrightAssertions.assertThat(progressBarPage.getSuccessProgressBar()).isVisible();
        String finalProgress = progressBarPage.getProgressBar().innerText();

        Assert.assertNotEquals(pausedProgress, finalProgress, "Progress did not increase after resuming");
    }
}
