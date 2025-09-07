package com.testautomation.nopcommerce.pages;

import com.testautomation.nopcommerce.core.AppConfig;
import com.testautomation.nopcommerce.core.DriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.Select;

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
    
    protected void selectByVisibleText(By select, String text) {
        new Select($(select)).selectByVisibleText(text);
    }

    protected String textOf(By by) {
        return $(by).getText().trim();
    }

    protected void waitShort() {
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
    }

    protected void waitUntilTextPresent(By by, String fragmentLowercase) {
        long end = System.currentTimeMillis() + 8000;
        while (System.currentTimeMillis() < end) {
            String t = driver.findElement(by).getText().toLowerCase();
            if (t.contains(fragmentLowercase.toLowerCase())) return;
            waitShort();
        }
    } 
    
 // Wait for the green notification bar to appear and go away (or be closed)
    protected void waitNotification() {
        try {
            WebElement bar = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.bar-notification")
                )
            );

            // Close it if a close “x” exists so it doesn't block clicks
            java.util.List<WebElement> closes = bar.findElements(By.cssSelector(".close"));
            if (!closes.isEmpty()) {
                try { closes.get(0).click(); } catch (Exception ignored) {}
            }

            // Wait until it disappears (auto-hide or after close)
            wait.until(ExpectedConditions.invisibilityOf(bar));
        } catch (TimeoutException ignored) {
            // If no notification ever appears, just continue
        }
    }
}
