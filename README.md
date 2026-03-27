# Selenium Test Automation Portfolio

A cross-browser test automation framework built with **Java 25**, **Selenium 4**, **TestNG**, and **Maven**, following the **Page Object Model (POM)** design pattern.

Tests run against [The Internet](https://the-internet.herokuapp.com) and other practice sites, covering a wide range of real-world automation scenarios.

---

## Technologies Used

| Tool | Version | Purpose |
| ---- | ------- | ------- |
| Java | 25 (LTS) | Programming language |
| Selenium | 4.38 | Browser automation |
| TestNG | 7.11 | Test framework |
| Maven | 3.9 | Build & dependency management |
| PDFBox | 2.0 | PDF download and text extraction |

---

## Project Structure

```text
src/test/java/com/qa/
├── pages/
│   ├── BasePage.java                  ← Shared utilities (waits, helpers)
│   ├── LoginPage.java                 ← Login page interactions
│   ├── SecureAreaPage.java            ← Secure area page interactions
│   ├── AddRemoveElementsPage.java     ← Add/Delete element interactions
│   ├── DynamicControlsPage.java       ← Remove button + status message
│   └── DynamicLoadingPage.java        ← Dynamic loading with Start button
├── tests/
│   ├── BaseTest.java                  ← Browser setup/teardown (@BeforeMethod/@AfterMethod)
│   ├── LoginTest.java                 ← POM-based login scenarios
│   ├── AssertionsDemoTest.java        ← Hard and soft assertion examples
│   ├── BrokenLinksTest.java           ← HTTP link checker (Java HttpClient)
│   ├── BrokenImagesTest.java          ← Image src HTTP status checker
│   ├── DependentTests.java            ← Dependent test chain (login → verify → logout)
│   ├── ListenerDemoTest.java          ← WebDriver event listener demo
│   ├── KendoDateTimePickerTest.java   ← Telerik Kendo date-time widget interaction
│   ├── LoginTestWithProperties.java   ← Config-driven login test
│   ├── WaitsTest.java                 ← Explicit, fluent and implicit wait strategies
│   ├── ScrollTest.java                ← scrollTo, scrollBy and scrollIntoView techniques
│   ├── ResizeElementTest.java         ← Actions-based element resize
│   ├── SSLCertificatesTest.java       ← Insecure/expired SSL certificate handling
│   ├── WindowsAuthTest.java           ← Firefox NTLM/basic auth via custom profile
│   └── PDFTextExtractTest.java        ← PDF download and text extraction (no browser)
└── utils/
    ├── DriverFactory.java             ← Creates Chrome/Firefox drivers (SSL certs enabled)
    ├── CustomWebDriverListener.java   ← Logs browser events (navigate, click, errors)
    └── TestLogger.java                ← Timestamped console and TestNG Reporter logging
config/
└── login.properties                   ← Credentials for LoginTestWithProperties
```

---

## Test Scenarios

### Chrome + Firefox (parallel)

| # | Test Class | Scenarios |
| - | ---------- | --------- |
| 1 | `LoginTest` | Valid login, wrong password, wrong username, logout |
| 2 | `AssertionsDemoTest` | Hard assertion on page title; soft assertions demoing intentional mismatches |
| 3 | `BrokenLinksTest` | Checks every link on the homepage for HTTP 400+ responses |
| 4 | `BrokenImagesTest` | Checks every `<img>` src for broken/unreachable images |
| 5 | `WaitsTest` | Explicit wait (dynamic loading), explicit wait (add/remove elements), fluent wait (polling), implicit wait |
| 6 | `ScrollTest` | Scroll to bottom/top, scroll by pixel coordinates, scroll element into view |
| 7 | `ResizeElementTest` | Drag resize handle with Actions API and assert width change |
| 8 | `SSLCertificatesTest` | Page loads with expired SSL cert; browser capability confirms insecure certs accepted |

### Chrome only

| # | Test Class | Scenarios |
| - | ---------- | --------- |
| 9 | `DependentTests` | Chained login → verify dashboard → logout with `dependsOnMethods` |
| 10 | `ListenerDemoTest` | Login flow wrapped with `EventFiringDecorator` + `CustomWebDriverListener` |
| 11 | `KendoDateTimePickerTest` | Date and time selection in a Telerik Kendo widget |
| 12 | `LoginTestWithProperties` | Login using credentials loaded from `config/login.properties` |

### Firefox only

| # | Test Class | Scenarios |
| - | ---------- | --------- |
| 13 | `WindowsAuthTest` | NTLM/basic auth via embedded URL credentials using a custom `FirefoxProfile` |

### Utility (no browser)

| # | Test Class | Scenarios |
| - | ---------- | --------- |
| 14 | `PDFTextExtractTest` | Downloads a sample PDF and verifies extracted text with PDFBox |

**Total: 38 test executions per run** (22 on Chrome, 15 on Firefox, 1 utility)

---

## How to Run

### Prerequisites

- Java 25+
- Maven 3.9+
- Chrome and/or Firefox installed

```bash
# Clone the repo
git clone https://github.com/ioanadaria/Selenium-Integration.git
cd Selenium-Integration

# Run all tests (Chrome + Firefox in parallel)
mvn clean test
```

> No manual driver download needed. Selenium Manager (built into Selenium 4) automatically handles ChromeDriver and GeckoDriver.

---

## Design Pattern: Page Object Model

The **Page Object Model** separates *how to interact with a page* from *what to test*.

```text
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
