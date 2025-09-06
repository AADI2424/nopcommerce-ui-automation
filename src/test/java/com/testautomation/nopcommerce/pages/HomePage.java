package com.testautomation.nopcommerce.pages;

import com.testautomation.nopcommerce.core.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

/** Page objects for the demo store home page. */
public class HomePage extends BasePage {

    private static final By SEARCH_INPUT  = By.id("small-searchterms");
    private static final By SEARCH_BUTTON = By.cssSelector("button[type='submit']");

    /** Open base URL and wait for home page to be ready. */
    public HomePage open() {
        driver.get(AppConfig.BASE_URL);
        return waitUntilLoaded();
    }

    /** Open any URL (useful if you point to other envs) and wait. */
    public HomePage open(String url) {
        driver.get(url);
        return waitUntilLoaded();
    }

    /** Wait for a stable marker of the page (search box or title). */
    public HomePage waitUntilLoaded() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT),
                ExpectedConditions.titleContains("nopCommerce")
        ));
        return this;
    }

    /** Title accessor used by steps. */
    public String getTitle() {
        return driver.getTitle();
    }

    /** Visibility accessor used by steps. */
    public boolean isSearchBoxVisible() {
        return isVisible(SEARCH_INPUT);
    }

    /** Simple search action (kept from your original). */
    public void search(String text) {
        type(SEARCH_INPUT, text);
        click(SEARCH_BUTTON);
    }
}
