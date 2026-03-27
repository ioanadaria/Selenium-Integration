package com.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base class for all Page Objects.
 * Contains shared utilities like waiting for elements to appear.
 * Every page class extends this so they all get these helpers for free.
 */
public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void waitForVisible(WebElement element) {
        if (element == null) throw new IllegalArgumentException("Element must not be null");
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickable(WebElement element) {
        if (element == null) throw new IllegalArgumentException("Element must not be null");
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
