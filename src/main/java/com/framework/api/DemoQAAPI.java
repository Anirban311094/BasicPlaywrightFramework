package com.framework.api;

import com.aventstack.extentreports.ExtentTest;
import com.framework.utils.ConfigReader;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import java.util.HashMap;
import java.util.Map;

public class DemoQAAPI {
    private final APIClient apiClient;

    public DemoQAAPI(APIRequestContext request, ExtentTest extentTest) {
        this.apiClient = new APIClient(request, extentTest);
    }

    /**
     * Generates a token for the account.
     */
    public APIResponse generateToken(String username, String password) {
        Map<String, String> body = new HashMap<>();
        body.put("userName", username); //
        body.put("password", password); //
        return apiClient.post(ConfigReader.getProperty("api_url_demoqa")+"/Account/v1/GenerateToken", body);
    }
}
