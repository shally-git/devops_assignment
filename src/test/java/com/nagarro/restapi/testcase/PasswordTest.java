package com.nagarro.restapi.testcase;

import static org.testng.Assert.assertEquals;

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

public class PasswordTest extends BaseTest {

	private final Logger logger = LoggerFactory.getLogger(PasswordTest.class);

	@Test(description = "When user forgot the password")
	public void ForgotPassword() throws IOException {
		logger.debug("---ForgotPassword method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		JSONReader jsonReader = new JSONReader();

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper.post(Resources.FORGOTPASSWORD,
				headerMap,
				jsonReader.readJSON("forgotPassword.json"));
		logger.debug(response.asString());
		int statusCode = response.getStatusCode();
		assertEquals(statusCode, 200);
		String message = response.jsonPath().getString("message");
		assertEquals(message, "A reset link has been emailed.");
		logger.debug("---ForgotPassword method stats----");
		test.info("A reset link has been sent successfully");

	}

	@Test(description = "When user forgot the password")
	public void ResetPassword() throws IOException {
		logger.debug("---ResetPassword method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		JSONReader jsonReader = new JSONReader();

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper
				.post(Resources.RESETPASSWORD,
				headerMap,
				jsonReader.readJSON("resetPassword.json"));
		logger.debug(response.asString());
		int statusCode = response.getStatusCode();
		assertEquals(statusCode, 200);
		String message = response.jsonPath().getString("message");
		assertEquals(message, "Invalid password reset token.");
		logger.debug("---ResetPassword method stats----");
		test.info("Password rest successfully");

	}
}
