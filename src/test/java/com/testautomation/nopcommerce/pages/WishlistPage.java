package com.testautomation.nopcommerce.pages;

import com.testautomation.nopcommerce.config.AppConfig;
import org.openqa.selenium.By;

public class WishlistPage extends BasePage {

    // All product links shown in the wishlist table
    private static final By PRODUCT_LINKS =
            By.cssSelector("table.wishlist-content td.product a");

    public WishlistPage open() {
        driver.get(AppConfig.BASE_URL + "/wishlist");
        return this;
    }

    public int itemsCount() {
        return driver.findElements(PRODUCT_LINKS).size();
    }

    public boolean hasProduct(String name) {
        return driver.findElements(PRODUCT_LINKS)
                .stream()
                .anyMatch(el -> el.getText().trim().equalsIgnoreCase(name));
    }
}
