@regression
Feature: Catalog navigation and product details

  Scenario: Navigate to Notebooks category
    When I open category "Computers" then subcategory "Notebooks"
    Then the category header should be "Notebooks"

  Scenario: Open first product details
    When I open category "Computers" then subcategory "Notebooks"
    And I open the first product from the list
    Then the product page title should contain "inch"

  Scenario: Sort products by price Low to High
    When I open category "Computers" then subcategory "Notebooks"
    And I sort products by "Price: Low to High"
    Then the first item's price should be less than or equal to the second
