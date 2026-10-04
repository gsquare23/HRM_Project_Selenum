package com.hrm.utilities;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {
	
	private WebDriver driver; 
	private WebDriverWait wait;
	
	public WaitUtils(WebDriver driver) {
		
		this.driver = driver;
		
		int timeOut = Integer.parseInt(ConfigReader.getProperty("explicitWait"));
		
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
	
	}
	
	public void waitForElementVisible(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}
	
	
	public void waitForElementClickable(By by) {
		wait.until(ExpectedConditions.elementToBeClickable(by));
	}
	
	public void waitForElementInvisible(By by) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}
	
	public void waitForTitleContains(String title) {
		wait.until(ExpectedConditions.titleContains(title));
	}

	public void waitForUrlContains(String url) {
		wait.until(ExpectedConditions.urlContains(url));
	}
}
