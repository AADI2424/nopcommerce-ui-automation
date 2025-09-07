package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.pages.HomePage;
import com.testautomation.nopcommerce.pages.SearchResultsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class SearchCurrencySteps {

    private final HomePage home = new HomePage();
    private final SearchResultsPage results = new SearchResultsPage();

    @When("I search for {string}")
    public void i_search_for(String text) {
        home.open();
        home.search(text);
    }

    @Then("search results should be shown")
    public void search_results_should_be_shown() {
        Assert.assertTrue(results.hasResults(), "No search results displayed");
    }

    @When("I change currency to {string}")
    public void i_change_currency_to(String currency) {
        home.open();
        home.changeCurrency(currency);
    }

    @Then("product prices should show the symbol {string}")
    public void product_prices_should_show_the_symbol(String symbol) {
        Assert.assertTrue(home.anyProductPriceHasSymbol(symbol),
                "No price with symbol " + symbol + " was found on home");
    }
}
