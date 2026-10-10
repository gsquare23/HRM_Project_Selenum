package com.hrm.baseClass;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.hrm.driver.DriverFactory;
import com.hrm.utilities.ConfigReader;

public class BaseClass {

    @BeforeMethod
    public void setUp() {

        ConfigReader.loadProperties();

        DriverFactory.initializeDriver();

        DriverFactory.getDriver()
                .get(ConfigReader.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {

        DriverFactory.quitDriver();
    }
}