package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.core.DataFactory;
import com.testautomation.nopcommerce.pages.ContactUsPage;
import com.testautomation.nopcommerce.pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class NewsletterContactSteps {

    private final HomePage home = new HomePage();
    private final ContactUsPage contact = new ContactUsPage();

    @When("I subscribe to newsletter with a random email")
    public void i_subscribe_to_newsletter_with_a_random_email() {
        home.open();
        home.subscribeNewsletter(DataFactory.randomEmail());
    }

    @Then("a newsletter success message should appear")
    public void a_newsletter_success_message_should_appear() {
        Assert.assertTrue(home.newsletterSuccessShown(),
                "Newsletter success message not shown");
    }

    @When("I open Contact us and submit an enquiry")
    public void i_open_contact_us_and_submit_an_enquiry() {
        home.open();
        home.openContactUs();
        contact.submit(DataFactory.randomName(), DataFactory.randomEmail(),
                "Automated enquiry message for verification.");
    }

    @Then("a contact success message should appear")
    public void a_contact_success_message_should_appear() {
        Assert.assertTrue(contact.successShown(), "Contact-us success not shown");
    }
}
