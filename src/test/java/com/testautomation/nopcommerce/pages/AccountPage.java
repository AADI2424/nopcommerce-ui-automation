package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;

public class AccountPage extends BasePage {

    private static final By MY_ACCOUNT = By.cssSelector("a.ico-account, a.account, a[href*='customer/info']");
    private static final By LOGOUT = By.cssSelector("a.ico-logout, a[href*='logout']");

    public boolean isMyAccountVisible() {
        return !driver.findElements(MY_ACCOUNT).isEmpty();
    }

    public void logout() {
        if (!driver.findElements(LOGOUT).isEmpty()) {
            click(LOGOUT);
            waitShort();
        }
    }
}
