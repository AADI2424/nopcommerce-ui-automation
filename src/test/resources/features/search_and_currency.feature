@regression
Feature: Search & Currency

  Scenario: Search shows results
    When I search for "book"
    Then search results should be shown

  Scenario: Change currency to Euro
    When I change currency to "Euro"
    Then product prices should show the symbol "€"
