package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;
import com.testautomation.nopcommerce.core.AppConfig;

public class ProductPage extends BasePage {

    private static final By PRODUCT_TITLE = By.cssSelector("div.product-name h1");
    private static final By ADD_TO_CART = By.cssSelector("button.add-to-cart-button");
    private static final By ADD_TO_WISHLIST = By.cssSelector("button.add-to-wishlist-button");
    private static final By ADD_TO_COMPARE = By.cssSelector("button.add-to-compare-list-button");

    public String getTitle() {
        return $(PRODUCT_TITLE).getText().trim();
    }

    public void addToWishlist() {
        click(ADD_TO_WISHLIST);
        waitNotification();
    }

    public void addToCompare() {
        click(ADD_TO_COMPARE);
        waitNotification();
    }

    /** Tries to add to cart; if the product needs options, clicking still navigates/validates. */
    public void addToCartIfPossible() {
        click(ADD_TO_CART);
        waitNotification();
    }
}
