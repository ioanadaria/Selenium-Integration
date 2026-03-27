package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for: https://the-internet.herokuapp.com/login
 *
 * This class knows everything about the login page:
 * - Where the fields are (@FindBy locators)
 * - How to interact with them (the methods below)
 *
 * Test classes NEVER touch raw Selenium here — they just call these methods.
 */
public class LoginPage extends BasePage {

    private static final String URL = "https://the-internet.herokuapp.com/login";

    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
    }

    /**
     * Fills in username + password and clicks Login.
     * Returns a SecureAreaPage because that's where a successful login lands.
     */
    public SecureAreaPage loginAs(String username, String password) {
        waitForVisible(usernameField);
        usernameField.clear();
        usernameField.sendKeys(username);
        passwordField.clear();
        passwordField.sendKeys(password);
        waitForClickable(loginButton);
        loginButton.click();
        return new SecureAreaPage(driver);
    }

    public String getFlashMessage() {
        waitForVisible(flashMessage);
        return flashMessage.getText();
    }
}
