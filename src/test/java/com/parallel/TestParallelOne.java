package com.parallel;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestParallelOne {

    WebDriver driver;

    @Parameters("browser")
    @Test
    public void openWebsite(String browserName) throws InterruptedException {
        System.out.println("TestParallelOne thread id: " + Thread.currentThread().threadId());

        if (browserName.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver",
                    "C:\\Selenium_data\\geckodriver-v0.34.0-win64\\geckodriver.exe");
            driver = new FirefoxDriver();
        }

        driver.get("https://example.com/");
        driver.manage().window().maximize();
        System.out.println("Page title: " + driver.getTitle());

        Thread.sleep(2000);
        driver.quit();
    }
}


