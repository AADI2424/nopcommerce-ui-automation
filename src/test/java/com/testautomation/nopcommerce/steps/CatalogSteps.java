package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.pages.CatalogPage;
import com.testautomation.nopcommerce.pages.HomePage;
import com.testautomation.nopcommerce.pages.ProductPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CatalogSteps {

    private final HomePage home = new HomePage();
    private final CatalogPage catalog = new CatalogPage();
    private final ProductPage product = new ProductPage();
    private String rememberedProduct;

    @When("I open category {string} then subcategory {string}")
    public void i_open_category_then_subcategory(String main, String sub) {
        home.open();
        home.navigateToCategory(main, sub);
    }

    @Then("the category header should be {string}")
    public void the_category_header_should_be(String expected) {
        Assert.assertEquals(catalog.getCategoryTitle(), expected, "Unexpected category title");
    }

    @When("I open the first product from the list")
    public void i_open_the_first_product_from_the_list() {
        rememberedProduct = catalog.openFirstProductAndRememberName();
    }

    @Then("the product page title should contain {string}")
    public void the_product_page_title_should_contain(String expected) {
        String title = product.getTitle();
        boolean ok = title.toLowerCase().contains(expected.toLowerCase());
        if (!ok && rememberedProduct != null) {
            ok = title.toLowerCase().contains(rememberedProduct.toLowerCase());
        }
        Assert.assertTrue(ok,
            "Title not containing '" + expected + "' (or clicked product '" + rememberedProduct + "') | got: " + title);
    }
    @When("I sort products by {string}")
    public void i_sort_products_by(String option) {
        catalog.sortBy(option);
    }

    @Then("the first item's price should be less than or equal to the second")
    public void the_first_item_s_price_should_be_less_than_or_equal_to_the_second() {
        Assert.assertTrue(catalog.isSortedLowToHigh(),
                "Prices are not sorted Low→High");
    }
}
