package com.framework.api;

import com.aventstack.extentreports.ExtentTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class APIClient {
    private static final Logger log = LogManager.getLogger(APIClient.class);
    private final APIRequestContext request;
    private final ExtentTest extentTest;
    private final ObjectMapper mapper = new ObjectMapper();

    public APIClient(APIRequestContext request, ExtentTest extentTest) {
        this.request = request;
        this.extentTest = extentTest;
    }

    /**
     * Executes a POST request and logs details.
     */
    public APIResponse post(String endpoint, Object body) {
        APIResponse response = request.post(endpoint, RequestOptions.create()
                .setHeader("accept", "application/json")
                .setHeader("Content-Type", "application/json")
                .setData(body));

        logResponseDetails(response);
        return response;
    }

    private void logResponseDetails(APIResponse response) {
        int status = response.status();
        String bodyText = response.text();
        String formattedBody;

        try {
            Object json = mapper.readValue(bodyText, Object.class);
            formattedBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        } catch (Exception e) {
            formattedBody = bodyText;
        }

        log.info("API Response | Status: {} | URL: {}", status, response.url());

        if (extentTest != null) {
            extentTest.info("<b>Endpoint:</b> " + response.url());
            extentTest.info("<b>Status Code:</b> " + status);
            extentTest.info("<b>Response Body:</b> <pre>" + formattedBody + "</pre>");
        }
    }

    /**
     * Extracts a specific string value from the JSON response body.
     *
     */
    public String getFieldValue(APIResponse response, String fieldName) {
        try {
            JsonNode node = mapper.readTree(response.text());
            return node.get(fieldName).asText();
        } catch (Exception e) {
            log.error("Failed to extract field {} from response", fieldName);
            return null;
        }
    }
}