# Selenium Test Automation Portfolio

A cross-browser test automation framework built with **Java 25**, **Selenium 4**, **TestNG**, and **Maven**, following the **Page Object Model (POM)** design pattern.

Tests run against [The Internet](https://the-internet.herokuapp.com) — a practice website built specifically for Selenium automation.

---

## Technologies Used

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 25 (LTS) | Programming language |
| Selenium | 4.38 | Browser automation |
| TestNG | 7.11 | Test framework |
| Maven | 3.9 | Build & dependency management |

---

## Project Structure

```
src/test/java/com/qa/
├── pages/
│   ├── BasePage.java        ← Shared utilities (waits, helpers)
│   ├── LoginPage.java       ← Login page interactions
│   └── SecureAreaPage.java  ← Secure area page interactions
├── tests/
│   ├── BaseTest.java        ← Browser setup and teardown
│   └── LoginTest.java       ← Login test scenarios
└── utils/
    └── DriverFactory.java   ← Creates Chrome/Firefox drivers
```

---

## Test Scenarios

| # | Test | Expected Result |
|---|------|----------------|
| 1 | Valid login (correct username + password) | Redirected to secure area with success message |
| 2 | Invalid password | Error message shown on login page |
| 3 | Invalid username | Error message shown on login page |
| 4 | Logout from secure area | Redirected back to login page with logout message |

Each scenario runs on **Chrome** and **Firefox** in parallel → **8 total test executions** per run.

---

## How to Run

**Prerequisites**
- Java 25+
- Maven 3.9+
- Chrome and/or Firefox installed

```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/Selenium-Integration.git
cd Selenium-Integration

# Run all tests (Chrome + Firefox in parallel)
mvn clean test
```

> No manual driver download needed. Selenium Manager (built into Selenium 4) automatically handles ChromeDriver and GeckoDriver.

---

## Design Pattern: Page Object Model

The **Page Object Model** separates *how to interact with a page* from *what to test*.

```
Without POM (bad):              With POM (good):
─────────────────────           ─────────────────────────────────
LoginTest.java                  LoginPage.java   ← HOW to use the page
  driver.findElement(...)         findElement(...)
  element.sendKeys(...)           sendKeys(...)
  button.click()                  button.click()

                                LoginTest.java   ← WHAT to test
                                  loginPage.loginAs("user", "pass")
                                  Assert.assertTrue(...)
```

Benefits:
- **Readable** — tests read like plain English
- **Maintainable** — if a page changes, update only the page class
- **Reusable** — any test can call `loginPage.loginAs(...)` without duplicating code
