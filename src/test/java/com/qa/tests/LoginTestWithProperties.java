package com.qa.tests;

import com.qa.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Demonstrates reading test configuration (URL, credentials, browser) from a
 * properties file instead of hardcoding values.
 *
 * Config file: config/login.properties (at project root)
 * Target site: https://practicetestautomation.com/practice-test-login/
 */
public class LoginTestWithProperties {

    private WebDriver driver;
    private final Properties props = new Properties();

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browserName) throws IOException {
        FileInputStream fis = new FileInputStream("config/login.properties");
        props.load(fis);
        driver = DriverFactory.getDriver(browserName);
        driver.manage().window().maximize();
    }

    @SuppressWarnings("null")
    @Test
    public void testLogin() {
        String url      = props.getProperty("url", "");
        String username = props.getProperty("username", "");
        String password = props.getProperty("password", "");

        driver.get(url);

        WebElement userField    = driver.findElement(By.id("username"));
        WebElement passField    = driver.findElement(By.id("password"));
        WebElement loginButton  = driver.findElement(By.id("submit"));

        userField.sendKeys(username);
        passField.sendKeys(password);
        loginButton.click();

        WebElement successMsg = driver.findElement(By.className("post-title"));
        String message = successMsg.getText();
        System.out.println("Login result: " + message);

        Assert.assertTrue(message.contains("Logged In Successfully"), "Login failed!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
