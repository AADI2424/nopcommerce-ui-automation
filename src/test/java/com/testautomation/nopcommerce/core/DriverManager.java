package com.testautomation.nopcommerce.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/** ThreadLocal-backed driver lifecycle. */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> TL_DRIVER = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        return TL_DRIVER.get();
    }

    public static void initDriver() {
        if (getDriver() != null) return;

        String browser = System.getProperty("browser", "chrome").toLowerCase();

        if ("chrome".equals(browser)) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            // Faster/more reliable with our explicit waits
            options.setPageLoadStrategy(PageLoadStrategy.EAGER);

            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                options.addArguments("--headless=new");
                options.addArguments("--window-size=1920,1080");
            }

            options.addArguments("--remote-allow-origins=*");

            TL_DRIVER.set(new ChromeDriver(options));
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        WebDriver driver = getDriver();

        // Use explicit waits everywhere; keep implicit at zero
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);

        // Guard against occasional long first-load stalls
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(AppConfig.PAGELOAD_TIMEOUT_SEC));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

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
