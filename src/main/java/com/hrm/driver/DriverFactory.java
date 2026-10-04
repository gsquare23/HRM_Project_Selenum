package com.hrm.driver;

import com.hrm.utilities.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver() {

        String browser = ConfigReader.getProperty("browser");
        boolean headless = Boolean.parseBoolean(
                ConfigReader.getProperty("headless"));

        WebDriver webDriver;

        switch (browser.toLowerCase()) {

        case "chrome":

            ChromeOptions chromeOptions = new ChromeOptions();

            if (headless) {
                chromeOptions.addArguments("--headless=new");
            }

            webDriver = new ChromeDriver(chromeOptions);

            break;

        case "firefox":

            webDriver = new FirefoxDriver();

            break;

        case "edge":

            webDriver = new EdgeDriver();

            break;

        default:

            throw new IllegalArgumentException(
                    "Unsupported browser: " + browser);
        }

        driver.set(webDriver);

        // Maximize only when NOT running headless
        if (!headless) {
            getDriver().manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {

        if (driver.get() != null) {

            driver.get().quit();
            driver.remove();
        }
    }
}