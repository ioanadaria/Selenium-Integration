package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Objects;

/**
 * Demonstrates three JavaScript-based scroll techniques:
 *   - Scroll to top/bottom using scrollTo()
 *   - Scroll by pixel offset using scrollBy()
 *   - Scroll a specific element into view using scrollIntoView()
 *
 * Source: ScrollPageExample, ScrollByCoordinatesExample, ScrollToElementExample
 */
public class ScrollTest extends BaseTest {

    private static final String LARGE_PAGE = "https://the-internet.herokuapp.com/large";

    /**
     * Scrolls to the bottom of the page, verifies position changed, then scrolls back to top.
     * Source: ScrollPageExample
     */
    @Test
    public void scrollToBottomAndBack() {
        driver.get(LARGE_PAGE);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        long yAfterDown = ((Number) Objects.requireNonNull(js.executeScript("return window.pageYOffset;"))).longValue();
        Assert.assertTrue(yAfterDown > 0, "Page should have scrolled down from top");

        js.executeScript("window.scrollTo(0, 0);");
        long yAfterTop = ((Number) Objects.requireNonNull(js.executeScript("return window.pageYOffset;"))).longValue();
        Assert.assertEquals(yAfterTop, 0L, "Page should be back at the top");
    }

    /**
     * Scrolls down and up by pixel coordinates and verifies the Y position at each step.
     * Source: ScrollByCoordinatesExample
     */
    @Test
    public void scrollByCoordinates() {
        driver.get(LARGE_PAGE);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0, 500);");
        long y1 = ((Number) Objects.requireNonNull(js.executeScript("return window.pageYOffset;"))).longValue();
        Assert.assertTrue(y1 > 0, "Page should have scrolled down after first scrollBy");

        js.executeScript("window.scrollBy(0, 700);");
        long y2 = ((Number) Objects.requireNonNull(js.executeScript("return window.pageYOffset;"))).longValue();
        Assert.assertTrue(y2 > y1, "Page should be further down after second scrollBy");

        js.executeScript("window.scrollBy(0, -400);");
        long y3 = ((Number) Objects.requireNonNull(js.executeScript("return window.pageYOffset;"))).longValue();
        Assert.assertTrue(y3 < y2, "Page should scroll up after negative scrollBy");
    }

    /**
     * Finds a deep table row and scrolls it into the viewport using scrollIntoView().
     * Source: ScrollToElementExample
     */
    @Test
    public void scrollToElement() {
        driver.get(LARGE_PAGE);
        WebElement target = driver.findElement(By.xpath("//table/tbody/tr[50]/td[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", target);
        Assert.assertTrue(target.isDisplayed(), "Target element should be visible after scrollIntoView");
    }
}
