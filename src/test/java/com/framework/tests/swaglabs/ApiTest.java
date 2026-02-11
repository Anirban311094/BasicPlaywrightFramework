package com.framework.tests.swaglabs;

import com.framework.api.APIClient;
import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class ApiTest extends BaseTest {

    @Test
    public void verifyStatusCode() {
        // Initialize client using the context from BaseTest and current ExtentTest
        APIClient client = new APIClient(apiContext, TestListener.getExtentTest());

        // Prepare the Form Data
        Map<String, String> params = new HashMap<>();
        params.put("foo1", "bar1");
        params.put("foo2", "bar2");

        // Execute POST
        APIResponse response = client.postForm("https://postman-echo.com/post", params);
        // Assertions
        Assert.assertEquals(response.status(), 200);
        Assert.assertTrue(response.text().contains("bar1"), "Response does not contain expected form value 'bar1'");
    }
}