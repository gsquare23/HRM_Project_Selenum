package com.hrm.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hrm.baseClass.BaseClass;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;
import com.hrm.testUtilities.DataProviders;
import com.hrm.utilities.ConfigReader;
import com.hrm.utilities.ExtentManager;

public class AdminPageTest extends BaseClass {

	private LoginPage loginPage;
	private HomePage homePage;
	private AdminPage adminPage;

	@BeforeMethod
	public void setupPages(Method method) {

		loginPage = new LoginPage();
		homePage = new HomePage();
		adminPage = new AdminPage();

		System.out.println("Currently executing test case: " + method.getName() + " | Thread: "
				+ Thread.currentThread().getName());
	}

	/**
	 * Logs in using credentials supplied by the DataProvider and opens the Admin
	 * page.
	 */
	private void openAdminPage(String loginUsername, String loginPassword) {

		ExtentManager.getTest().info("Starting login");

		loginPage.login(loginUsername, loginPassword);

		ExtentManager.getTest().info("Navigating to Admin page");

		boolean adminPageOpened = homePage.menuVerification("Admin");

		Assert.assertTrue(adminPageOpened, "Unable to navigate to Admin page");

		ExtentManager.getTest().pass("Successfully navigated to Admin page");
	}

	/**
	 * Uses validLoginData from DataProviders for login credentials.
	 */

	@Test(description = "Verify navigation to Admin page", groups = { "Smoke",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class)
	public void verifyNavigationToAdminPage(String loginUsername, String loginPassword) {

		openAdminPage(loginUsername, loginPassword);

		ExtentManager.getTest().pass("Admin page navigation verified successfully");
	}

	@Test(description = "Verify adding new admin user", groups = { "Functional", "Regression" },
			dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 1)

	public void verifyAddingNewAdminUser(String loginUsername, String loginPassword) {

		ExtentManager.getTest().info("Starting add admin user test");

		openAdminPage(loginUsername, loginPassword);

		String username = ConfigReader.getProperty("newUsername");
		String employerName = ConfigReader.getProperty("employerName");
		String newUserPassword = ConfigReader.getProperty("newUserpassword");

		adminPage.addNewAdminUSer(employerName, username, newUserPassword);

		Assert.assertTrue(adminPage.isSuccessMessageDisplayed(),
				"Success message was not displayed after adding the user");

		boolean adminPageOpened = homePage.menuVerification("Admin");

		Assert.assertTrue(adminPageOpened, "Unable to return to Admin page after adding user");

		Assert.assertTrue(adminPage.verifyUserAdded(username), "Newly created user was not found: " + username);

		ExtentManager.getTest().pass("New admin user was added and verified successfully");
	}

	@Test(description = "Verify deleting admin user with Yes", groups = { "Functional", "Regression" },
			dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 4)
	public void deleteNewAddedUser(String loginUsername, String loginPassword) {

		ExtentManager.getTest().info("Starting delete admin user test");

		openAdminPage(loginUsername, loginPassword);

		String username = ConfigReader.getProperty("newUsername");

		adminPage.deleteUserWithYes(username);

		Assert.assertFalse(adminPage.isUserPresent(username), "User should not exist after deletion: " + username);

		ExtentManager.getTest().pass("User was successfully deleted and verified");
	}

	@Test(description = "Verify cancelling user deletion with No", groups = { "Functional", "Regression" }, 
				dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 3)
	public void cancelDeleteNewAddedUser(String loginUsername, String loginPassword) {

		ExtentManager.getTest().info("Starting cancel deletion test");

		openAdminPage(loginUsername, loginPassword);

		String username = ConfigReader.getProperty("newUsername");

		adminPage.deleteUserWithNo(username);

		Assert.assertTrue(adminPage.isUserPresent(username),
				"User should still exist after cancelling deletion: " + username);

		ExtentManager.getTest().pass("Deletion was cancelled and the user remains present");
	}

	@Test(description = "Verify search user by username", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 2)
	public void searchUserWithUsername(String loginUsername, String loginPassword) {

		openAdminPage(loginUsername, loginPassword);

		String username = ConfigReader.getProperty("newUsername");

		adminPage.searchUserWithUsername(username);

		Assert.assertTrue(adminPage.areSearchResultsDisplayed(), "Search result count was not displayed");

		Assert.assertTrue(adminPage.isUserPresent(username), "Expected user was not found: " + username);

		ExtentManager.getTest().pass("Username search operation completed successfully");
	}

	@Test(description = "Verify search user by role", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class,priority = 2)
	public void searchUserWithUserRole(String loginUsername, String loginPassword) {

		openAdminPage(loginUsername, loginPassword);

		String username = ConfigReader.getProperty("newUsername");

		adminPage.searchUserWithUserRole(username);

		Assert.assertTrue(adminPage.areSearchResultsDisplayed(), "Search result count was not displayed");

		Assert.assertTrue(adminPage.isUserPresent(username), "Expected user was not found: " + username);

		ExtentManager.getTest().pass("User-role search operation completed successfully");
	}

	@Test(description = "Verify search user by Employee Name", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 2)
	public void searchUserWithEmployeeName(String loginUsername, String loginPassword) {

		openAdminPage(loginUsername, loginPassword);

		String employerName = ConfigReader.getProperty("employerName");

		adminPage.searchUserWithEmployeeName(employerName);

		Assert.assertTrue(adminPage.areSearchResultsDisplayed(), "Search result count was not displayed");

		Assert.assertTrue(adminPage.isEmployerRowPresent(employerName),
				"Unable to find employee name in search results: " + employerName);

		ExtentManager.getTest().pass("Employee-name search operation completed successfully");
	}

	@Test(description = "Verify search user by status", groups = { "Functional",
			"Regression" }, dataProvider = "validLoginData", dataProviderClass = DataProviders.class, priority = 2)
	public void searchUserWithStatus(String loginUsername, String loginPassword) {

		openAdminPage(loginUsername, loginPassword);

		String username = ConfigReader.getProperty("newUsername");

		adminPage.searchUserWithStatus(username);

		Assert.assertTrue(adminPage.areSearchResultsDisplayed(), "Search result count was not displayed");

		Assert.assertTrue(adminPage.isUserPresent(username), "Expected user was not found: " + username);

		ExtentManager.getTest().pass("User-status search operation completed successfully");
	}
}
