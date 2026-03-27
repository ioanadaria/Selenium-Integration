package com.qa.tests;

import com.qa.utils.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Demonstrates TestNG dependsOnMethods — tests share a single browser session
 * so login state is preserved across loginTest → verifyDashboard → logoutTest.
 *
 * Uses @BeforeClass/@AfterClass instead of extending BaseTest because the shared
 * driver state between dependent tests would be lost with per-method setup/teardown.
 */
public class DependentTests {

    private WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browserName) {
        driver = DriverFactory.getDriver(browserName);
        driver.manage().window().maximize();
        System.out.println("🟢 Browser started successfully.");
    }

    @Test
    public void loginTest() {
        System.out.println("🔹 Running loginTest...");

        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button.radius")).click();

        String successMessage = driver.findElement(By.id("flash")).getText();
        System.out.println("✅ Login message: " + successMessage);
        Assert.assertTrue(successMessage.contains("You logged into a secure area!"), "Login failed!");
    }

    @Test(dependsOnMethods = "loginTest")
    public void verifyDashboard() {
        System.out.println("🔹 Running verifyDashboard (depends on loginTest)...");

        String header = driver.findElement(By.cssSelector("div.example h2")).getText();
        System.out.println("✅ Dashboard header: " + header);
        Assert.assertEquals(header, "Secure Area", "Dashboard header is incorrect!");
    }

    @Test(dependsOnMethods = "verifyDashboard")
    public void logoutTest() {
        System.out.println("🔹 Running logoutTest (depends on verifyDashboard)...");

        driver.findElement(By.cssSelector("a.button.secondary.radius")).click();
        String logoutMessage = driver.findElement(By.id("flash")).getText();
        System.out.println("✅ Logout message: " + logoutMessage);
        Assert.assertTrue(logoutMessage.contains("You logged out of the secure area!"), "Logout failed!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("🛑 Browser closed.");
        }
    }
}
