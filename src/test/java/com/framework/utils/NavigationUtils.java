package com.framework.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitUntilState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NavigationUtils {
    private static final Logger log = LogManager.getLogger(NavigationUtils.class);

    /**
     * Navigates to a URL with a deep-clean retry mechanism.
     * Handles: Timeouts, 502 Bad Gateway, and "Site can't be reached" errors.
     */
    public static void navigateWithRetry(Page page, String url, int maxRetries) {
        int attempts = 0;
        boolean isLoaded = false;

        while (attempts < maxRetries && !isLoaded) {
            try {
                log.info("Attempting navigation: {} (Attempt {}/{})", url, (attempts + 1), maxRetries);

                // 1. Set a generous timeout for the initial load
                page.navigate(url, new Page.NavigateOptions()
                        .setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
                        .setTimeout(45000));

                // 2. Validate the page content to catch "soft" errors like 502 or 404
                String content = page.content().toLowerCase();
                if (content.contains("502 bad gateway") || content.contains("nginx") || page.title().contains("502")) {
                    throw new PlaywrightException("Server Error: 502 Bad Gateway detected.");
                }

                // 3. Check if we are on a "Site can't be reached" pseudo-page (Chromium specific)
                if (content.contains("err_connection_timed_out") || content.contains("err_connection_refused")) {
                    throw new PlaywrightException("Network Error: Connection Timed Out.");
                }

                isLoaded = true;
                log.info("Successfully loaded: {}", url);

            } catch (Exception e) {
                attempts++;
                log.error("Navigation attempt {} failed. Error: {}", attempts, e.getMessage());

                if (attempts < maxRetries) {
                    log.warn("Clearing state and retrying...");

                    // DEEP CLEAN: Clear cookies and storage for a fresh session
                    page.context().clearCookies();
                    page.evaluate("() => { localStorage.clear(); sessionStorage.clear(); }");

                    // Wait 3 seconds before next attempt to allow server/network to breathe
                    page.waitForTimeout(3000);

                    // Force a reload from the server (bypassing cache)
                    try {
                        page.reload(new Page.ReloadOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
                    } catch (Exception reloadError) {
                        log.debug("Reload failed, continuing to next loop iteration.");
                    }
                } else {
                    log.error("Critical: Page failed to load after {} attempts.", maxRetries);
                    throw e;
                }
            }
        }
    }
}