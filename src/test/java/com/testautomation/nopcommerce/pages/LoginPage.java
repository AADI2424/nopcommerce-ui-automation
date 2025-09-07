package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final By EMAIL = By.id("Email");
    private static final By PASSWORD = By.id("Password");
    private static final By LOGIN_BTN = By.cssSelector("button.login-button");

    public void login(String email, String password) {
        type(EMAIL, email);
        type(PASSWORD, password);
        click(LOGIN_BTN);
        waitShort();
    }
}
