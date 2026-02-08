package com.framework.api;

import com.microsoft.playwright.options.FormData;
import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.utils.ReportUtils;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class APIClient {
    private APIRequestContext request;
    private ExtentTest extentTest;
    private static final Logger log = LogManager.getLogger(APIClient.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public APIClient(APIRequestContext request, ExtentTest extentTest) {
        this.request = request;
        this.extentTest = extentTest;
    }

    /**
     * Executes a GET request and logs details
     */
    public APIResponse get(String endpoint) {
        ReportUtils.logInfo(extentTest, "<b>API GET Request:</b> " + endpoint);
        APIResponse response = request.get(endpoint);
        logResponseDetails(response);
        return response;
    }

    /**
     * Executes a POST request with a body and logs details
     */
    public APIResponse post(String endpoint, Object body) {
        ReportUtils.logInfo(extentTest, "<b>API POST Request:</b> " + endpoint);
        APIResponse response = request.post(endpoint, RequestOptions.create().setData(body));
        logResponseDetails(response);
        return response;
    }

    /**
     * Helper to prettify JSON and log to Report/Console
     */
    private void logResponseDetails(APIResponse response) {
        int status = response.status();
        String body = response.text();

        log.info("API Response Status: " + status);

        try {
            // Prettify JSON string
            Object json = mapper.readValue(body, Object.class);
            String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);

            if (extentTest != null) {
                extentTest.info("Response Status: " + status);
                extentTest.info("Response Body: <pre>" + prettyJson + "</pre>");
            }
        } catch (Exception e) {
            // Fallback if body is not JSON
            if (extentTest != null) {
                extentTest.info("Response Status: " + status);
                extentTest.info("Response Body: " + body);
            }
        }
    }

    /**
     * Executes a POST request with Form Data and logs details
     * Target: https://postman-echo.com/post
     */
    public APIResponse postForm(String endpoint, Map<String, String> formData) {
        ReportUtils.logInfo(extentTest, "<b>API POST Form Request:</b> " + endpoint);

        // Create Playwright FormData object
        FormData form = FormData.create();
        formData.forEach(form::set);

        // Execute request using .setForm()
        APIResponse response = request.post(endpoint, RequestOptions.create().setForm(form));

        logResponseDetails(response);
        return response;
    }

}