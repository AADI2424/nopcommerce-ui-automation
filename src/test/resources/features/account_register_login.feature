@regression
Feature: Register and Login

  Scenario: Register a new user then login back
    When I register a new account and login back
    Then My account link should be visible
