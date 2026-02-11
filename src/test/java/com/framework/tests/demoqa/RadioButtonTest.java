package com.framework.tests.demoqa;

import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.demoqa.RadioButtonPage;
import com.framework.utils.NavigationUtils;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RadioButtonTest extends BaseTest {
    private RadioButtonPage radioButtonPage;

    @BeforeMethod
    public void pageSetUp() {
        // Direct navigation to the sub-page for speed and stability
        NavigationUtils.navigateWithRetry(page, "https://demoqa.com/radio-button", 5);
        radioButtonPage = new RadioButtonPage(page, TestListener.getExtentTest());
    }

    @Test(description = "Verify that a user can randomly select any enabled radio button and see the correct success message")
    public void userCanSelectAnyRadioButton() {
        String expectedMessage = radioButtonPage.clickOnAnyRadioButton();

        // Assertion: Verify success message appears and matches the clicked button text
        Assert.assertEquals(radioButtonPage.getSuccessMessageText(), expectedMessage,
                "The success message text did not match the selected radio button label.");
    }
}
