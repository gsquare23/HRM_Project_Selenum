package com.hrm.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.hrm.baseClass.BaseClass;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;
import com.hrm.testUtilities.DataProviders;
import com.hrm.utilities.ConfigReader;
import com.hrm.utilities.ExtentManager;

public class LoginPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages(Method method) {

		loginPage = new LoginPage();
		homePage = new HomePage();

		System.out.println("Currently executing test case: " + method.getName() + " | Thread: "
				+ Thread.currentThread().getName());

	}

	@Test(description = "Login with valid username and password --- Positive", dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void validLoginTest(String username, String password) {

		ExtentManager.getTest().info("Starting valid login test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

//		String username = ConfigReader.getProperty("username");
//		String password = ConfigReader.getProperty("password");

		ExtentManager.getTest().info("Entering valid login credentials");

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted with configured credentials");

		boolean dashboardDisplayed = homePage.isDashboardDisplayed();

		Assert.assertTrue(dashboardDisplayed, "Dashboard is not displayed after successful login");

		ExtentManager.getTest().pass("Valid login successful. Dashboard is displayed");
	}

	@Test(description = "Login with invalid username --- Negative", dataProvider = "inValidUsername", dataProviderClass = DataProviders.class)
	public void inValidUsername(String username, String password) {

		ExtentManager.getTest().info("Starting invalid username test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		System.out.println(Thread.currentThread().getName() + " | " + Thread.currentThread().getId());

		ExtentManager.getTest().info("Entering invalid username");

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted");

		Assert.assertTrue(loginPage.isErrorMsgVisible(), "Expected error message was not displayed");

		Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials", "Unexpected error message");

		ExtentManager.getTest().pass("Error message displayed for invalid username");
	}

	@Test(description = "Login with invalid password --- Negative", dataProvider = "inValidPassword", dataProviderClass = DataProviders.class)

	public void inValidPassword(String username, String password) {

		ExtentManager.getTest().info("Starting invalid password test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		System.out.println(Thread.currentThread().getName() + " | " + Thread.currentThread().getId());

		ExtentManager.getTest().info("Entering valid username and invalid password");

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted");

		boolean errorDisplayed = loginPage.isErrorMsgVisible();

		Assert.assertTrue(errorDisplayed, "Expected error message was not displayed for invalid password");

		ExtentManager.getTest().pass("Error message displayed for invalid password");
	}

	@Test(description = "Login with both username and password blank --- Negative", dataProvider = "blankUsernameAndBlankPassword", dataProviderClass = DataProviders.class)
	public void blankUsernameAndBlankPassword(String username, String password) {

		ExtentManager.getTest().info("Starting blank credentials test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted with both fields blank");

		boolean requiredMessageDisplayed = loginPage.isRequiredErrorMsgVisible();

		Assert.assertTrue(requiredMessageDisplayed,
				"Required validation messages were not displayed for blank credentials");

		ExtentManager.getTest().pass("Required validation displayed for blank credentials");
	}

	@Test(description = "Login with username blank --- Negative", dataProvider = "blankUsernameAndPassword", dataProviderClass = DataProviders.class)
	public void blankUsernameAndPassword(String username, String password) {

		ExtentManager.getTest().info("Starting blank username test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted with username blank");

		boolean requiredMessageDisplayed = loginPage.isRequiredErrorMsgVisible();

		Assert.assertTrue(requiredMessageDisplayed, "Username required validation message was not displayed");

		ExtentManager.getTest().pass("Username required validation displayed");
	}

	@Test(description = "Login with password blank --- Negative", dataProvider = "usernameAndBlankPassword", dataProviderClass = DataProviders.class)
	public void usernameAndBlankPassword(String username, String password) {

		ExtentManager.getTest().info("Starting blank password test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted with password blank");

		boolean requiredMessageDisplayed = loginPage.isRequiredErrorMsgVisible();

		Assert.assertTrue(requiredMessageDisplayed, "Password required validation message was not displayed");

		ExtentManager.getTest().pass("Password required validation displayed");
	}

	@Test(description = "Verify password is masked --- UI")
	public void isPasswordMasked() {

		ExtentManager.getTest().info("Starting password masking test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		boolean passwordMasked = loginPage.isPasswordMasked();

		ExtentManager.getTest().info("Password field masking status: " + passwordMasked);

		Assert.assertTrue(passwordMasked, "Password field is not masked");

		ExtentManager.getTest().pass("Password field is masked correctly");
	}

	@Test(description = "Verify user can logout --- Functional", dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void validLogoutTest(String username, String password) {

		ExtentManager.getTest().info("Starting logout test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		ExtentManager.getTest().info("Entering valid login credentials");

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted");

		Assert.assertTrue(homePage.isDashboardDisplayed(), "Dashboard is not displayed after login");

		ExtentManager.getTest().pass("Login successful and dashboard displayed");

		ExtentManager.getTest().info("Clicking logout");

		homePage.clickLogout();

		boolean loginHeadingDisplayed = loginPage.isLoginHeadingVisible();

		Assert.assertTrue(loginHeadingDisplayed, "Login page is not displayed after logout");

		ExtentManager.getTest().pass("Logout successful and login page displayed");
	}

	@Test(description = "Verify user cannot access protected page after logout --- Security/Functional", dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyProtectedPageAccessAfterLogout(String username, String password) {

		ExtentManager.getTest().info("Starting protected page access test");

		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		ExtentManager.getTest().info("Entering valid login credentials");

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted");

		Assert.assertTrue(homePage.isDashboardDisplayed(), "Dashboard is not displayed after login");

		ExtentManager.getTest().pass("Dashboard displayed after successful login");

		ExtentManager.getTest().info("Logging out");

		homePage.clickLogout();

		Assert.assertTrue(loginPage.isLoginHeadingVisible(), "Login page is not displayed after logout");

		ExtentManager.getTest().info("Logout successful. Attempting browser back navigation");

		loginPage.navigateBack();

		ExtentManager.getTest().info("Refreshing page ");

		loginPage.pageRefresh();

		boolean dashboardDisplayed = homePage.isDashboardDisplayed();

		Assert.assertFalse(dashboardDisplayed,
				"Dashboard is displayed after logout and refres; protected page may be accessible");

		ExtentManager.getTest().pass("Protected dashboard was not accessible after logout");
	}
}