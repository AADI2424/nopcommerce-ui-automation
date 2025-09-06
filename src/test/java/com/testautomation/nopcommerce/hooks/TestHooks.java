package com.testautomation.nopcommerce.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import com.testautomation.nopcommerce.core.DriverManager;

/**
 * Test hooks: create/quit WebDriver before/after each scenario.
 */
public class TestHooks {

    @Before
    public void setUp() {
        WebDriver driver = DriverManager.getDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
