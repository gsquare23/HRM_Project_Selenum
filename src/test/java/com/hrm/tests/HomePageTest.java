
package com.hrm.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hrm.baseClass.BaseClass;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;
import com.hrm.testUtilities.DataProviders;
import com.hrm.utilities.ExtentManager;

public class HomePageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;

	@BeforeMethod
	public void setupPages(Method method) {

		loginPage = new LoginPage();
		homePage = new HomePage();

		System.out.println("Currently executing test case: " + method.getName() + " | Thread: "
				+ Thread.currentThread().getName());
	}

	/**
	 * Reusable login helper using Excel credentials.
	 */
	private void loginAsUser(String username, String password) {

		ExtentManager.getTest().info("Starting login");

		loginPage.login(username, password);

		ExtentManager.getTest().info("Login form submitted");
	}

	@Test(description = "Verify Dashboard is displayed after successful login", groups = { "Smoke",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyDashboardIsDisplayedAfterLogin(String username, String password) {

		ExtentManager.getTest().info("Verifying dashboard after login");
		ExtentManager.getTest().info("Thread: " + Thread.currentThread().getName());

		loginAsUser(username, password);

		Assert.assertTrue(homePage.isDashboardDisplayed(), "Dashboard is not displayed after successful login");

		ExtentManager.getTest().pass("Dashboard is displayed after successful login");
	}

	@Test(description = "Verify main menu is displayed on Dashboard", groups = { "UI",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyMainMenuIsDisplayed(String username, String password) {

		ExtentManager.getTest().info("Verifying main menu");

		loginAsUser(username, password);

		Assert.assertTrue(homePage.isDashboardDisplayed(), "Dashboard is not displayed after login");

		Assert.assertTrue(homePage.menuDisplayed(), "Main menu is not displayed on the dashboard");

		ExtentManager.getTest().pass("Main menu is displayed on the dashboard");
	}

	@Test(description = "Verify user profile is displayed", groups = { "UI",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyUserProfileIsDisplayed(String username, String password) {

		ExtentManager.getTest().info("Verifying user profile visibility");

		loginAsUser(username, password);

		Assert.assertTrue(homePage.userProfile(), "User profile is not displayed on the dashboard");

		ExtentManager.getTest().pass("User profile is displayed");
	}

	@Test(description = "Verify user can navigate to Admin menu", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyNavigationToAdminMenu(String username, String password) {

		ExtentManager.getTest().info("Verifying Admin menu navigation");

		loginAsUser(username, password);

		Assert.assertTrue(homePage.menuVerification("Admin"), "Admin menu navigation failed");

		ExtentManager.getTest().pass("Successfully navigated to Admin menu");
	}

	@Test(description = "Verify navigation to PIM", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyNavigationToPIMMenu(String username, String password) {

		ExtentManager.getTest().info("Verifying PIM menu navigation");

		loginAsUser(username, password);

		Assert.assertTrue(homePage.menuVerification("PIM"), "PIM menu navigation failed");

		ExtentManager.getTest().pass("Successfully navigated to PIM menu");
	}

	@Test(description = "Verify navigation to Leave", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyNavigationToLeaveMenu(String username, String password) {

		ExtentManager.getTest().info("Verifying Leave menu navigation");

		loginAsUser(username, password);

		Assert.assertTrue(homePage.menuVerification("Leave"), "Leave menu navigation failed");

		ExtentManager.getTest().pass("Successfully navigated to Leave menu");
	}

	@Test(description = "Verify navigation to Time", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyNavigationToTimeMenu(String username, String password) {

		ExtentManager.getTest().info("Verifying Time menu navigation");

		loginAsUser(username, password);

		Assert.assertTrue(homePage.menuVerification("Time"), "Time menu navigation failed");

		ExtentManager.getTest().pass("Successfully navigated to Time menu");
	}
}
