package com.qa.tests;

import com.qa.pages.AddRemoveElementsPage;
import com.qa.pages.DynamicControlsPage;
import com.qa.pages.DynamicLoadingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Objects;

/**
 * Demonstrates all three Selenium wait strategies:
 *   - Explicit wait (WebDriverWait + ExpectedConditions)
 *   - Fluent wait (polling interval + ignored exceptions)
 *   - Implicit wait (global timeout applied to every findElement)
 */
public class WaitsTest extends BaseTest {

    /**
     * Explicit wait — waits for "Hello World!" text to appear after clicking Start.
     * Source: ConditionalWaitExample / Wait_Selenium
     */
    @Test
    public void explicitWaitForDynamicContent() {
        DynamicLoadingPage page = new DynamicLoadingPage(driver);
        page.open(1);
        page.clickStart();
        Assert.assertEquals(page.getFinishText(), "Hello World!");
    }

    /**
     * Explicit wait — waits for Add/Delete buttons to be clickable before interacting.
     * Source: Wait_Selenium
     */
    @Test
    public void explicitWaitForAddRemoveElements() {
        AddRemoveElementsPage page = new AddRemoveElementsPage(driver);
        page.open();
        page.clickAddElement();
        Assert.assertTrue(page.isDeleteButtonDisplayed(), "Delete button should appear after adding an element");
        page.clickDelete();
        Assert.assertFalse(page.isDeleteButtonDisplayed(), "Delete button should disappear after deletion");
    }

    /**
     * Fluent wait — polls every second (up to 15s) while ignoring NoSuchElementException.
     * Source: FluentWaitExample
     */
    @Test
    public void fluentWaitForElementVisibility() {
        DynamicControlsPage page = new DynamicControlsPage(driver);
        page.open();
        page.clickRemove();

        Wait<org.openqa.selenium.WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);

        WebElement message = Objects.requireNonNull(
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message"))),
                "Message element should be present after removal");
        Assert.assertFalse(message.getText().isEmpty(), "Status message should be visible after removal");
    }

    /**
     * Implicit wait — global timeout makes findElement retry until the element appears.
     * Source: ImplicitWaitExample
     */
    @Test
    public void implicitWaitForDynamicElement() {
        driver.manage().timeouts().implicitlyWait(Objects.requireNonNull(Duration.ofSeconds(10)));
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        driver.findElement(By.xpath("//button[text()='Remove']")).click();
        WebElement message = driver.findElement(By.id("message"));
        Assert.assertFalse(message.getText().isEmpty(), "Status message should be present after removal");
    }
}
