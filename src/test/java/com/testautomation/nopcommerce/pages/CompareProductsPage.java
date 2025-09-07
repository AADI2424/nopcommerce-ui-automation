package com.testautomation.nopcommerce.pages;

//import com.testautomation.nopcommerce.config.AppConfig;
import com.testautomation.nopcommerce.core.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CompareProductsPage extends BasePage {

    private static final By PRODUCT_TITLES =
            By.cssSelector("table.compare-products-table .product-name a");
    private static final By COMPARE_NAMES =
            By.cssSelector("table.compare-products-table tr.product-name td a");
    private static final By CLEAR_LIST = By.cssSelector(".clear-list");

    /** Open the compare list page directly */
    public CompareProductsPage open() {
        driver.get(AppConfig.BASE_URL + "/compareproducts");
        return this;
    }

    /** Number of products currently in the compare table */
    public int productsCount() {
        return driver.findElements(PRODUCT_TITLES).size();
    }
    
    /** Whether a product with the given name is present in the compare table */
    public boolean hasProduct(String name) {
        List<WebElement> names = driver.findElements(PRODUCT_TITLES);
        for (WebElement n : names) {
            if (n.getText().trim().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    
    /** How many products are currently shown on the Compare page. */
    public int itemsCount() {
        int n = driver.findElements(COMPARE_NAMES).size();
        if (n == 0) {
            // fallback: some themes show only remove buttons – count those
            n = driver.findElements(
                    By.cssSelector("table.compare-products-table button.remove-button")
            ).size();
        }
        return n;
    }
    

    /** Click the “Clear list” link if it’s present */
    public void clearList() {
        if (isVisible(CLEAR_LIST)) {
            click(CLEAR_LIST);
            waitShort();
        }
    }
}
