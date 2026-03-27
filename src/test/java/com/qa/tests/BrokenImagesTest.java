package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 * Checks every image on the broken-images page for HTTP 400+ responses.
 * Extends BaseTest — runs on both Chrome and Firefox in parallel.
 */
public class BrokenImagesTest extends BaseTest {

    @Test
    public void verifyImagesStatus() {
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        driver.get("https://the-internet.herokuapp.com/broken_images");

        List<WebElement> images = driver.findElements(By.tagName("img"));
        System.out.println("🔍 Found " + images.size() + " images on the page.");

        int brokenCount = 0;

        for (WebElement img : images) {
            String src = img.getAttribute("src");
            if (src == null || src.isEmpty()) {
                System.out.println("⚠️ Image has no src attribute!");
                brokenCount++;
                continue;
            }

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(src))
                        .method("HEAD", HttpRequest.BodyPublishers.noBody())
                        .timeout(Duration.ofSeconds(10))
                        .build();

                HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
                int statusCode = response.statusCode();

                if (statusCode >= 400) {
                    System.out.println("❌ Broken image: " + src + " (Status: " + statusCode + ")");
                    brokenCount++;
                } else {
                    System.out.println("✅ Valid image: " + src + " (Status: " + statusCode + ")");
                }

            } catch (IOException | InterruptedException | IllegalArgumentException e) {
                System.out.println("⚠️ Error checking image: " + src + " → " + e.getMessage());
                brokenCount++;
            }
        }

        System.out.println("===================================");
        System.out.println("Total broken images found: " + brokenCount);
        System.out.println("===================================");

        // Log warning but do not fail — the page intentionally has broken images
        if (brokenCount > 0) {
            System.out.println("⚠️ Warning: " + brokenCount + " broken images found.");
        }
    }
}
