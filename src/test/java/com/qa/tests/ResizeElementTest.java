package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

/**
 * Demonstrates resizing a web element using the Selenium Actions API
 * by dragging its resize handle.
 *
 * Source: ResizeElementExample
 */
public class ResizeElementTest extends BaseTest {

    @Test
    public void resizeElementWithActions() {
        driver.get("https://jqueryui.com/resizable/");

        // The resizable demo is rendered inside an iframe
        driver.switchTo().frame(driver.findElement(By.cssSelector(".demo-frame")));

        WebElement resizableBox = driver.findElement(By.id("resizable"));
        int initialWidth = resizableBox.getSize().getWidth();

        WebElement resizeHandle = resizableBox.findElement(By.cssSelector(".ui-resizable-se"));

        new Actions(Objects.requireNonNull(driver))
                .clickAndHold(resizeHandle)
                .moveByOffset(100, 50)
                .release()
                .perform();

        int newWidth = resizableBox.getSize().getWidth();
        Assert.assertTrue(newWidth > initialWidth,
                "Element width should increase after dragging the resize handle");
    }
}
