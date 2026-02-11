package com.framework.tests.demoqa;

import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.demoqa.ModalPage;
import com.framework.utils.ConfigReader;
import com.framework.utils.NavigationUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ModalTest extends BaseTest {
    private ModalPage modalPage;

    @BeforeMethod
    public void pageSetUp() {
        // Using our Utility for stable navigation to the slow DemoQA site
        NavigationUtils.navigateWithRetry(page, ConfigReader.getProperty("web_url_demoqa")+"modal-dialogs", 5);
        modalPage = new ModalPage(page, TestListener.getExtentTest());
    }

    @Test(description = "Verify that a user can successfully open the Small Modal dialog")
    public void userCanOpenSmallModal() {
        modalPage.clickOnSmallModal();
        Assert.assertTrue(modalPage.isModalVisible(), "Small modal was not displayed");
        Assert.assertEquals(modalPage.getModalTitle(), "Small Modal");
    }

    @Test(description = "Verify that a user can successfully open the Large Modal dialog")
    public void userCanOpenLargeModal() {
        modalPage.clickOnLargeModal();
        Assert.assertTrue(modalPage.isModalVisible(), "Large modal was not displayed");
        Assert.assertEquals(modalPage.getModalTitle(), "Large Modal");
    }

    @Test(description = "Verify that a user can close the Small Modal using the close button")
    public void userCanCloseSmallModal() {
        modalPage.clickOnSmallModal();
        // Playwright handles wait for visibility automatically during click
        modalPage.closeSmallModal();
        Assert.assertFalse(modalPage.isModalVisible(), "Small modal is still visible after closing");
    }

    @Test(description = "Verify that a user can close the Large Modal using the close button")
    public void userCanCloseLargeModal() {
        modalPage.clickOnLargeModal();
        modalPage.closeLargeModal();
        Assert.assertFalse(modalPage.isModalVisible(), "Large modal is still visible after closing");
    }
}
