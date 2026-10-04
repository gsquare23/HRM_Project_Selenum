package com.hrm.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hrm.actionDriver.ActionDriver;
import com.hrm.baseClass.BaseClass;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	
	
	@BeforeMethod
	public void setupPages (Method method) {
		loginPage = new LoginPage();
		homePage = new HomePage();
		
		String testCaseName = method.getName(); 
        System.out.println("Currently executing test case: " + testCaseName);
	}
	
	@Test(description = "Login with valid username and password	--- Positive")
	public void validLoginTest() {
		
		loginPage.login("admin", "admin123");
		
		Assert.assertTrue(
                homePage.isDashboardDisplayed(),
                "Dashboard is not displayed after login");
	}
	
	
	@Test(description = "Login with invalid username --- Negative")
	public void inValidUsername() {
		loginPage.login("invalidUsername", "admin123");
		
		Assert.assertTrue(loginPage.isErrorMsgVisible());
	}
	
	
	@Test(description = "Login with invalid password --- Negative")
	public void inValidPassword() {
		loginPage.login("admin", "inValidPassword");
		
		Assert.assertTrue(loginPage.isErrorMsgVisible());
	}
	

	@Test(description = "Login with both username and password blank --- Negative")
	public void blankUsernameAndBlankPassword() {
		loginPage.login("", "");
		
		Assert.assertTrue(loginPage.isRequiredErrorMsgVisible());
	}
	
	
	@Test(description = "Login with username blank --- Negative")
	public void blankUsernameAndPassword() {
		loginPage.login("", "admin123");
		
		Assert.assertTrue(loginPage.isRequiredErrorMsgVisible());
	}
	
	@Test(description = "Login with password blank --- Negative")
	public void usernameAndBlankPassword() {
		loginPage.login("admin", "");
		
		Assert.assertTrue(loginPage.isRequiredErrorMsgVisible());
	}
	
	
	@Test(description = "Verify password is masked --- UI")
	public void isPasswordMasked() {
		boolean flag = loginPage.isPasswordMasked();
		
		Assert.assertTrue(flag);
	}
	
	
	@Test(description = "Verify user can logout --- Functional")
	public void validLogoutTest() {
		loginPage.login("admin", "admin123");
		
		Assert.assertTrue(
                homePage.isDashboardDisplayed(),
                "Dashboard is not displayed after login");
		
		homePage.clickLogout();
		
		Assert.assertTrue(loginPage.isLoginHeadingVisible());
		
	}
	
	
	
	@Test(description = "Verify user cannot access protected page after logout --- Security/Functional")
	public void verifyProtectedPageAccessAfterLogout() {
		loginPage.login("admin", "admin123");
		
		Assert.assertTrue(
                homePage.isDashboardDisplayed(),
                "Dashboard is not displayed after login");
		
		homePage.clickLogout();
		
		Assert.assertTrue(loginPage.isLoginHeadingVisible());
		
		loginPage.navigateBack();
		
		Assert.assertFalse(
                homePage.isDashboardDisplayed(),
                "Dashboard is displayed after logout");
	}
	
}
