package com.hrm.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.hrm.basePage.BasePage;

public class HomePage extends BasePage {

	private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
	private By userDropdown = By.cssSelector(".oxd-userdropdown-tab");

	private By logoutButton = By.xpath("//a[normalize-space()='Logout']");
	private By dashboardMenus = By.className("oxd-main-menu-item--name");
	private By menuHeader = By.className("oxd-topbar-header-breadcrumb-module");

	public boolean isDashboardDisplayed() {

		try {
			return actionDriver.isDisplayed(dashboardHeader);

		} catch (Exception e) {
			return false;
		}
	}

	public String getDashboardTitle() {

		actionDriver.isDisplayed(dashboardHeader);

		return driver.findElement(dashboardHeader).getText();
	}

	public void clickLogout() {
		actionDriver.click(userDropdown);
		actionDriver.click(logoutButton);
	}

	public boolean menuDisplayed() {
		List<WebElement> menus = driver.findElements(dashboardMenus);
		return menus.size() == 12;
	}

	public boolean userProfile() {
		return actionDriver.isDisplayed(userDropdown);
	}

	public boolean menuVerification(String menuValue) {
		actionDriver.selectValueFromList(dashboardMenus, menuValue);

		if (!actionDriver.isDisplayed(menuHeader)) {
			return false;
		}

		String menuText = driver.findElement(menuHeader).getText();

		return (menuText.equalsIgnoreCase(menuValue));

	}

}
