package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for https://the-internet.herokuapp.com/dynamic_loading/{example}
 * Demonstrates explicit wait — content is hidden and shown after clicking Start.
 */
public class DynamicLoadingPage extends BasePage {

    @FindBy(xpath = "//div[@id='start']/button")
    private WebElement startButton;

    @FindBy(xpath = "//div[@id='finish']/h4")
    private WebElement finishText;

    public DynamicLoadingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open(int example) {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/" + example);
    }

    public void clickStart() {
        waitForClickable(startButton);
        startButton.click();
    }

    public String getFinishText() {
        waitForVisible(finishText);
        return finishText.getText();
    }
}
