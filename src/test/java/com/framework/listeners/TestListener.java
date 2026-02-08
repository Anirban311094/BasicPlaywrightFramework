package com.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.framework.base.BaseTest;
import com.framework.utils.ExtentManager;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.Base64;

public class TestListener implements ITestListener {
    // Initialize ExtentReports and Logger
    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final Logger log = LogManager.getLogger(TestListener.class);

    /**
     * This static method allows ReportUtils to access the current test instance
     * to log custom info and screenshots during the test execution.
     */
    public static ExtentTest getExtentTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info(">>>> Starting Test Execution: " + result.getMethod().getMethodName());
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("<<<< Test Passed: " + result.getMethod().getMethodName());
        test.get().pass("Test Passed Successfully");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("XXXX Test Failed: " + result.getMethod().getMethodName());
        log.error("Reason: " + result.getThrowable());

        // 1. Log the exception in Extent Report
        if (test.get() != null) {
            test.get().fail(result.getThrowable());

            try {
                // 2. Capture Screenshot as Base64 from the thread-safe Page instance in BaseTest
                byte[] buffer = BaseTest.getPage().screenshot(new Page.ScreenshotOptions().setFullPage(true));
                String base64Code = Base64.getEncoder().encodeToString(buffer);

                // 3. Attach the screenshot to the Extent Report
                test.get().fail("Failure Screenshot",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build());

                log.info("Screenshot captured and attached to the report.");
            } catch (Exception e) {
                log.error("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("---- Test Skipped: " + result.getMethod().getMethodName());
        if (test.get() != null) {
            test.get().skip("Test Skipped");
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("#### All tests finished. Writing results to HTML report... ####");
        extent.flush();
    }
}