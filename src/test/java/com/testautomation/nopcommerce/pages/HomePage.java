package com.testautomation.nopcommerce.pages;

import com.testautomation.nopcommerce.core.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebDriverException;


public class HomePage extends BasePage {

  private static final By SEARCH_INPUT = By.id("small-searchterms");
  private static final By SEARCH_BUTTON = By.cssSelector("button[type='submit'], button.search-box-button");

  private static final By TOP_MENU = By.cssSelector("ul.top-menu.notmobile");
  private static final By LEFT_SUBCATS = By.cssSelector("div.block.block-category-navigation a");
  private static final By CURRENCY_SELECT = By.id("customerCurrency");

  private static final By PRICE_ANY = By.cssSelector("span.price.actual-price");

  private static final By FOOTER = By.cssSelector("div.footer");
  private static final By FOOTER_LINKS = By.cssSelector("div.footer a");

  private static final By NEWSLETTER_EMAIL = By.id("newsletter-email");
  private static final By NEWSLETTER_SUBMIT = By.id("newsletter-subscribe-button");
  private static final By NEWSLETTER_RESULT = By.id("newsletter-result-block");

  private static final By LOGIN_LINK = By.cssSelector("a.ico-login");
  private static final By REGISTER_LINK = By.cssSelector("a.ico-register");
  private static final By CONTACT_US_LINK = By.linkText("Contact us");

  private static final By CART_LINK = By.linkText("Shopping cart");
  private static final By WISHLIST_LINK = By.linkText("Wishlist");
  private static final By COMPARE_PAGE_LINK = By.linkText("Compare products list");
  private static final By CART_QTY = By.cssSelector("span.cart-qty");

  private static final By COOKIE_OK = By.id("eu-cookie-ok");
//  /** Open base URL with a retry and wait for the real home to load */
  
  public HomePage open() {
	    driver.get(AppConfig.BASE_URL);
	    return waitUntilLoaded();
	}

	public HomePage open(String url) {
	    driver.get(url);
	    return waitUntilLoaded();
	}
 

  // existing search
  public void search(String text) {
    type(SEARCH_INPUT, text);
    click(SEARCH_BUTTON);
  }

  // ===== helpers =====
  public String getTitle() {
    return driver.getTitle();
  }

  public void navigateToCategory(String main, String sub) {
    click(By.xpath("//ul[contains(@class,'top-menu')]//a[normalize-space()='" + main + "']"));
    click(By.xpath("//div[contains(@class,'block-category-navigation')]//a[normalize-space()='" + sub + "']"));
    waitShort();
  }

  public void changeCurrency(String currencyVisibleText) {
	    selectByVisibleText(CURRENCY_SELECT, currencyVisibleText);
	    String symbol = currencyVisibleText.toLowerCase().contains("euro") ? "€"
	                 : currencyVisibleText.toLowerCase().contains("us dollar") ? "$"
	                 : "";
	    if (!symbol.isEmpty()) {
	        wait.until(d -> driver.findElements(PRICE_ANY)
	                .stream().anyMatch(e -> e.getText().contains(symbol)));
	    } else {
	        waitShort();
	    }
	}



  public boolean anyProductPriceHasSymbol(String symbol) {
	    wait.until(d -> !d.findElements(PRICE_ANY).isEmpty());
	    return driver.findElements(PRICE_ANY).stream().anyMatch(e -> e.getText().contains(symbol));
	}


  public void openFooterLink(String linkText) {
	    click(By.xpath("//div[contains(@class,'footer')]//a[normalize-space()='" + linkText + "']"));
	    // Use a strong title wait on the first word (e.g., "Sitemap", "Privacy")
	    String head = linkText.split("\\s+")[0];
	    wait.until(ExpectedConditions.or(
	            ExpectedConditions.titleContains(head),
	            ExpectedConditions.urlContains(head.toLowerCase().replace(' ', '-'))
	    ));
	}


  public void subscribeNewsletter(String email) {
	    ((org.openqa.selenium.JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", $(NEWSLETTER_EMAIL));
	    type(NEWSLETTER_EMAIL, email);
	    click(NEWSLETTER_SUBMIT);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(NEWSLETTER_RESULT));
	}


  public boolean newsletterSuccessShown() {
	    String msg = driver.findElement(NEWSLETTER_RESULT).getText().trim().toLowerCase();
	    return msg.contains("thank") || msg.contains("success") || msg.contains("already");
	}
  public void openLoginPage()    { click(LOGIN_LINK);    waitShort(); }
  public void openRegisterPage() { click(REGISTER_LINK); waitShort(); }
  public void openContactUs()    { click(CONTACT_US_LINK); waitShort(); }
  public void openCart()         { click(CART_LINK);     waitShort(); }
  public void openWishlist()     { click(WISHLIST_LINK); waitShort(); }

  public void openComparePage() {
    if (!driver.findElements(COMPARE_PAGE_LINK).isEmpty()) {
      click(COMPARE_PAGE_LINK);
      waitShort();
    } else {
      driver.get(AppConfig.BASE_URL + "compareproducts"); // no leading slash
      waitShort();
    }
  }

  public int getCartQty() {
    if (driver.findElements(CART_QTY).isEmpty()) return 0;
    String txt = textOf(CART_QTY); // like "(1)"
    String n = txt.replaceAll("[^0-9]", "");
    if (n.isEmpty()) return 0;
    return Integer.parseInt(n);
  }

  /** Wait until the real home page is shown (not the “Just a moment…” page) */
  public HomePage waitUntilLoaded() {
	    try {
	        wait.until(ExpectedConditions.or(
	                ExpectedConditions.titleContains("nopCommerce"),
	                ExpectedConditions.presenceOfElementLocated(SEARCH_INPUT)
	        ));
	        // dismiss cookie bar if it shows up
	        if (!driver.findElements(COOKIE_OK).isEmpty()) {
	            click(COOKIE_OK);
	        }
	    } catch (TimeoutException ignored) { }
	    return this;
	}

  /** Used by step: Then the search box is visible */
  public boolean isSearchBoxVisible() {
    return isVisible(SEARCH_INPUT);
  }
}
