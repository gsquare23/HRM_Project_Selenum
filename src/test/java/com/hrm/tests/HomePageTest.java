package com.hrm.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hrm.baseClass.BaseClass;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;

public class HomePageTest extends BaseClass{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	
	@BeforeMethod
	public void setupPages (Method method) {
		loginPage = new LoginPage();
		homePage = new HomePage();
		
		String testCaseName = method.getName(); 
        System.out.println("Currently executing test case: " + testCaseName);
	}
	
	
	@Test(description = "Verify Dashboard is displayed after successful login",
		    groups = {"Smoke", "Regression"})
	public void verifyDashboardIsDisplayedAfterLogin() {
		loginPage.login("admin", "admin123");
		
		Assert.assertTrue(
                homePage.isDashboardDisplayed(),
                "Dashboard is not displayed after login");
		
	}
	
	@Test(
		    description = "Verify main menu is displayed on Dashboard",
		    groups = {"UI", "Regression"}
		)
	public void verifyMainMenuIsDisplayed() {
		loginPage.login("admin", "admin123");
		
		Assert.assertTrue(
                homePage.isDashboardDisplayed(),
                "Dashboard is not displayed after login");
		
		Assert.assertTrue(homePage.menuDisplayed());
		
	}
	
	
	@Test(description = "Verify user profile is displayed --- UI")
	public void verifyUserProfileIsDisplayed() {
		loginPage.login("admin", "admin123");
		
		Assert.assertTrue(homePage.userProfile());
		
	}
	
	
	@Test(
		    description = "Verify user can navigate to Admin menu",
		    groups = {"Functional", "Regression"}
		)
	public void verifyNavigationToAdminMenu() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(
	            homePage.menuVerification("Admin"),
	            "Admin menu navigation failed"
	    );
	}
	
	@Test(description = "Verify navigation to PIM --- Functional")
	public void verifyNavigationToPIMMenu() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.menuVerification("PIM"));
	}
	
	@Test(description = "Verify navigation to Leave --- Functional")
	public void verifyNavigationToLeaveMenu() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.menuVerification("Leave"));
	}
	
	@Test(description = "Verify navigation to Time --- Functional")
	public void verifyNavigationToTimeMenu() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.menuVerification("Time"));
	}
	
	
	
	
	
	
}
