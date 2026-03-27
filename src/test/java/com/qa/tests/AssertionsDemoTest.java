package com.qa.tests;

import com.qa.utils.TestLogger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

/**
 * Demonstrates hard and soft assertions with TestNG.
 * Extends BaseTest — each test method gets a fresh browser via @BeforeMethod.
 * Runs on both Chrome and Firefox in parallel via testng.xml.
 */
public class AssertionsDemoTest extends BaseTest {

    @Test(priority = 1)
    public void hardAssertionTest() {
        TestLogger.info("Running Hard Assertion Test...");

        driver.get("https://the-internet.herokuapp.com/login");
        String actualTitle = driver.getTitle();
        TestLogger.info("Page title fetched: " + actualTitle);

        // HARD ASSERT: stops immediately if fails
        Assert.assertEquals(actualTitle, "The Internet", "Title does not match!");
        TestLogger.pass("Hard assertion passed — Title verified successfully.");

        driver.findElement(By.id("username")).sendKeys("tomsmith");
        TestLogger.pass("Username field is visible and accessible.");
    }

    @Test(priority = 2)
    public void softAssertionTest() {
        TestLogger.info("Running Soft Assertion Test...");
        SoftAssert softAssert = new SoftAssert();

        driver.get("https://the-internet.herokuapp.com/login");

        String pageHeader = driver.findElement(By.tagName("h2")).getText();
        String buttonText = driver.findElement(By.cssSelector("button.radius")).getText();

        // SOFT assertions: will not stop the test — intentional mismatches to demo behavior
        softAssert.assertEquals(pageHeader, "Login Page", "Page header mismatch!");
        if (pageHeader.equals("Login Page")) {
            TestLogger.pass("Page header is correct.");
        } else {
            TestLogger.fail("Page header mismatch! Found: " + pageHeader);
        }

        softAssert.assertEquals(buttonText, "Login123", "Button text mismatch!");
        if (buttonText.equals("Login123")) {
            TestLogger.pass("Button text verified.");
        } else {
            TestLogger.fail("Button text mismatch! Found: " + buttonText);
        }

        TestLogger.info("Continuing test even after failed soft assertions...");

        // Collect and report all soft failures at the end
        softAssert.assertAll();
    }
}
