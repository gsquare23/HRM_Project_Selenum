package com.hrm.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hrm.actionDriver.ActionDriver;
import com.hrm.baseClass.BasePage;
import com.hrm.driver.DriverFactory;
import com.hrm.utilities.WaitUtils;

public class HomePage extends BasePage  {
	
	
	private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
	private By usedIdButton = By.className("oxd-userdropdown-name");
	private By logoutButton = By.xpath("//a[text() = 'Logout']");
	private By dashboardMenus = By.className("oxd-main-menu-item--name");
	private By menuHeader = By.className("oxd-topbar-header-breadcrumb-module");
	
	
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
    
    
    public boolean menuDisplayed() {
    	List<WebElement> menus= driver.findElements(dashboardMenus);
    	
    	if(menus.size() == 12) {
    		return true;
    	}
    	else{
    		return false;
    	}
    	
    }
    
    
    public boolean userProfile() {
    	return actionDriver.isDisplayed(usedIdButton);
    }
    
    
    public boolean menuVerification(String menuValue) {
    	actionDriver.selectValueFromList(dashboardMenus, menuValue);
    	
    	actionDriver.isDisplayed(menuHeader);
    	
    	String menuText = driver.findElement(menuHeader).getText();
    	
    	if(menuText.equalsIgnoreCase(menuValue)) {
    		return true;
    	}
    	
    	else {
    		return false;
    	}
    }

}
