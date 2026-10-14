
package com.hrm.baseClass;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.hrm.driver.DriverFactory;
import com.hrm.utilities.ConfigReader;
import com.hrm.utilities.ScreenshotUtils;

public class BaseClass {

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        ConfigReader.loadProperties();

        DriverFactory.initializeDriver();

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver was not initialized for this test thread."
            );
        }

        driver.get(ConfigReader.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            WebDriver driver = DriverFactory.getDriver();

            if (result.getStatus() == ITestResult.FAILURE
                    && driver != null
                    && result.getAttribute("screenshotPath") == null) {

                String path = ScreenshotUtils.takeScreenshot(
                        driver,
                        result.getName()
                );

                if (path != null) {
                    result.setAttribute("screenshotPath", path);
                }
            }

        } finally {
            DriverFactory.quitDriver();
        }
    }
}
