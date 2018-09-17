package com.njkol.mongo.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyValues {

	private static InputStream inputStream;
	private static String propFileName = "src/main/resources/app.properties";

	public static String getPropValues(String key) throws IOException {

		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			return prop.getProperty(key);
		} finally {
			inputStream.close();
		}
	}
}
