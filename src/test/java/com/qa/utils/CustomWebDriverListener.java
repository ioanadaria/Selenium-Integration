package com.qa.utils;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;

public class CustomWebDriverListener implements WebDriverListener {

    @Override
    public void beforeGet(WebDriver driver, String url) {
        System.out.println("➡️ Navigating to: " + url);
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        System.out.println("✅ Finished navigating to: " + url);
    }

    @Override
    public void beforeClick(WebElement element) {
        try {
            System.out.println("🖱️ About to click on element: <" + element.getTagName() + "> with text '" + element.getText() + "'");
        } catch (StaleElementReferenceException e) {
            System.out.println("⚠️ Element went stale before click.");
        }
    }

    @Override
    public void afterClick(WebElement element) {
        System.out.println("✅ Clicked on element successfully.");
    }

    @Override
    public void onError(Object target, java.lang.reflect.Method method, Object[] args, InvocationTargetException e) {
        System.out.println("❌ Exception occurred: " + e.getCause().getMessage());
    }
}
