
package com.hrm.pages;

import org.openqa.selenium.By;
import com.hrm.basePage.BasePage;

public class AdminPage extends BasePage {

	// ==================== Search Locators ====================

	private By usernameSearchBox = By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]");

	private By searchButton = By.xpath("//button[@type='submit']");

	private By recordCount = By
			.xpath("//span[contains(normalize-space(), 'Record') " + "and contains(normalize-space(), 'Found')]");

	// ==================== Add User Locators ====================

	private By addButton = By.xpath("//div[@class='orangehrm-header-container']//button[@type='button']");

	private By addUSerText = By.xpath("//h6[text()='Add User']");

	private By userRoleInputBox = By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[1]");

	private By selectAdminUserRole = By.xpath("//div[@role='listbox']//span[text()='Admin']");

	private By addEmployerName = By.xpath("//input[@placeholder='Type for hints...']");

	private By autoSelectEmployerName = By.xpath("//div[@role='listbox']//span");

	private By statusDropdown = By.xpath("(//i[@class='oxd-icon bi-caret-down-fill oxd-select-text--arrow'])[2]");

	private By status = By.xpath("//div[@role='listbox']//span[text()='Enabled']");

	private By userNameInputBox = By.xpath("(//div[@class='oxd-input-group oxd-input-field-bottom-space']//input)[2]");

	private By password = By.xpath("(//input[@type='password'])[1]");

	private By confirmPassword = By.xpath("(//input[@type='password'])[2]");

	private By saveButton = By.xpath("(//button[@type='submit'])[1]");

	private By successMessage = By.xpath("//div[contains(@class,'oxd-toast-content')]");

	// ==================== Delete User Locators ====================

	private By deleteModal = By.xpath("//p[text()='Are you Sure?']");

	private By yesDeleteButton = By.xpath("//div[@class='orangehrm-modal-footer']//button[2]");

	private By noDeleteButton = By.xpath("//div[@class='orangehrm-modal-footer']//button[1]");

	// ==================== Dynamic Locators ====================

	public By getAddedUserLocator(String username) {
		return By.xpath("//div[text()='" + username + "']");
	}

	private By getUserRowLocator(String username) {
		return By.xpath("//div[contains(@class,'oxd-table-card')]//div[text()='" + username + "']");
	}

	private By getDeleteButtonLocator(String username) {
		return By.xpath("//div[contains(@class,'oxd-table-card')]" + "[.//div[text()='" + username + "']]"
				+ "//button[.//i[contains(@class,'bi-trash')]]");
	}

	private By getEmployerRowLocator(String employerName) {
		return By.xpath("//div[contains(@class,'oxd-table-card')]//div[text()='" + employerName + "']");
	}

	// ==================== Search Verification Methods ====================

	public boolean areSearchResultsDisplayed() {
		return actionDriver.isDisplayed(recordCount);
	}

	public boolean isUserPresent(String username) {
		return actionDriver.isDisplayed(getUserRowLocator(username));
	}

	public boolean isEmployerRowPresent(String employerName) {
		return actionDriver.isDisplayed(getEmployerRowLocator(employerName));
	}

	public boolean verifyUserAdded(String username) {
		return actionDriver.isDisplayed(getAddedUserLocator(username));
	}

	public boolean isSuccessMessageDisplayed() {
		return actionDriver.isDisplayed(successMessage);
	}
	// ==================== Search Actions ====================

	public void searchUserWithUsername(String username) {
		actionDriver.enterText(usernameSearchBox, username);
		actionDriver.click(searchButton);
	}

	public void searchUserWithUserRole(String username) {
		actionDriver.click(userRoleInputBox);
		actionDriver.click(selectAdminUserRole);
		actionDriver.click(searchButton);
	}

	public void searchUserWithEmployeeName(String employerName) {
		actionDriver.enterText(addEmployerName, employerName);
		actionDriver.click(autoSelectEmployerName);
		actionDriver.click(searchButton);
	}

	public void searchUserWithStatus(String username) {
		actionDriver.click(statusDropdown);
		actionDriver.click(status);
		actionDriver.click(searchButton);
	}

	// ==================== Add User Action ====================

	public void addNewAdminUSer(String employerName, String userName, String passwordValue) {

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

	}

	// ==================== Delete User Actions ====================

	public boolean isDeleteConfirmationDisplayed() {
		return actionDriver.isDisplayed(deleteModal);
	}

	private void openDeleteConfirmation(String username) {

		By deleteButton = getDeleteButtonLocator(username);

		actionDriver.click(deleteButton);
		actionDriver.isDisplayed(deleteModal);
	}

	public void deleteUserWithYes(String username) {

		openDeleteConfirmation(username);

		actionDriver.click(yesDeleteButton);

	}

	public void deleteUserWithNo(String username) {

		openDeleteConfirmation(username);

		actionDriver.click(noDeleteButton);

	}
}