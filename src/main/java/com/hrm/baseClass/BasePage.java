package com.hrm.baseClass;

import org.openqa.selenium.WebDriver;

import com.hrm.actionDriver.ActionDriver;
import com.hrm.driver.DriverFactory;
import com.hrm.utilities.WaitUtils;

public class BasePage {

    protected WebDriver driver;
    protected ActionDriver actionDriver;
    protected WaitUtils waitUtils;

    public BasePage() {

        this.driver = DriverFactory.getDriver();
        this.actionDriver = new ActionDriver(driver);
        this.waitUtils = new WaitUtils(driver);
    }
}