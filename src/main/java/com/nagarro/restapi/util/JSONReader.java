package com.nagarro.restapi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class JSONReader {


	private static final String TESTDATA_PATH = "\\src\\test\\java\\com\\nagarro\\restapi\\testdata\\";

	public String readJSON(String filePath) throws IOException {

		String completeFilePath = System.getProperty("user.dir")
				+ TESTDATA_PATH + filePath;
		FileInputStream fileInputStream = new FileInputStream(new File(completeFilePath));
		String jsonBody = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8.name());
		return jsonBody;
	}

}
