package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hrm.actionDriver.ActionDriver;
import com.hrm.driver.DriverFactory;
import com.hrm.utilities.WaitUtils;

public class HomePage {
	
	
	private WebDriver driver;
	private WaitUtils waitUtils;
	private ActionDriver actionDriver;
	
	private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
	private By usedIdButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text() = 'Logout']");
	
	public HomePage() {
		this.driver = DriverFactory.getDriver();
		
		this.waitUtils = new WaitUtils(driver);
		this.actionDriver = new ActionDriver(driver);
		
	}
	
	public boolean isDashboardDisplayed() {
		
		try {
			waitUtils.waitForElementVisible(dashboardHeader);

			return driver.findElements(dashboardHeader)
			        .size() > 0;
		} catch (Exception e) {
			return false;
		}
    }

    public String getDashboardTitle() {

        waitUtils.waitForElementVisible(dashboardHeader);

        return driver.findElement(
                dashboardHeader).getText();
    }
    
    
    public void clickLogout() {
    	actionDriver.click(usedIdButton);
    	actionDriver.click(logoutButton);
    }

}
