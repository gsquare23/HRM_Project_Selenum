package com.hrm.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.hrm.basePage.BasePage;

public class AdminPage extends BasePage{

	private By usernameSearchBox = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");
	private By searchButton = By.xpath("//button[@type='submit']");
	
	private By recordFound = By.xpath("//span[contains(normalize-space(), 'Record Found')]");
	private By recordsFound = By.xpath("//span[contains(normalize-space(), 'Records Found')]");
	
	
	private By addButton = By.xpath("//div[@class= 'orangehrm-header-container']//button[@type='button']");
	
	
	// SaveSystemUSer Page 
	
	private By addUSerText = By.xpath("//h6[text()= 'Add User']");
	private By userRoleInputBox = By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[1]");
	private By selectAdminUserRole = By.xpath("//div[@role='listbox']//span[text()='Admin']");
	private By addEmployerName = By.xpath("//input[@placeholder= 'Type for hints...']");
	private By autoSelectEmployerName = By.xpath("//div[@role='listbox']//span");
	private By statusDropdown = By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[2]");
	private By status = By.xpath("//div[@role='listbox']//span[text()='Enabled']");
	private By userNameInputBox = By.xpath("(//div[@class='oxd-input-group oxd-input-field-bottom-space']//input)[2]");
	private By password = By.xpath("(//input[@type= 'password'])[1]");
	private By confirmPassword = By.xpath("(//input[@type= 'password'])[2]");
	private By saveButton = By.xpath("(//button[@type= 'submit'])[1]");
	private By successMessage = By.xpath("//div[contains(@class,'oxd-toast-content')]");
	
	private By deleteModal = By.xpath("//p[text() = 'Are you Sure?']");
	private By yesDeleteButton = By.xpath("//div[@class = 'orangehrm-modal-footer']//button[2]");
	private By noDeleteButton = By.xpath("//div[@class = 'orangehrm-modal-footer']//button[1]");
	
	public By getAddedUserLocator(String username) {
	    return By.xpath("//div[text() = '"+ username + "']");
	}
	
	public void searchByUsername(String username) {
		
		actionDriver.enterText(usernameSearchBox, username);
		
		actionDriver.click(searchButton);
	}
	
	
	public void addNewAdminUSer(String employerName, String userName, String passwordValue) throws InterruptedException {
		
		actionDriver.click(addButton);
		
		actionDriver.isDisplayed(addUSerText);
		
		actionDriver.click(userRoleInputBox);
	
		actionDriver.click(selectAdminUserRole);
		
		actionDriver.enterText(addEmployerName, employerName);
		
		actionDriver.click(autoSelectEmployerName);
		
		actionDriver.click(statusDropdown);
	
		actionDriver.click(status);
		
		actionDriver.enterText(userNameInputBox, userName);
		
		actionDriver.enterText(password, passwordValue);
	
		actionDriver.enterText(confirmPassword, passwordValue);
	
		actionDriver.click(saveButton);
		
		Assert.assertTrue(actionDriver.isDisplayed(successMessage));
	
	}
	
	public boolean verifyUserAdded(String username) {
		return actionDriver.isDisplayed(getAddedUserLocator(username));
	}
	
	
	private By getUserRowLocator(String username) {
	    return By.xpath(
	        "//div[contains(@class,'oxd-table-card')]//div[text()='" + username + "']");
	}

	private By getDeleteButtonLocator(String username) {
	    return By.xpath(
	        "//div[contains(@class,'oxd-table-card')]" +
	        "[.//div[normalize-space()='" + username + "']]" +
	        "//button[.//i[contains(@class,'bi-trash')]]"
	    );
	}

	public void deleteUserWithYes(String username) {

	    By userRow = getUserRowLocator(username);
	    By deleteButton = getDeleteButtonLocator(username);

	    Assert.assertTrue(
	        actionDriver.isDisplayed(deleteButton),
	        "Delete button not found for user: " + username
	    );

	    actionDriver.click(deleteButton);

	    Assert.assertTrue(
	        actionDriver.isDisplayed(deleteModal),
	        "Delete confirmation modal was not displayed"
	    );

	    actionDriver.click(yesDeleteButton);

	    System.out.println(userRow);
	    
	    Assert.assertTrue(
	        actionDriver.isInvisible(userRow),
	        "User row was not removed after deletion: " + username
	    );
	}
	
	public void deleteUserWithNo(String username) {
		By deleteButtonLocator = getDeleteButtonLocator(username);
	    
	    // Verify the delete button itself is displayed
	    Assert.assertTrue(actionDriver.isDisplayed(deleteButtonLocator), "Delete button for user row was not found.");

	    // Find and click the button directly
	    WebElement deleteButton = driver.findElement(deleteButtonLocator);
	    deleteButton.click();

	    // Confirm and complete modal actions
	    Assert.assertTrue(
	        actionDriver.isDisplayed(deleteModal),
	        "Delete confirmation modal was not displayed"
	    );
	    
	    actionDriver.click(noDeleteButton);
	    
	    Assert.assertTrue(actionDriver.isDisplayed(deleteButtonLocator));

	}
	
	public void seachUserWithUsername(String username) {
		
		actionDriver.isDisplayed(usernameSearchBox);
		
		actionDriver.enterText(usernameSearchBox, username);
		
		actionDriver.click(searchButton);
		
		Assert.assertTrue(actionDriver.isDisplayed(recordFound), "Unable to search user with "  + username );
		
		By userRow = getUserRowLocator(username);
		
		Assert.assertTrue(actionDriver.isDisplayed(userRow), "Unable to search user with "  + username ); 
	}
	
	
	public void searchUserWithUserRole(String username) {
		
		actionDriver.click(userRoleInputBox);
		
		actionDriver.click(selectAdminUserRole);
		
		actionDriver.click(searchButton);
		
		Assert.assertTrue(actionDriver.isDisplayed(recordsFound), "Unable to search user with "  + username );
		
		By userRow = getUserRowLocator(username);
		
		Assert.assertTrue(actionDriver.isDisplayed(userRow), "Unable to search user with "  + username ); 
		
		
	}
	
	private By getEmployerRowLocator(String employerName) {
	    return By.xpath(
	        "//div[contains(@class,'oxd-table-card')]//div[text()='"+employerName+"']");
	}

	public void searchUserWithEmployeeName(String employerName) {
		
		actionDriver.enterText(addEmployerName, employerName);
		
		actionDriver.click(autoSelectEmployerName);
		
		actionDriver.click(searchButton);
		
		Assert.assertTrue(actionDriver.isDisplayed(recordFound), "Unable to search user with employer Name "  + employerName );
		
		By employerRow = getEmployerRowLocator(employerName);
		
		System.out.println(employerRow);
		Assert.assertTrue(actionDriver.isDisplayed(employerRow), "Unable to search user with employer Name "  + employerName ); 
		
	}

	public void searchUserWithStatus(String username) {
		actionDriver.click(statusDropdown);
		
		actionDriver.click(status);
		
		actionDriver.click(searchButton);
		
		Assert.assertTrue(actionDriver.isDisplayed(recordsFound), "Unable to search user with "  + username );
		
		By userRow = getUserRowLocator(username);
		
		Assert.assertTrue(actionDriver.isDisplayed(userRow), "Unable to search user with "  + username ); 
	}
	
}
