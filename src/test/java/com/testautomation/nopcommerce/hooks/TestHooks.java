package com.testautomation.nopcommerce.hooks;

import com.testautomation.nopcommerce.core.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TestHooks {

    @Before
    public void start() {
        DriverManager.initDriver();
    }

    @After
    public void stop(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (scenario.isFailed() && driver instanceof TakesScreenshot) {
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(png, "image/png", "failed_screenshot");
        }
        DriverManager.quitDriver();
    }
}
