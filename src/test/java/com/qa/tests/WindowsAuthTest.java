package com.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Objects;

/**
 * Demonstrates Windows/NTLM authentication handling in Firefox using a FirefoxProfile.
 * Firefox can be configured to automatically supply credentials for trusted URIs,
 * allowing Selenium to bypass the native auth dialog.
 *
 * This test manages its own driver lifecycle as it requires a custom Firefox profile
 * and is not cross-browser compatible.
 *
 * Source: WindowsAuthenticationFirefox
 */
public class WindowsAuthTest {

    private WebDriver driver;
    private static final String TRUSTED_URI = "http://httpbin.org";

    @BeforeMethod
    public void setUp() {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("network.automatic-ntlm-auth.trusted-uris", TRUSTED_URI);
        profile.setPreference("network.negotiate-auth.trusted-uris", TRUSTED_URI);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        options.setAcceptInsecureCerts(true);

        driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void firefoxHandlesBasicAuthViaUrl() {
        // Credentials embedded in the URL bypass the native browser dialog
        driver.get("http://foo:bar@httpbin.org/basic-auth/foo/bar");
        String pageSource = Objects.requireNonNull(driver.getPageSource(), "Page source should not be null");
        Assert.assertTrue(pageSource.contains("\"authenticated\": true"),
                "Response should confirm successful authentication");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
