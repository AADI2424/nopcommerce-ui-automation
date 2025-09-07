package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;

public class ContactUsPage extends BasePage {

    private static final By FULL_NAME = By.id("FullName");
    private static final By EMAIL = By.id("Email");
    private static final By ENQUIRY = By.id("Enquiry");
    private static final By SUBMIT = By.cssSelector("button.contact-us-button, button[name='send-email']");
    private static final By RESULT = By.cssSelector("div.result, div.topic-block-title");

    public void submit(String name, String email, String enquiry) {
        type(FULL_NAME, name);
        type(EMAIL, email);
        type(ENQUIRY, enquiry);
        click(SUBMIT);
        waitShort();
    }

    public boolean successShown() {
        return textOf(RESULT).toLowerCase().contains("successfully sent");
    }
}
