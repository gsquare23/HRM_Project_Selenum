package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.hrm.actionDriver.ActionDriver;
import com.hrm.baseClass.BaseClass;
import com.hrm.driver.DriverFactory;
import com.hrm.utilities.WaitUtils;

public class LoginPage {

	
	private WebDriver driver;
	private WaitUtils waitUtils;
	private ActionDriver actionDriver;
	
	// Locators 
	private By username = By.name("username");
	private By password = By.name("password");
	private By loginButton = By.xpath("//button[@type = 'submit']");
	private By errorMsg = By.xpath("//p[text()= 'Invalid credentials']");
	private By requiredError = By.xpath("//span[text()= 'Required']");
	private By loginText = By.xpath("//h5[text()= 'Login']");
		
	
	public LoginPage() {
		
		this.driver = DriverFactory.getDriver();
		
		this.waitUtils = new WaitUtils(driver);
		
		this.actionDriver = new ActionDriver(driver);
	}
	
	public void enterUserName(String usernameValue) {
		actionDriver.enterText(username, usernameValue);
	}
	
	
	public void enterPassword(String passwordValue) {
		actionDriver.enterText(password, passwordValue);
	}
	
	
	public void login(String enterUsername , String enterPassword) {
		enterUserName(enterUsername);
		enterPassword(enterPassword);
		actionDriver.click(loginButton);
	}
	
	
	public boolean isErrorMsgVisible() {
		return actionDriver.isDisplayed(errorMsg);
	}
	
	public boolean isRequiredErrorMsgVisible() {
		return actionDriver.isDisplayed(requiredError);
	}
	
	
	public boolean isPasswordMasked() {
		actionDriver.isDisplayed(password);
		
		String flag = driver.findElement(password).getAttribute("type");
		
		if(flag.equals("password")) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public boolean isLoginHeadingVisible() {
		return actionDriver.isDisplayed(loginText);
	}
	
	public void navigateBack() {
		actionDriver.navigateBack();
	}
}

