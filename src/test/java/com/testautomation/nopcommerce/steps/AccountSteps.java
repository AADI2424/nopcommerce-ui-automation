package com.testautomation.nopcommerce.steps;

import com.testautomation.nopcommerce.core.DataFactory;
import com.testautomation.nopcommerce.pages.AccountPage;
import com.testautomation.nopcommerce.pages.HomePage;
import com.testautomation.nopcommerce.pages.LoginPage;
import com.testautomation.nopcommerce.pages.RegisterPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AccountSteps {

    private final HomePage home = new HomePage();
    private final RegisterPage register = new RegisterPage();
    private final LoginPage login = new LoginPage();
    private final AccountPage account = new AccountPage();

    private String email;
    private String password;

    @When("I register a new account and login back")
    public void i_register_a_new_account_and_login_back() {
        email = DataFactory.randomEmail();
        password = "P@ssw0rd!" + DataFactory.randomNumber(1000, 9999);

        home.open();
        home.openRegisterPage();
        register.register("Auto", "User", email, password);

        // logout then login again
        account.logout();
        home.openLoginPage();
        login.login(email, password);
    }

    @Then("My account link should be visible")
    public void my_account_link_should_be_visible() {
        Assert.assertTrue(account.isMyAccountVisible(), "My account link not visible");
    }
}
