@regression
Feature: Footer links

  Scenario: Open Privacy notice page
    When I open footer link "Privacy notice"
    Then the page title should contain footer text "Privacy"

  Scenario: Open Sitemap page
    When I open footer link "Sitemap"
    Then the page title should contain footer text "Sitemap"
