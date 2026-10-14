package com.hrm.basePage;

import org.openqa.selenium.WebDriver;

import com.hrm.actionDriver.ActionDriver;
import com.hrm.driver.DriverFactory;

public class BasePage {

    protected WebDriver driver;
    protected ActionDriver actionDriver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();

        if (driver == null) {
            throw new IllegalStateException(
                "WebDriver is not initialized. "
                + "Ensure BaseClass setup runs before creating page objects."
            );
        }

        this.actionDriver = new ActionDriver(driver);
    }
}