package com.testautomation.nopcommerce.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

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
        if (!"chrome".equals(browser)) {
            throw new IllegalArgumentException("Unsupported browser: " + browser + " (only 'chrome' supported here)");
        }

        // Ensure matching driver is available (Selenium Manager could also do this, but WDM is fine)
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.setAcceptInsecureCerts(true);

        // Headless setup (Jenkins): mvn ... -Dheadless=true
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        // Robust defaults (harmless on Windows)
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-extensions");
        // Not usually needed on current Chrome/Driver but kept for safety with some corp images
        options.addArguments("--remote-allow-origins=*");

        // Make sure we can actually find the Chrome binary (especially when Jenkins runs as a service)
        Path chromeBin = resolveChromeBinary();
        if (chromeBin != null) {
            options.setBinary(chromeBin.toFile());
        } else {
            // Give a crystal clear error if Chrome is missing
            throw new IllegalStateException(
                "Google Chrome binary not found. Install Chrome for all users or pass -Dchrome.binary=C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe");
        }

        // Log versions once for easier debugging
        logChromeVersion(chromeBin);
        logJavaMaven();

        // Start driver (retry once if the port bind stalls)
        WebDriver driver;
        try {
            driver = new ChromeDriver(options);
        } catch (WebDriverException first) {
            sleep(2000);
            driver = new ChromeDriver(options);
        }
        TL_DRIVER.set(driver);

        // Zero implicit wait; use explicit waits
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(AppConfig.PAGELOAD_TIMEOUT_SEC));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        if (!headless) {
            try { driver.manage().window().maximize(); } catch (Exception ignored) {}
        }
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

    // ----- helpers -----

    private static Path resolveChromeBinary() {
        // Allow explicit override
        String explicit = System.getProperty("chrome.binary");
        if (explicit == null) explicit = System.getenv("CHROME_BINARY");
        if (explicit != null) {
            Path p = Path.of(explicit);
            if (Files.exists(p)) return p;
        }

        // Common Windows + Linux locations
        List<String> candidates = Arrays.asList(
            System.getenv("ProgramFiles") + "\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe",
            System.getenv("ProgramFiles(x86)") + "\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe",
            System.getProperty("user.home") + "\\\\AppData\\\\Local\\\\Google\\\\Chrome\\\\Application\\\\chrome.exe",
            "/usr/bin/google-chrome",
            "/usr/bin/chromium",
            "/snap/bin/chromium"
        );

        for (String c : candidates) {
            if (c != null) {
                Path p = Path.of(c);
                if (Files.exists(p)) return p;
            }
        }
        return null;
    }

    private static void logChromeVersion(Path chromeBin) {
        try {
            Process p = new ProcessBuilder(chromeBin.toString(), "--version")
                    .redirectErrorStream(true)
                    .start();
            p.waitFor();
            String out = new String(p.getInputStream().readAllBytes());
            System.out.println("[DriverManager] " + out.trim());
        } catch (IOException | InterruptedException ignored) { }
    }

    private static void logJavaMaven() {
        try {
            execAndPrint("java", "-version");
            execAndPrint("mvn", "-v");
        } catch (Exception ignored) { }
    }

    private static void execAndPrint(String... cmd) throws IOException, InterruptedException {
        Process p = new ProcessBuilder(cmd)
                .redirectErrorStream(true)
                .start();
        p.waitFor();
        String out = new String(p.getInputStream().readAllBytes());
        System.out.println("[DriverManager] " + out.trim());
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { }
    }
}
