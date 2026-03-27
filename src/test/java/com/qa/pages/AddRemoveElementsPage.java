package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for https://the-internet.herokuapp.com/add_remove_elements/
 * Used to demonstrate explicit wait with elementToBeClickable.
 */
public class AddRemoveElementsPage extends BasePage {

    @FindBy(xpath = "//button[text()='Add Element']")
    private WebElement addButton;

    @FindBy(xpath = "//button[text()='Delete']")
    private WebElement deleteButton;

    public AddRemoveElementsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
    }

    public void clickAddElement() {
        waitForClickable(addButton);
        addButton.click();
    }

    public void clickDelete() {
        waitForClickable(deleteButton);
        deleteButton.click();
    }

    public boolean isDeleteButtonDisplayed() {
        try {
            return deleteButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
