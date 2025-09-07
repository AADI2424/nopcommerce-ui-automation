package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class FooterSteps {

    private final HomePage home = new HomePage();

    @When("I open footer link {string}")
    public void i_open_footer_link(String linkText) {
        home.open();
        home.openFooterLink(linkText);
    }

    @Then("the page title should contain footer text {string}")
    public void the_page_title_should_contain_footer_text(String expected) {
        Assert.assertTrue(home.getTitle().toLowerCase().contains(expected.toLowerCase()),
                "Footer page title does not contain: " + expected);
    }
}
