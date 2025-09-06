package com.testautomation.nopcommerce.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Home page object for demo.nopcommerce.com
 */
public class HomePage {

    private final WebDriver driver;

    private final By searchBox = By.cssSelector("input#small-searchterms");
    private final By logo = By.cssSelector("div.header-logo a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl);
    }

    public boolean isSearchBoxVisible() {
        WebElement el = driver.findElement(searchBox);
        return el.isDisplayed();
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public boolean isLogoVisible() {
        return driver.findElement(logo).isDisplayed();
    }
    
    
}
