package com.qa.tests;

import com.qa.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

/**
 * Base class for all test classes.
 *
 * @BeforeMethod runs before every single test — opens a fresh browser.
 * @AfterMethod runs after every single test — closes the browser.
 *
 * Every test class extends this so they all get setup/teardown for free.
 */
public class BaseTest {

    protected WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void setUp(String browserName) {
        driver = DriverFactory.getDriver(browserName);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
