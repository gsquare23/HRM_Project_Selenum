package com.hrm.utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

	private static Properties prop;

	public static void loadProperties() {

		prop = new Properties();
		String path = System.getProperty("user.dir") + "/src/main/resources/config.properties";
		try {
			
			
			FileInputStream fis = new FileInputStream(path);

			prop.load(fis);

		} catch (Exception e) {
			throw new RuntimeException(
                    "Unable to load config.properties from: "
                    + path,
                    e);
		}

	}

	public static String getProperty(String key) {

		if (prop == null) {
			loadProperties();
		}

		   String value = prop.getProperty(key);

	        if (value == null) {

	            throw new RuntimeException(
	                    "Property not found in config.properties: "
	                    + key);
	        }

	        return value;
	}

}
