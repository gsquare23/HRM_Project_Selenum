
package com.hrm.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.ExtentTest;
import com.hrm.driver.DriverFactory;
import com.hrm.utilities.ExtentManager;
import com.hrm.utilities.ScreenshotUtils;

public class TestListener implements ITestListener, IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		
		
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
		
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();

		ExtentManager.createTest(testName);

		ExtentTest test = ExtentManager.getTest();

		String description = result.getMethod().getDescription();

		if (description != null && !description.isBlank()) {
			test.info("Description: " + description);
		}

		test.info("Test class: " + result.getTestClass().getName());

		test.info("Thread: " + Thread.currentThread().getName());

		System.out.println("\n========== TEST STARTED: " + testName + " ==========");
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentManager.getTest().pass("Test passed");

		System.out.println("TEST PASSED: " + result.getName());

		ExtentManager.removeTest();
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ExtentTest test = ExtentManager.getTest();

		System.err.println("TEST FAILED: " + result.getName());

		Throwable failure = result.getThrowable();

		if (failure != null) {
			System.err.println("Failure reason: " + failure.getMessage());

			test.fail(failure);
		} else {
			test.fail("Test failed without a throwable message");
		}

		String screenshotPath = (String) result.getAttribute("screenshotPath");

		if (screenshotPath == null) {

			screenshotPath = ScreenshotUtils.takeScreenshot(DriverFactory.getDriver(), result.getName());

			if (screenshotPath != null) {
				result.setAttribute("screenshotPath", screenshotPath);
			}
		}

		if (screenshotPath != null) {
			try {
				test.addScreenCaptureFromPath(screenshotPath, "Failure screenshot");
			} catch (Exception e) {
				test.warning("Unable to attach screenshot: " + e.getMessage());
			}
		}

		ExtentManager.removeTest();
	}


@Override
public void onTestSkipped(ITestResult result) {

    ExtentTest test = ExtentManager.getTest();

    if (test == null) {
        ExtentManager.createTest(result.getName());
        test = ExtentManager.getTest();
    }

    Boolean retrying =
            (Boolean) result.getAttribute("retrying");

    if (Boolean.TRUE.equals(retrying)) {

        test.info(
                "This attempt did not pass. "
                + "TestNG will retry the test.");

        System.out.println(
                "RETRY PENDING: " + result.getName());

    } else {

        Throwable failure = result.getThrowable();

        if (failure != null) {
            test.skip(failure);
        } else {
            test.skip("Test skipped");
        }

        System.out.println(
                "TEST SKIPPED: " + result.getName());
    }

    ExtentManager.removeTest();
}


	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.flushReport();
	}
}