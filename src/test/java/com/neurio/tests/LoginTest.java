package com.neurio.tests;

import com.neurio.tests.shared.BasicTest;
import com.neurio.tests.shared.Common;
import com.neurio.tests.shared.LoginPage;
import com.neurio.tests.shared.StringRef;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by Robert T. on 2016-05-16.
 * LoginTest
 */
public class LoginTest extends BasicTest {

    @Test
    public void LoginPageTest() {
        String EMAIL = "email";
        String PASSWORD = "password";
        String ADMIN_LOGIN = "admin@energy-aware.com";
        String ADMIN_PASSWORD = "bonny5_worktable";

        Report("Login Test");

        When("I click on the Support Centre Link");
        LoginPage.clickOnLink(StringRef.SUPPORT_CENTRE);
        Then("I am at the support page");
        Assert.assertTrue(getTitle().contains(StringRef.PORTAL), "Support Link Does Not Work");
        Then("I go back to home page");
        goBack();
        When("I click on the About Link");
        LoginPage.clickOnLink(StringRef.ABOUT);
        Then("I am at the about page");
        Assert.assertTrue(getTitle().contains(StringRef.ABOUT_PAGE_TITLE), "About Link Does Not Work");
        Then("I go back to home page");
        goBack();
        When("I click on the Terms Link");
        LoginPage.clickOnLink(StringRef.TERMS);
        Then("I am at the terms page");
        Assert.assertTrue(getTitle().contains(StringRef.TERMS_PAGE_TITLE), "Terms Link Does Not Work");
        Then("I go back to home page");
        goBack();
        Then("I click on the Privacy Link");
        LoginPage.clickOnLink(StringRef.PRIVACY);
        Then("I am at the privacy page");
        Assert.assertTrue(getTitle().contains(StringRef.PRIVACY_PAGE_TITLE), "Privacy Link Does Not Work");
        Then("I go back to home page");
        goBack();
        When("LoginPage Case - Invalid Email and Incorrect Password");

        LoginPage.enterFields(EMAIL + "@email.com", PASSWORD);

        Then("Check for Error to Appear");
        Assert.assertTrue(Common.assertError(), "No Error Appeared");
        LoginPage.clearFields();

        When("LoginPage Case - Invalid Email and Correct Password");

        LoginPage.enterFields(EMAIL + "@email.com", ADMIN_PASSWORD);

        Then("Check for Error to Appear");
        Assert.assertTrue(Common.assertError(), "No Error Appeared");
        LoginPage.clearFields();

        When("LoginPage Case - Correct Email and Invalid Password");
        LoginPage.enterFields(ADMIN_LOGIN, PASSWORD);

        Then("Check for Error to Appear");
        Assert.assertTrue(Common.assertError(), "No Error Appeared");
        LoginPage.clearFields();

        When("LoginPage Case - Valid Email and Password");
        LoginPage.signIn(ADMIN_LOGIN, ADMIN_PASSWORD);

        Report("Login Test passed!");
    }
}