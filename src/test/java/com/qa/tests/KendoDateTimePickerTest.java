package com.qa.tests;

import com.qa.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Demonstrates interacting with a third-party Kendo UI DateTimePicker widget.
 * Uses @BeforeClass/@AfterClass to keep the WebDriverWait reference in sync
 * with the single driver instance used across setup and test.
 */
public class KendoDateTimePickerTest {

    private static final Logger log = LoggerFactory.getLogger(KendoDateTimePickerTest.class);

    private WebDriver driver;
    private WebDriverWait wait;

    @Parameters("browser")
    @BeforeClass
    public void setup(@Optional("chrome") String browserName) {
        log.info("Initializing WebDriver...");
        driver = DriverFactory.getDriver(browserName);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://demos.telerik.com/kendo-ui/datetimepicker/index");
        log.info("Opened Kendo DateTimePicker demo page.");

        // Handle cookie banner if present
        try {
            WebElement accept = new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            if (accept != null) accept.click();
            log.info("Cookie banner accepted.");
        } catch (Exception ignored) {}
    }

    @Test
    public void pickDateAndTime() {
        LocalDateTime target = LocalDateTime.of(2025, 11, 15, 10, 0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        String formattedValue = fmt.format(target);

        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(By.id("datetimepicker")));
        if (input == null) return;

        ((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly')", input);

        log.info("Typing value '{}' into DateTimePicker input...", formattedValue);
        input.click();
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.DELETE);
        input.sendKeys(formattedValue);
        input.sendKeys(Keys.TAB);

        String value = input.getAttribute("value");
        log.info("✅ Selected DateTime: {}", value);
    }

    @AfterClass
    public void teardown() {
        log.info("Closing browser...");
        if (driver != null) driver.quit();
        log.info("Browser closed. Test finished.");
    }
}
