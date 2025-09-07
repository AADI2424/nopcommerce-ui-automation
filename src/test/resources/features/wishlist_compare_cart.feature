@regression
Feature: Wishlist, Compare and Cart

  Scenario: Add a product to wishlist
    When I add the first notebook to the wishlist
    Then wishlist should contain at least one item

  Scenario: Add a product to compare list
    When I add the first notebook to compare list
    Then compare page should list at least one product

  Scenario: Add a product to cart
    When I add the first notebook to the cart
    Then the shopping cart quantity should be greater than zero

  Scenario: Remove a product from the cart
    When I remove the first item from the cart
    Then the shopping cart should be empty
