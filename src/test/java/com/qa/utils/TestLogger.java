package com.qa.utils;

import org.testng.Reporter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestLogger {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void info(String message) {
        log("🟢 INFO", message);
    }

    public static void pass(String message) {
        log("✅ PASS", message);
    }

    public static void fail(String message) {
        log("❌ FAIL", message);
    }

    private static void log(String level, String message) {
        String formatted = "[" + timestamp() + "] " + level + ": " + message;
        System.out.println(formatted);
        Reporter.log(formatted + "<br>");
    }

    private static String timestamp() {
        return LocalDateTime.now().format(FORMATTER);
    }
}
