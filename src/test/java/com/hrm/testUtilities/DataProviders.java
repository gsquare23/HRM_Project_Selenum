
package com.hrm.testUtilities;

import java.util.List;

import org.testng.annotations.DataProvider;

import com.hrm.utilities.ExcelReaderUtils;

public class DataProviders {

    private static final String FILE_PATH =
            System.getProperty("user.dir")
            + "/src/test/resources/TestData/LoginData_HRMProject.xlsx";


    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData() {
        return getSheetData("validLoginData");
    }

    @DataProvider(name = "inValidUsername")
    public static Object[][] inValidUsername() {
        return getSheetData("inValidUsername");
    }

    @DataProvider(name = "inValidPassword")
    public static Object[][] inValidPassword() {
        return getSheetData("inValidPassword");
    }

    @DataProvider(name = "blankUsernameAndBlankPassword")
    public static Object[][] blankUsernameAndBlankPassword() {
        return getSheetData("blankUsernameAndBlankPassword");
    }

    @DataProvider(name = "blankUsernameAndPassword")
    public static Object[][] blankUsernameAndPassword() {
        return getSheetData("blankUsernameAndPassword");
    }

    @DataProvider(name = "usernameAndBlankPassword")
    public static Object[][] usernameAndBlankPassword() {
        return getSheetData("usernameAndBlankPassword");
    }


    private static Object[][] getSheetData(String sheetName) {

        List<String[]> sheetData =
                ExcelReaderUtils.getSheetData(FILE_PATH, sheetName);

        Object[][] data = new Object[sheetData.size()][];

        for (int i = 0; i < sheetData.size(); i++) {
            data[i] = sheetData.get(i);
        }

        return data;
    }
}
