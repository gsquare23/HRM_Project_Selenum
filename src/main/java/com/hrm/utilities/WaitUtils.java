package com.hrm.utilities;

import java.time.Duration;

import org.jspecify.annotations.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	
	private WebDriverWait wait;
	
	public WaitUtils(WebDriver driver) {
		
		int timeOut = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
		
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	
	}
	
	public WebElement waitForElementVisible(By by) {
		return (wait.until(ExpectedConditions.visibilityOfElementLocated(by)));
	}
	
	
	public WebElement waitForElementClickable(By by) {
		return (wait.until(ExpectedConditions.elementToBeClickable(by)));
	}
	
	public boolean waitForElementInvisible(By by) {
		return (wait.until(ExpectedConditions.invisibilityOfElementLocated(by)));
	}
	
	public void waitForTitleContains(String title) {
		wait.until(ExpectedConditions.titleContains(title));
	}

	public void waitForUrlContains(String url) {
		wait.until(ExpectedConditions.urlContains(url));
	}
}
