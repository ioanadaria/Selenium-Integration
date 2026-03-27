package com.qa.tests;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Verifies that the browser is configured to accept untrusted/expired SSL certificates,
 * and that pages behind invalid certificates load successfully.
 *
 * The driver is created with setAcceptInsecureCerts(true) in DriverFactory,
 * so the test works for both Chrome and Firefox.
 *
 * Source: HandleUntrustedSSLCertificatesChrome, HandleUntrustedSSLCertificatesFirefox
 */
public class SSLCertificatesTest extends BaseTest {

    @Test
    public void pageLoadsWithExpiredSSLCertificate() {
        driver.get("https://expired.badssl.com/");
        String title = driver.getTitle();
        Assert.assertFalse(title == null || title.isEmpty(),
                "Page should load despite an expired SSL certificate");
    }

    @SuppressWarnings("null")
    @Test
    public void browserAcceptsInsecureCertificates() {
        Object capability = ((HasCapabilities) driver).getCapabilities().getCapability(CapabilityType.ACCEPT_INSECURE_CERTS);
        Assert.assertNotNull(capability, "ACCEPT_INSECURE_CERTS capability should be set");
        Assert.assertTrue((boolean) capability,
                "Browser should be configured to accept insecure certificates");
    }
}
