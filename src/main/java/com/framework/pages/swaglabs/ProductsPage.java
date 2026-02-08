package com.framework.pages.swaglabs;
import com.framework.components.ActionComponent;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.framework.utils.ReportUtils;
import com.aventstack.extentreports.ExtentTest;

public class ProductsPage {
    private final Page page;
    private static final Logger log = LogManager.getLogger(LoginPage.class);
    private ActionComponent action;
    private final ExtentTest extentTest;

    private final Locator sortByDropdown;
    private final Locator allItems;
    private final Locator allItemsPrice;

    public ProductsPage(Page page, ExtentTest extentTest) {
        this.page = page;
        this.action = new ActionComponent(page, extentTest);
        this.extentTest = extentTest;

        this.allItems = page.locator("//div[@class='inventory_item_name ']");
        this.allItemsPrice = page.locator("//div[@class='inventory_item_price']");
        this.sortByDropdown = page.locator("//select[@class='product_sort_container']");
    }

    public void verifyProductSortByDesc() {

    }
}
