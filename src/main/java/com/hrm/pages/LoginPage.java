package com.hrm.pages;

import org.openqa.selenium.By;

import com.hrm.basePage.BasePage;


public class LoginPage extends BasePage {

	
	// Locators 
	private By username = By.name("username");
	private By password = By.name("password");
	private By loginButton = By.xpath("//button[@type = 'submit']");
	private By errorMsg = By.xpath("//p[text()= 'Invalid credentials']");
	private By requiredError = By.xpath("//span[text()= 'Required']");
	private By loginText = By.xpath("//h5[text()= 'Login']");
	
	
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

	    if (!actionDriver.isDisplayed(password)) {
	        return false;
	    }

	    String type = driver.findElement(password)
	            .getAttribute("type");

	    return "password".equalsIgnoreCase(type);
	}
	
	
	public boolean isLoginHeadingVisible() {
		return actionDriver.isDisplayed(loginText);
	}
	
	public void navigateBack() {
		actionDriver.navigateBack();
	}
}

