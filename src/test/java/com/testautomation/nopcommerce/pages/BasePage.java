package com.testautomation.nopcommerce.pages;

import com.testautomation.nopcommerce.core.AppConfig;
import com.testautomation.nopcommerce.core.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Small base with sane waits + helpers. */
public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(AppConfig.EXPLICIT_WAIT_SEC));
    }

    protected WebElement $(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void click(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by)).click();
    }

    protected void type(By by, CharSequence text) {
        WebElement el = $(by);
        el.clear();
        el.sendKeys(text);
    }

    protected boolean isVisible(By by) {
        try {
            return $(by).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
