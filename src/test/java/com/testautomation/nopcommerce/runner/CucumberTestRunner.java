package com.testautomation.nopcommerce.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * Cucumber test runner executed via TestNG (see testng.xml).
 * Generates HTML and JSON reports under target/.
 */
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.testautomation.nopcommerce.steps","com.testautomation.nopcommerce.hooks"},
        plugin = {"pretty","html:target/cucumber-report.html","json:target/cucumber.json"},
        monochrome = true
)
public class CucumberTestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
