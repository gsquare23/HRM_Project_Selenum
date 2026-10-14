 package com.hrm.actionDriver;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		WebElement element = waitUtils.waitForElementVisible(by);

	    element.clear();
	    
	    element.sendKeys(value);
	}

	// Click an element safely if the DOM refreshes
	public void click(By by) {

	    StaleElementReferenceException lastException = null;

	    for (int attempt = 1; attempt <= 3; attempt++) {
	        try {
	            WebElement element = waitUtils.waitForElementClickable(by);
	            element.click();
	            return;

	        } catch (StaleElementReferenceException e) {
	            lastException = e;

	            System.out.println(
	                "Element became stale. Retrying click: "
	                + by + " | Attempt: " + attempt
	            );
	        }
	    }

	    throw lastException;
	}
	
	public boolean isDisplayed(By by) {

	    try {

	        waitUtils.waitForElementVisible(by);

	        return true;

	    } catch (Exception e) {

	        return false;
	    }
	}
	
	
	
	public boolean isInvisible(By by) {
		try {
			return waitUtils.waitForElementInvisible(by);
		} catch (Exception e) {
		
			return false;
		}
	}
	
	public void navigateBack() {
		driver.navigate().back();
	}
	

	public void pageRefresh() {
		driver.navigate().refresh();
	}
	
	public void selectValueFromList(By locator, String value) {

	    WebDriverWait wait =
	            new WebDriverWait(driver, Duration.ofSeconds(10));

	    wait.until(d -> {
	        List<WebElement> elements = d.findElements(locator);

	        for (WebElement element : elements) {
	            try {
	                if (element.isDisplayed()
	                        && element.getText().trim()
	                                .equalsIgnoreCase(value)) {

	                    if (element.isEnabled()) {
	                        element.click();
	                        return true;
	                    }
	                }
	            } catch (StaleElementReferenceException e) {
	                // DOM changed; fetch fresh elements on next poll
	            }
	        }

	        return false;
	    });
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
