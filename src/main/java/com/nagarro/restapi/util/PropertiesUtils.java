package com.nagarro.restapi.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class PropertiesUtils {

	// store properties file path in string
	final static String TEST_CONFIG_PROP_PATH = System.getProperty("user.dir")
			+ "\\src\\main\\resources\\config.properties";

	private static Properties prop;

	public Properties getPropInstance() {
		prop = readPropertyFile();
		return prop;
	}

	private static Properties readPropertyFile() {

		Properties prop = new Properties();

		try {
			FileInputStream fileInputStream = new FileInputStream(TEST_CONFIG_PROP_PATH);
			prop.load(fileInputStream);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
