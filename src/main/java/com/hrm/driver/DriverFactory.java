
package com.hrm.driver;

import java.util.Locale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import com.hrm.utilities.ConfigReader;

public final class DriverFactory {

    private static final ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    private DriverFactory() {
        // Prevent object creation
    }

    public static void initializeDriver() {

        // Clean up any existing driver on this thread.
        quitDriver();

        String browser = ConfigReader.getProperty("browser");

        if (browser == null || browser.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Browser is not configured in config.properties");
        }

        browser = browser.trim().toLowerCase(Locale.ROOT);

        boolean headless = Boolean.parseBoolean(
                ConfigReader.getProperty("headless"));

        WebDriver webDriver;

        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();

                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                webDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (headless) {
                    firefoxOptions.addArguments("-headless");
                }

                webDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();

                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }

                webDriver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser);
        }

        // Associate this browser with the current thread.
        driver.set(webDriver);

        try {
            if (!headless) {
                webDriver.manage().window().maximize();
            }
        } catch (RuntimeException e) {
            try {
                webDriver.quit();
            } finally {
                driver.remove();
            }

            throw e;
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        WebDriver webDriver = driver.get();

        try {
            if (webDriver != null) {
                webDriver.quit();
            }
        } finally {
            driver.remove();
        }
    }
}