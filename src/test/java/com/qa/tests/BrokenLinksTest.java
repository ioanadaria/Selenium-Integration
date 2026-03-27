package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Checks every link on the-internet homepage for HTTP 400+ responses.
 * Extends BaseTest — runs on both Chrome and Firefox in parallel.
 */
public class BrokenLinksTest extends BaseTest {

    @Test
    public void verifyBrokenLinks() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        driver.get("https://the-internet.herokuapp.com/");

        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("🔗 Found " + links.size() + " links on the page.");

        int brokenCount = 0;
        List<String> brokenUrls = new ArrayList<>();

        for (WebElement link : links) {
            String url = link.getAttribute("href");

            if (url == null || url.isEmpty() || !url.startsWith("http")) {
                System.out.println("⚠️ Skipping invalid or empty link: " + url);
                continue;
            }

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .timeout(Duration.ofSeconds(10))
                        .build();

                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                int statusCode = response.statusCode();

                if (statusCode >= 400) {
                    System.out.println("❌ Broken link: " + url + " (Status: " + statusCode + ")");
                    brokenCount++;
                    brokenUrls.add(url);
                } else {
                    System.out.println("✅ Valid link: " + url + " (Status: " + statusCode + ")");
                }

            } catch (IOException | InterruptedException | IllegalArgumentException e) {
                System.out.println("⚠️ Error checking link: " + url + " → " + e.getMessage());
                brokenCount++;
                brokenUrls.add(url);
            }
        }

        System.out.println("===================================");
        System.out.println("Total broken links found: " + brokenCount);
        if (!brokenUrls.isEmpty()) {
            System.out.println("Broken URLs:");
            brokenUrls.forEach(System.out::println);
        }
        System.out.println("===================================");

        Assert.assertEquals(brokenCount, 0, "❌ Some links are broken on the page!");
    }
}
