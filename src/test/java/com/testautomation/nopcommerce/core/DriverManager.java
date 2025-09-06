package com.testautomation.nopcommerce.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Very small driver lifecycle helper backed by ThreadLocal.
 * Names and usage match what's already in your project.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        return TL_DRIVER.get();
    }

    public static void initDriver() {
        if (getDriver() != null) return;

        // Browser option (defaults to chrome). Add others later if you want.
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            // Faster & more stable in CI
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                options.addArguments("--headless=new");
            }
            options.addArguments("--remote-allow-origins=*");
            TL_DRIVER.set(new ChromeDriver(options));
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Common setup
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppConfig.IMPLICIT_WAIT_SEC));
        driver.manage().window().maximize();
    }

    public static void quitDriver() {
        WebDriver driver = getDriver();
        if (driver != null) {
            try {
                driver.quit();
            } finally {
                TL_DRIVER.remove();
            }
        }
    }
}
