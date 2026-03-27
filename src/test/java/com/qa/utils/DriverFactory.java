package com.qa.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * Creates WebDriver instances for the requested browser.
 * Selenium Manager (built into Selenium 4) automatically downloads
 * the correct browser driver — no manual driver setup needed.
 */
public class DriverFactory {

    public static WebDriver getDriver(String browserName) {
        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setAcceptInsecureCerts(true);
            return new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.setAcceptInsecureCerts(true);
            return new FirefoxDriver(options);
        }
        throw new IllegalArgumentException("Unsupported browser: " + browserName);
    }
}
