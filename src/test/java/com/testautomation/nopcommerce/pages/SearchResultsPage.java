package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;

public class SearchResultsPage extends BasePage {
    private static final By PRODUCT_TILES = By.cssSelector("div.product-item");

    public boolean hasResults() {
        return driver.findElements(PRODUCT_TILES).size() > 0;
    }
}
