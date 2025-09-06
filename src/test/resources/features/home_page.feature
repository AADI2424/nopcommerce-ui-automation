Feature: Home page smoke
  As a visitor
  I want to open the demo site
  So that I can verify the home page is accessible

  @smoke
  Scenario: Open demo store home page
    Given I open the demo store
    Then the page title should contain "nopCommerce demo store"
    And the search box is visible
