package com.framework.base;

import com.framework.utils.ConfigReader;
import com.microsoft.playwright.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected APIRequestContext apiContext;

    protected static final Logger log = LogManager.getLogger(BaseTest.class);

    // ThreadLocal containers to ensure parallel execution safety
    private static ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static ThreadLocal<APIRequestContext> threadLocalApiContext = new ThreadLocal<>();

    // Getters for the Listener and API Client
    public static Page getPage() {
        return threadLocalPage.get();
    }

    public static APIRequestContext getApiContext() {
        return threadLocalApiContext.get();
    }

    @BeforeClass
    public void setup() {
        log.info("--- Initializing Framework Setup ---");
        playwright = Playwright.create();

        // 1. Initialize Browser based on config
        String browserName = ConfigReader.getProperty("browser");
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        log.info("Launching Browser: " + browserName + " (Headless: " + isHeadless + ")");

        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(isHeadless);

        if (browserName.equalsIgnoreCase("firefox")) {
            browser = playwright.firefox().launch(options);
        } else if (browserName.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(options);
        } else {
            browser = playwright.chromium().launch(options);
        }

        // 2. Setup Web Context & Page
        context = browser.newContext();
        page = context.newPage();
        threadLocalPage.set(page);
        log.info("Web context and Page initialized.");

        // 3. Setup API Context for API Testing
        log.info("Setting up API Context with Base URL: " + ConfigReader.getProperty("api_url"));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        apiContext = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(ConfigReader.getProperty("api_url"))
                .setExtraHTTPHeaders(headers));

        // Save API context to ThreadLocal for APIClient access
        threadLocalApiContext.set(apiContext);
    }

    @AfterClass
    public void tearDown() {
        log.info("--- Tearing Down Framework Setup ---");

        if (threadLocalApiContext.get() != null) {
            threadLocalApiContext.get().dispose();
            threadLocalApiContext.remove();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }

        if (threadLocalPage.get() != null) {
            threadLocalPage.remove();
        }

        log.info("All resources closed successfully.");
    }
}