package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;

public class RegisterPage extends BasePage {

    private static final By FIRST = By.id("FirstName");
    private static final By LAST = By.id("LastName");
    private static final By EMAIL = By.id("Email");
    private static final By PASSWORD = By.id("Password");
    private static final By CONFIRM = By.id("ConfirmPassword");
    private static final By REGISTER_BTN = By.id("register-button");
    private static final By RESULT = By.cssSelector("div.result");

    public void register(String first, String last, String email, String password) {
        type(FIRST, first);
        type(LAST, last);
        type(EMAIL, email);
        type(PASSWORD, password);
        type(CONFIRM, password);
        click(REGISTER_BTN);
        waitUntilTextPresent(RESULT, "registration completed");
    }
}
