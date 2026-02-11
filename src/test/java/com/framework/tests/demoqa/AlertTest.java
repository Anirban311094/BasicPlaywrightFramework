package com.framework.tests.demoqa;


import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.demoqa.AlertsPage;
import com.framework.utils.ConfigReader;
import com.framework.utils.NavigationUtils;
import com.framework.utils.ReportUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AlertTest extends BaseTest {
    private AlertsPage alertsPage;

    @BeforeMethod
    public void pageSetUp() {
        // Navigate and setup via existing POM logic
        NavigationUtils.navigateWithRetry(page, ConfigReader.getProperty("web_url_demoqa")+"alerts", 5);
//        page.navigate(ConfigReader.getProperty("web_url_demoqa")+"alerts");
        alertsPage = new AlertsPage(page, TestListener.getExtentTest());
    }

    @Test
    public void userCanOpenAlertWindow() {
        // Setup listener BEFORE clicking
        page.onceDialog(dialog -> {
            Assert.assertEquals(dialog.message(), "You clicked a button");
            ReportUtils.logInfo(TestListener.getExtentTest(), "Alert handled: " + dialog.message());
            dialog.accept();
        });

        alertsPage.clickOnAlertButton();
    }

    @Test
    public void userCanOpenTimerAlertWindow() {
        page.onceDialog(dialog -> {
            Assert.assertEquals(dialog.message(), "This alert appeared after 5 seconds");
            dialog.accept();
        });

        alertsPage.clickOnTimerAlertButton();
        // Playwright automatically waits for the dialog even if it takes 5 seconds
    }

    @Test
    public void userCanOpenConfirmWindow() {
        page.onceDialog(dialog -> {
            Assert.assertEquals(dialog.message(), "Do you confirm action?");
            dialog.accept(); // Clicks OK
        });

        alertsPage.clickOnConfirmButton();
        Assert.assertEquals(alertsPage.getSuccessMessageText(), "You selected Ok");
    }

    @Test
    public void userCanOpenPromptWindow() {
        String inputText = "itbootcamp";

        page.onceDialog(dialog -> {
            Assert.assertEquals(dialog.type(), "prompt");
            dialog.accept(inputText); // Accept with input text
        });

        alertsPage.clickOnPromptButton();
        Assert.assertEquals(alertsPage.getSuccessMessageText(), "You entered " + inputText);
    }
}
