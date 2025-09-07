@regression
Feature: Newsletter & Contact-us

  Scenario: Newsletter subscription with random email
    When I subscribe to newsletter with a random email
    Then a newsletter success message should appear

  Scenario: Submit Contact Us enquiry
    When I open Contact us and submit an enquiry
    Then a contact success message should appear
