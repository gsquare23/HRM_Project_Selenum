 package com.hrm.actionDriver;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.hrm.utilities.WaitUtils;

public class ActionDriver {

	private WebDriver driver;
	private WaitUtils waitUtils;

	public ActionDriver(WebDriver driver) {
		this.driver = driver;
		this.waitUtils = new WaitUtils(driver);
	}

	// method to enter Text

	public void enterText(By by, String value) {
		waitUtils.waitForElementVisible(by);

		WebElement element = driver.findElement(by);

	    element.clear();
	    element.sendKeys(value);

		System.out.println("Entered text: " + getElementDescription(by) + "--->" + value);

	}

	// Method to click an element
	public void click(By by) {
		waitUtils.waitForElementClickable(by);

		WebElement element = driver.findElement(by);

	    element.click();
	}
	
	
	public boolean isDisplayed(By by) {

	    try {

	        waitUtils.waitForElementVisible(by);

	        return driver.findElement(by).isDisplayed();

	    } catch (Exception e) {

	        return false;
	    }
	}
	
	
	public void navigateBack() {
		driver.navigate().back();
	}
	
	
	
	public void selectValueFromList(By by, String menuValue) {
		 waitUtils.waitForElementVisible(by);
		 
		 List<WebElement> values = driver.findElements(by);
		 
		 for(WebElement value : values) {
			  if(value.getText().equalsIgnoreCase(menuValue)) {
				  value.click();
				  return;
			  }
		 }
		 
		 throw new RuntimeException(
		            "Unable to find menu value: " + menuValue);

		
	}

	// Method to get the description of an Element

	public String getElementDescription(By locator) {
		// Check for null driver or locator to avoid null pointer exception
		if (driver == null) {
			return "driver is null";
		}
		if (locator == null) {
			return "locator is null";
		}

		try {
			// Find the element using locator
			WebElement element = driver.findElement(locator);

			// Get Element description

			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String text = element.getText();
			String className = element.getDomAttribute("class");
			String placeHolder = element.getDomAttribute("placeholder");

			// Return the description based on Element attributes
			if (isNotEmpty(name)) {
				return "Element with name: " + name;
			} else if (isNotEmpty(id)) {
				return "Element with id: " + id;
			} else if (isNotEmpty(text)) {
				return "Element with text: " + truncate(text, 50);
			} else if (isNotEmpty(className)) {
				return "Element with className: " + className;
			} else if (isNotEmpty(placeHolder)) {
				return "Element with placeHolder: " + placeHolder;
			}
		} catch (Exception e) {
			System.out.println("unable to describe the element");
		}
		return "Unable to describe the element";

	}

	// Utility method to check a String is not null or empty
	private boolean isNotEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	// Utility method to truncate long string
	private String truncate(String value, int maxlength) {
		if (value == null || value.length() <= maxlength) {
			return value;
		}
		return value.substring(0, maxlength) + "...";
	}

}
