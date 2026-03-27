package com.qa.tests;

import com.qa.pages.LoginPage;
import com.qa.pages.SecureAreaPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test scenarios for the Login page on https://the-internet.herokuapp.com/login
 *
 * Valid credentials:
 *   Username: tomsmith
 *   Password: SuperSecretPassword!
 */
public class LoginTest extends BaseTest {

    @Test(description = "Valid credentials redirect to the secure area")
    public void validLoginShowsSecureArea() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        SecureAreaPage secureArea = loginPage.loginAs("tomsmith", "SuperSecretPassword!");

        Assert.assertTrue(
            secureArea.getFlashMessage().contains("You logged into a secure area"),
            "Expected success flash message was not shown"
        );
        Assert.assertTrue(
            secureArea.isLogoutButtonDisplayed(),
            "Logout button should be visible after a successful login"
        );
    }

    @Test(description = "Wrong password shows an error message")
    public void invalidPasswordShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.loginAs("tomsmith", "wrongpassword");

        Assert.assertTrue(
            loginPage.getFlashMessage().contains("Your password is invalid"),
            "Expected invalid-password error message was not shown"
        );
    }

    @Test(description = "Wrong username shows an error message")
    public void invalidUsernameShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        loginPage.loginAs("wronguser", "SuperSecretPassword!");

        Assert.assertTrue(
            loginPage.getFlashMessage().contains("Your username is invalid"),
            "Expected invalid-username error message was not shown"
        );
    }

    @Test(description = "Logout from secure area returns to the login page")
    public void logoutReturnsToLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();

        SecureAreaPage secureArea = loginPage.loginAs("tomsmith", "SuperSecretPassword!");
        LoginPage returnedLoginPage = secureArea.logout();

        Assert.assertTrue(
            returnedLoginPage.getFlashMessage().contains("You logged out of the secure area"),
            "Expected logged-out flash message was not shown"
        );
    }
}
