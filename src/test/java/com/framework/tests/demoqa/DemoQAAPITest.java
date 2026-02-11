package com.framework.tests.demoqa;

import com.framework.api.APIClient;
import com.framework.api.DemoQAAPI;
import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoQAAPITest extends BaseTest {

    @Test(description = "Verify successful token generation (200 Success model)")
    public void testTokenGenerationSuccess() {
        DemoQAAPI demoApi = new DemoQAAPI(apiContext, TestListener.getExtentTest());

        APIResponse response = demoApi.generateToken("anirchak", "Anicha@7809#");

        Assert.assertEquals(response.status(), 200, "API did not return 200 Success");

        String body = response.text();

        Assert.assertTrue(body.contains("token"), "Missing token field");
        Assert.assertTrue(body.contains("expires"), "Missing expires field");
        Assert.assertTrue(body.contains("status"), "Missing status field");
        Assert.assertTrue(body.contains("result"), "Missing result field");
    }

    @Test(description = "Verify error handling for invalid request (400 Error model)")
    public void testTokenGenerationBadRequest() {
        DemoQAAPI demoApi = new DemoQAAPI(apiContext, TestListener.getExtentTest());

        // Sending empty password to trigger 400 error
        APIResponse response = demoApi.generateToken("anirchak", "");

        Assert.assertEquals(response.status(), 400, "API did not return 400 Error");

        String body = response.text();

        Assert.assertTrue(body.contains("code"), "Missing code field");
        Assert.assertTrue(body.contains("message"), "Missing message field");
    }

    @Test(description = "Verify token generation and fetch the token value")
    public void testTokenFetching() {
        DemoQAAPI demoApi = new DemoQAAPI(apiContext, TestListener.getExtentTest());

        APIResponse response = demoApi.generateToken("anirchak", "Anicha@7809#");
        Assert.assertEquals(response.status(), 200);

        String token = new APIClient(apiContext, null).getFieldValue(response, "token");
        System.out.println("token: "+token);

        Assert.assertNotNull(token, "Token should not be null");
        System.out.println("Fetched Token: " + token);

        if (TestListener.getExtentTest() != null) {
            TestListener.getExtentTest().pass("Successfully fetched token: " + token);
        }
    }
}
