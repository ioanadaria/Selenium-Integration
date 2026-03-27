package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for: https://the-internet.herokuapp.com/secure
 * This is the page you land on after a successful login.
 */
public class SecureAreaPage extends BasePage {

    @FindBy(css = "h2")
    private WebElement heading;

    @FindBy(id = "flash")
    private WebElement flashMessage;

    @FindBy(css = "a[href='/logout']")
    private WebElement logoutButton;

    public SecureAreaPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getHeading() {
        waitForVisible(heading);
        return heading.getText();
    }

    public String getFlashMessage() {
        waitForVisible(flashMessage);
        return flashMessage.getText();
    }

    public boolean isLogoutButtonDisplayed() {
        waitForVisible(logoutButton);
        return logoutButton.isDisplayed();
    }

    public LoginPage logout() {
        waitForClickable(logoutButton);
        logoutButton.click();
        return new LoginPage(driver);
    }
}
