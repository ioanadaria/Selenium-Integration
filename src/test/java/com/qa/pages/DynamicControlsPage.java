package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for https://the-internet.herokuapp.com/dynamic_controls
 * Used to demonstrate fluent wait and implicit wait scenarios.
 */
public class DynamicControlsPage extends BasePage {

    @FindBy(xpath = "//button[text()='Remove']")
    private WebElement removeButton;

    @FindBy(id = "message")
    private WebElement message;

    public DynamicControlsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
    }

    public void clickRemove() {
        waitForClickable(removeButton);
        removeButton.click();
    }

    public String getMessage() {
        waitForVisible(message);
        return message.getText();
    }
}
