package com.qa.tests;

import com.qa.utils.CustomWebDriverListener;
import com.qa.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Demonstrates Selenium 4 EventFiringDecorator + WebDriverListener.
 * The listener logs before/after navigation and click events to the console.
 *
 * Uses @BeforeClass/@AfterClass to wrap the driver with the decorator once
 * for the entire test class.
 */
public class ListenerDemoTest {

    private WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browserName) {
        WebDriver baseDriver = DriverFactory.getDriver(browserName);
        CustomWebDriverListener listener = new CustomWebDriverListener();
        driver = new EventFiringDecorator<WebDriver>(listener).decorate(baseDriver);
        driver.manage().window().maximize();
        System.out.println("🟢 WebDriver with listener initialized.");
    }

    @Test
    public void testLoginFlow() {
        driver.get("https://the-internet.herokuapp.com/login");

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button.radius")).click();

        String message = driver.findElement(By.id("flash")).getText();
        Assert.assertTrue(message.contains("You logged into a secure area!"), "Login failed!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Browser closed.");
        }
    }
}
