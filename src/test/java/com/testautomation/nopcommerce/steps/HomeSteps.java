package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.config.AppConfig;  
import com.testautomation.nopcommerce.core.DriverManager;
import com.testautomation.nopcommerce.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class HomeSteps {

    private final WebDriver driver = DriverManager.getDriver();
    private final HomePage homePage = new HomePage();

    @Given("I open the demo store")
    public void i_open_the_demo_store() {
        homePage.open().waitUntilLoaded();   // <-- use the no-arg open()
    }

    @Then("the page title should contain {string}")
    public void the_page_title_should_contain(String expected) {
        String title = homePage.getTitle();
        Assert.assertTrue(title.contains(expected),
                "Expected title contain: " + expected + " but got: " + title);
    }

    @Then("the search box is visible")
    public void the_search_box_is_visible() {
        Assert.assertTrue(homePage.isSearchBoxVisible(), "Search box should be visible");
    }
}
