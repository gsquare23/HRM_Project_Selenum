
package com.hrm.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderUtils {

	private ExcelReaderUtils() {
		// Utility class
	}

	public static List<String[]> getSheetData(String filePath, String sheetName) {

		List<String[]> data = new ArrayList<>();
		DataFormatter formatter = new DataFormatter();

		try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet(sheetName);

			if (sheet == null) {
				throw new IllegalArgumentException("Sheet does not exist: " + sheetName);
			}

			Row headerRow = sheet.getRow(0);

			if (headerRow == null || headerRow.getLastCellNum() < 1) {
				throw new IllegalArgumentException("Header row is missing or empty: " + sheetName);
			}

			int totalColumns = headerRow.getLastCellNum();

			boolean keepBlankRows = "blankUsernameAndBlankPassword".equals(sheetName);

			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {

				Row row = sheet.getRow(rowIndex);

				if (row == null && !keepBlankRows) {
					continue;
				}

				String[] rowData = new String[totalColumns];
				boolean emptyRow = true;

				for (int colIndex = 0; colIndex < totalColumns; colIndex++) {

					rowData[colIndex] = formatter.formatCellValue(
							row == null ? null : row.getCell(colIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));

					if (!rowData[colIndex].isBlank()) {
						emptyRow = false;
					}
				}

				if (!emptyRow || keepBlankRows) {
					data.add(rowData);
				}
			}

		} catch (IOException e) {
			throw new IllegalStateException("Unable to read Excel file: " + filePath, e);
		}

		if (data.isEmpty()) {
			throw new IllegalStateException("No test data found in sheet: " + sheetName);
		}

		return data;
	}
}
