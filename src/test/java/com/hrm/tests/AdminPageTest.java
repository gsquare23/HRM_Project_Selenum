
package com.hrm.tests;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.hrm.baseClass.BaseClass;
import com.hrm.pages.AdminPage;
import com.hrm.pages.HomePage;
import com.hrm.pages.LoginPage;
import com.hrm.utilities.ConfigReader;

public class AdminPageTest extends BaseClass {

    private LoginPage loginPage;
    private HomePage homePage;
    private AdminPage adminPage;

    @BeforeMethod
    public void setupPages(Method method) {
        loginPage = new LoginPage();
        homePage = new HomePage();
        adminPage = new AdminPage();

        System.out.println(
            "Currently executing test case: " + method.getName()
        );
    }

    @Test(description = "Verify navigation to Admin page")
    public void verifyNavigationToAdminPage() {
        loginPage.login("admin", "admin123");

        Assert.assertTrue(
            homePage.menuVerification("Admin"),
            "Admin menu navigation failed"
        );
    }

    @Test(description = "Verify adding new admin user", priority = 1)
    public void verifyAddingNewAdminUser() throws InterruptedException {
        loginPage.login("admin", "admin123");
        homePage.menuVerification("Admin");

        adminPage.addNewAdminUSer(
            ConfigReader.getProperty("employerName"),
            ConfigReader.getProperty("newUsername"),
            ConfigReader.getProperty("password")
        );

        homePage.menuVerification("Admin");

        Assert.assertTrue(
            adminPage.verifyUserAdded(
                ConfigReader.getProperty("newUsername")
            ),
            "New admin user was not found"
        );
    }

    @Test(description = "Verify deleting admin user with Yes",priority = 4)
    public void deleteNewAddedUser() {
        loginPage.login("admin", "admin123");
        homePage.menuVerification("Admin");

        adminPage.deleteUserWithYes(ConfigReader.getProperty("newUsername"));
    }

    @Test(description = "Verify cancelling user deletion with No", priority = 3)
    
    public void cancelDeleteNewAddedUser() {
        loginPage.login("admin", "admin123");
        homePage.menuVerification("Admin");
        
        adminPage.deleteUserWithNo(ConfigReader.getProperty("newUsername"));
    }
    
    
    @Test(description = "Verify Search user by username",priority = 2)    
    		
    public void searchUserWithUsername() {
    	loginPage.login("admin", "admin123");
    	homePage.menuVerification("Admin");
    	
    	adminPage.seachUserWithUsername(ConfigReader.getProperty("newUsername"));
    }
    
    @Test(description = "Search user by role",priority = 2 )
    public void searchUserWithUserRole() {
    	loginPage.login("admin", "admin123");
    	homePage.menuVerification("Admin");
    	
    	adminPage.searchUserWithUserRole(ConfigReader.getProperty("newUsername"));
    }
    
    
    @Test(description = "Search user by Employee Name", priority = 2)
    public void searchUserWithEmployeeName() {
     	loginPage.login("admin", "admin123");
    	homePage.menuVerification("Admin");
    	
    	adminPage.searchUserWithEmployeeName(ConfigReader.getProperty("employerName"));
    }
    @Test(description = "Search user by status", priority = 2)
    public void searchUserWithStatus() {
    	loginPage.login("admin", "admin123");
    	homePage.menuVerification("Admin");
    	
    	adminPage.searchUserWithStatus(ConfigReader.getProperty("newUsername"));
    }
    
}