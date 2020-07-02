package com.nagarro.restapi.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.nagarro.restapi.base.BaseTest;
import com.nagarro.restapi.util.JSONReader;
import com.nagarro.restapi.util.Resources;
import com.nagarro.restapi.util.RestClientWrapper;

import io.restassured.response.Response;

public class UsersTest extends BaseTest {

	private final Logger logger = LoggerFactory.getLogger(UsersTest.class);

	// Skipping this method as user has already been created so this will fail.
	@Test(description = "When a new user created successfully", priority = 1, enabled = false)
	public void addNewUserSuccessfulTest() {

		logger.debug("---addNewUserSuccessfulTest method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();

		JSONReader jsonReader = new JSONReader();

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response;
		try {
			response = restClientWrapper.post(Resources.GETUSER, headerMap, jsonReader.readJSON("addUser.json"));
			logger.debug(response.asString());
			String newUserToken = response.jsonPath().getString("User-Token");
			assertNotNull(newUserToken);
			logger.debug("---addNewUserSuccessfulTest method ends----");
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.toString());
		}
		test.info("New user created successfully");
	}

	@Test(description = "When a user is already existing", priority = 2)
	public void addNewUserFailTest() {
		logger.debug("---addNewUserFailTest method starts----");
		HashMap<String, String> headerMap = new HashMap<String, String>();

		JSONReader jsonReader = new JSONReader();

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response;
		try {
			response = restClientWrapper.post(Resources.GETUSER, headerMap, jsonReader.readJSON("addUser.json"));
			logger.debug(response.asString());
			String errorCode = response.jsonPath().getString("error_code");
			assertEquals(errorCode, "32");
			logger.debug("---addNewUserFailTest method ends----");
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug(e.toString());

		}
		test.info("A new user added successfully");
	}

	@Test(description = "Get specific user", priority = 3)
	public void getUserTest() {
		logger.debug("---getUserTest method starts----");
		String loginUserName = "priyankakumari";
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper.get(Resources.GETUSER + "/" + loginUserName, headerMap);
		logger.debug(response.asString());
		int statusCode = response.getStatusCode();
		logger.debug("---getUserTest method ends----");
		assertEquals(statusCode, 200);

		test.info(loginUserName + " User get successfully");
	}

	@Test(description = "Update a specific user", priority = 4)
	public void UpdateUser() throws IOException {
		logger.debug("---UpdateUser method starts----");
		String loginUserName = "priyankakumari";
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);
		JSONReader jsonReader = new JSONReader();

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);
		Response response = restClientWrapper
				.put(Resources.GETUSER + "/" + loginUserName,
				headerMap,
				jsonReader.readJSON("updateUser.json"));
		logger.debug(response.asString());
		String message = response.jsonPath().getString("message");
		logger.debug("---UpdateUser method ends----");
		assertEquals(message, "User successfully updated.");
		test.info(loginUserName + " User got updated successfully");

	}

}
