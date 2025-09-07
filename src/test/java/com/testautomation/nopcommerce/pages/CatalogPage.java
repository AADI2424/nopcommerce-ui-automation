package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CatalogPage extends BasePage {

    private static final By CATEGORY_TITLE = By.cssSelector("div.page-title h1");
    private static final By PRODUCT_LINKS  = By.cssSelector("h2.product-title a"); 
    private static final By PRICE_SPANS    = By.cssSelector("span.price.actual-price");
    private static final By SORT_SELECT    = By.id("products-orderby");

    public String getCategoryTitle() {
        return $(CATEGORY_TITLE).getText().trim();
    }

    public void openFirstProduct() {
        List<WebElement> links = driver.findElements(PRODUCT_LINKS);
        links.get(0).click();
    }

    public void sortBy(String visibleText) {
        WebElement firstBefore = null;
        var listBefore = driver.findElements(PRODUCT_LINKS);
        if (!listBefore.isEmpty()) firstBefore = listBefore.get(0);

        selectByVisibleText(SORT_SELECT, visibleText);

        // wait for grid refresh
        if (firstBefore != null) {
            wait.until(ExpectedConditions.stalenessOf(firstBefore));
        }
        wait.until(d -> driver.findElements(PRICE_SPANS).size() >= 2);
    }

    public boolean isSortedLowToHigh() {
        wait.until(d -> driver.findElements(PRICE_SPANS).size() >= 2);
        List<BigDecimal> prices = driver.findElements(PRICE_SPANS)
                .stream()
                .map(WebElement::getText)
                .map(this::toNumber)
                .collect(Collectors.toList());
        if (prices.size() < 2) return true;
        return prices.get(0).compareTo(prices.get(1)) <= 0;
    }

    private BigDecimal toNumber(String text) {
        String clean = text.replaceAll("[^0-9.]", "");
        if (clean.isEmpty()) clean = "0";
        return new BigDecimal(clean);
    }

    public void openTopMenu(String menu) { click(By.linkText(menu)); }

    public void openSubCategory(String sub) {
        click(By.linkText(sub));
        waitShort();
    }

    public void addToWishlistFromGrid(String productName) {
        By btn = By.xpath("//h2[@class='product-title']/a[normalize-space()='" + productName + "']" +
                "/ancestor::div[contains(@class,'product-item')]//button[contains(@class,'add-to-wishlist')]");
        click(btn);
        waitShort();
    }

    public void addToCompareFromGrid(String productName) {
        By btn = By.xpath("//h2[@class='product-title']/a[normalize-space()='" + productName + "']" +
                "/ancestor::div[contains(@class,'product-item')]//button[contains(@class,'add-to-compare')]");
        click(btn);
        waitShort();
    }

    /** Opens the first product and returns its name so steps can assert against either expected text or this name. */
    public String openFirstProductAndRememberName() {
        String name = $(PRODUCT_LINKS).getText().trim();
        click(PRODUCT_LINKS);
        return name;
    }
}
