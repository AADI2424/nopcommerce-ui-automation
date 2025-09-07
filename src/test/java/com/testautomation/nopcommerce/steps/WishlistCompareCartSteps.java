package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.pages.*;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class WishlistCompareCartSteps {

    private final HomePage home = new HomePage();
    private final CatalogPage catalog = new CatalogPage();
    private final ProductPage product = new ProductPage();
    private final CompareProductsPage compare = new CompareProductsPage();
    private final WishlistPage wishlist = new WishlistPage();
    private final CartPage cart = new CartPage();

    // helper: always start from Notebooks
    private void goToNotebooks() {
        home.open();
        home.navigateToCategory("Computers", "Notebooks");
    }

    @When("I add the first notebook to the wishlist")
    public void i_add_the_first_notebook_to_the_wishlist() {
        goToNotebooks();
        catalog.openFirstProduct();
        product.addToWishlist();
        home.openWishlist();
    }

    @Then("wishlist should contain at least one item")
    public void wishlist_should_contain_at_least_one_item() {
        Assert.assertTrue(wishlist.itemsCount() >= 1, "Wishlist is empty");
    }

    @When("I add the first notebook to compare list")
    public void i_add_the_first_notebook_to_compare_list() {
        goToNotebooks();
        catalog.openFirstProduct();
        product.addToCompare();
        home.openComparePage();
    }

    @Then("compare page should list at least one product")
    public void compare_page_should_list_at_least_one_product() {
        Assert.assertTrue(compare.itemsCount() >= 1, "Compare list is empty");
    }

    @When("I add the first notebook to the cart")
    public void i_add_the_first_notebook_to_the_cart() {
        goToNotebooks();
        catalog.openFirstProduct();
        product.addToCartIfPossible();
    }

    @Then("the shopping cart quantity should be greater than zero")
    public void the_shopping_cart_quantity_should_be_greater_than_zero() {
        Assert.assertTrue(home.getCartQty() > 0, "Cart qty did not increase");
    }

    @When("I remove the first item from the cart")
    public void i_remove_the_first_item_from_the_cart() {
        home.openCart();
        cart.removeFirstItem();
    }

    @Then("the shopping cart should be empty")
    public void the_shopping_cart_should_be_empty() {
        Assert.assertTrue(cart.isEmpty(), "Cart still has items");
    }
}
