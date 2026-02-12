package com.framework.tests.demoqa.bookstore;

import com.framework.base.BaseTest;
import com.framework.listeners.TestListener;
import com.framework.pages.demoqa.bookstore.BookStorePage;
import com.framework.pages.demoqa.bookstore.LoginPage;
import com.framework.pages.demoqa.bookstore.ProfilePage;
import com.framework.utils.ExcelReader;
import com.framework.utils.NavigationUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.IOException;

public class BookStoreTest extends BaseTest {
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private BookStorePage bookStorePage;
    private ExcelReader reader;

    @BeforeMethod
    public void pageSetUp() throws IOException {
        // Handle flakiness with deep-clean navigation
        NavigationUtils.navigateWithRetry(page, "https://demoqa.com/login", 5);

        loginPage = new LoginPage(page, TestListener.getExtentTest());
        profilePage = new ProfilePage(page, TestListener.getExtentTest());
        bookStorePage = new BookStorePage(page, TestListener.getExtentTest());
        reader = new ExcelReader("src/test/resources/testdata/AppData.xlsx");

        // Login using data from Excel
        String username = reader.getStringData("Login", 2, 0);
        String password = reader.getStringData("Login", 2, 1);
        loginPage.inputUsername(username);
        loginPage.inputPassword(password);
        loginPage.clickOnLoginButton();
        page.waitForURL("**/profile");
    }

    @Test(priority = 10, description = "Verify a user can add a book to their collection")
    public void userCanAddAnyBook() {
        bookStorePage.navigateTo("Book Store");

        // Setup Playwright dialog handler for the 'Book added' alert
        page.onceDialog(dialog -> dialog.accept());

        bookStorePage.clickOnAnyBook();
        String bookTitle = bookStorePage.getSelectedBookTitle();
        bookStorePage.clickOnAddToCollection();

        // Refresh and verify in Profile
        page.reload();
        bookStorePage.navigateTo("Profile");
        Assert.assertTrue(profilePage.getBooksTextList().contains(bookTitle), "Book was not found in profile!");
    }

    @Test(priority = 20, description = "Clean up: Delete all books from profile")
    public void cleanUpProfile() {
        bookStorePage.navigateTo("Profile");

        if (profilePage.getBooksCount() > 0) {
            // Setup dialog handler for the confirmation alert
            page.onceDialog(dialog -> dialog.accept());
            profilePage.clickOnDeleteAllBooks();
            profilePage.clickOnOk();

            page.reload();
            Assert.assertEquals(profilePage.getBooksCount(), 0, "Books were not deleted!");
        }
    }

    @Test(description = "Search for a book in the profile")
    public void userCanSearchBooks() {
        bookStorePage.navigateTo("Profile");
        String searchInput = "Git Pocket Guide";

        profilePage.inputSearch(searchInput);
        Assert.assertTrue(profilePage.getBooksTextList().stream()
                .anyMatch(text -> text.equalsIgnoreCase(searchInput)));
    }

    @Test(description = "Delete a single book from profile")
    public void userCanDeleteAnyBook() {
        bookStorePage.navigateTo("Profile");
        int countBefore = profilePage.getBooksCount();

        if (countBefore > 0) {
            page.onceDialog(dialog -> dialog.accept());
            profilePage.deleteAnyBook();
            profilePage.clickOnOk();

            page.reload();
            Assert.assertEquals(profilePage.getBooksCount(), countBefore - 1);
        }
    }


}