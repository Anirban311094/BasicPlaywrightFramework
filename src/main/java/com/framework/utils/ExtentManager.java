package com.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // This creates a 'reports' folder in your project root
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExecutionReport.html");
            spark.config().setReportName("Automation Test Results");
            spark.config().setDocumentTitle("Test Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "Playwright-Java");
            extent.setSystemInfo("Author", "Anirban Chakrabarty");
        }
        return extent;
    }
}