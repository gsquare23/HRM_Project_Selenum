
package com.hrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    private static final String SCREENSHOT_DIR =
            System.getProperty("user.dir")
            + "/test-output/screenshots/";

    public static String takeScreenshot(
            WebDriver driver, String screenshotName) {

        if (driver == null) {
            System.err.println(
                    "Screenshot skipped: WebDriver is null.");
            return null;
        }

        try {
            // Create screenshot directory if it doesn't exist
            File directory = new File(SCREENSHOT_DIR);

            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException(
                        "Unable to create screenshot directory: "
                        + directory.getAbsolutePath());
            }

            // Generate a timestamp for the filename
            String timestamp = new SimpleDateFormat(
                    "yyyy-MM-dd_HH-mm-ss-SSS")
                    .format(new Date());

            String safeName = screenshotName.replaceAll(
                    "[^a-zA-Z0-9._-]", "_");

            File destination = new File(
                    directory,
                    safeName + "_" + timestamp + ".png");

            // Capture and save screenshot
            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            FileUtils.copyFile(source, destination);

            String savedPath = destination.getAbsolutePath();

            System.out.println(
                    "Screenshot saved: " + savedPath);

            return savedPath;

        } catch (IOException | RuntimeException e) {
            System.err.println(
                    "Screenshot capture failed for "
                    + screenshotName + ": " + e.getMessage());

            return null;
        }
    }
}