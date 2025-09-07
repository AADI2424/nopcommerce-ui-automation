package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;
import com.testautomation.nopcommerce.core.AppConfig;
import org.openqa.selenium.support.ui.ExpectedConditions;  

public class CartPage extends BasePage {

    private static final By ROWS = By.cssSelector("table.cart tbody tr");
  //  private static final By REMOVE_FIRST = By.cssSelector("table.cart tbody tr:first-child button.remove-btn, table.cart tbody tr:first-child button.remove, table.cart tbody tr:first-child input.remove-from-cart");
    private static final By REMOVE_FIRST =
    	    By.cssSelector("table.cart tbody tr:first-child input[name='removefromcart']");
    private static final By UPDATE_CART = By.cssSelector("button[name='updatecart'], button.update-cart-button");

//    public void removeFirstItem() {
//        if (driver.findElements(ROWS).isEmpty()) return;
//        if (!driver.findElements(REMOVE_FIRST).isEmpty()) {
//            click(REMOVE_FIRST);
//        }
//        if (!driver.findElements(UPDATE_CART).isEmpty()) {
//            click(UPDATE_CART);
//        }
//        waitShort();
//    }
    
    public void removeFirstItem() {
        int before = driver.findElements(ROWS).size();
        if (before == 0) return;

        click(REMOVE_FIRST);
        if (!driver.findElements(UPDATE_CART).isEmpty()) {
            click(UPDATE_CART);
        }

        // Wait until at least one row disappears (or the cart becomes empty)
        wait.until(ExpectedConditions.numberOfElementsToBeLessThan(ROWS, before));
    }

    public boolean isEmpty() {
        return driver.findElements(ROWS).isEmpty();
    }
}
