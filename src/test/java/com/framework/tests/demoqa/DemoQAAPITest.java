package com.framework.tests.demoqa;

import com.framework.api.APIClient;
import com.framework.api.DemoQAAPI;
import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.utils.ExcelReader;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

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

        // Sending empty password to trigger error
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

    @DataProvider(name = "bookExcelData")
    public Object[][] getBookExcelData() throws IOException {
        // Path to your Excel file
        ExcelReader reader = new ExcelReader("src/test/resources/testdata/AppData.xlsx");
        String sheetName = "BookDetails";

        int rowCount = reader.getLastRow(sheetName);
        // Assuming headers are in row 0, data starts from row 1
        Object[][] data = new Object[rowCount][7];

        for (int i = 1; i <= rowCount; i++) {
            data[i - 1][0] = reader.getStringData(sheetName, i, 0); // ISBN
            data[i - 1][1] = reader.getStringData(sheetName, i, 1); // Title
            data[i - 1][2] = reader.getStringData(sheetName, i, 2); // SubTitle
            data[i - 1][3] = reader.getStringData(sheetName, i, 3); // Author
            data[i - 1][4] = reader.getStringData(sheetName, i, 4); // PublishDate
            data[i - 1][5] = reader.getStringData(sheetName, i, 5); // Publisher
            data[i - 1][6] = reader.getStringData(sheetName, i, 6); // Pages
        }
        return data;
    }

    @Test(dataProvider = "bookExcelData", description = "Iterative API validation using Excel data")
    public void testBookDetailsAPI(String isbn, String title, String subtitle, String author,
                                   String publishDate, String publisher, String pages) {

        DemoQAAPI demoApi = new DemoQAAPI(apiContext, TestListener.getExtentTest());
        APIClient client = new APIClient(apiContext, TestListener.getExtentTest());

        APIResponse response = demoApi.getBookByISBN(isbn);
        Assert.assertEquals(response.status(), 200, "API failed for ISBN: " + isbn);

        // Extracting values from API
        String isbnAPI = client.getFieldValue(response, "isbn");
        String titleAPI = client.getFieldValue(response, "title");
        String subtitleAPI = client.getFieldValue(response, "subTitle");
        String authorAPI = client.getFieldValue(response, "author");
        String publishDateAPI = client.getFieldValue(response, "publish_date");
        String publisherAPI = client.getFieldValue(response, "publisher");
        String pagesAPI = client.getFieldValue(response, "pages");

        // Iterative Validation with Console Logging
        System.out.println("--- Validating Dataset for: " + title + " ---");

        System.out.println("ISBN API: " + isbnAPI + ", Expected: " + isbn);
        Assert.assertEquals(isbnAPI, isbn);

        System.out.println("Title API: " + titleAPI + ", Expected: " + title);
        Assert.assertEquals(titleAPI, title);

        System.out.println("Author API: " + authorAPI + ", Expected: " + author);
        Assert.assertEquals(authorAPI, author);

        System.out.println("Pages API: " + pagesAPI + ", Expected: " + pages);
        Assert.assertEquals(pagesAPI, pages);

        System.out.println("----------------------------------------------");
    }
}
