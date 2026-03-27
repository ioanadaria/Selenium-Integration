package com.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Creates WebDriver instances for the requested browser.
 * Selenium Manager (built into Selenium 4) automatically downloads
 * the correct browser driver — no manual driver setup needed.
 */
public class DriverFactory {

    public static WebDriver getDriver(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            return new FirefoxDriver();
        }
        throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }
}
