package com.framework.utils;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Base64;

public class ReportUtils {
    private static final Logger log = LogManager.getLogger(ReportUtils.class);

    /**
     * Logs to Console and Extent Report with a screenshot.
     * We pass 'page' and 'extentTest' as arguments so this utility
     * stays in the 'main' folder without import errors.
     */
    public static void logStepWithScreenshot(Page page, ExtentTest extentTest, String message) {
        log.info(message);

        try {
            byte[] buffer = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            String base64Code = Base64.getEncoder().encodeToString(buffer);

            if (extentTest != null) {
                extentTest.info(message,
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Code).build());
            }
        } catch (Exception e) {
            log.error("Could not capture screenshot: " + e.getMessage());
        }
    }

    public static void logInfo(ExtentTest extentTest, String message) {
        log.info(message);
        if (extentTest != null) {
            extentTest.info(message);
        }
    }
}