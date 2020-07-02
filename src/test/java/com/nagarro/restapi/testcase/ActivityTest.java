package com.nagarro.restapi.testcase;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.nagarro.restapi.base.BaseTest;
import com.nagarro.restapi.util.Resources;
import com.nagarro.restapi.util.RestClientWrapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ActivityTest extends BaseTest {

	String activity_id;
	private final Logger logger = LoggerFactory.getLogger(ActivityTest.class);

	@Test(description = "Get activity of the user having user session")
	public void GetActivity() {
		logger.debug("---GetActivity method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);
		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);
		Response response = restClientWrapper.get(Resources.GETACTIVITY, headerMap);
		logger.debug(response.asString());
		activity_id = response.jsonPath().getString("activities.activity_id[1]"); // getString("activities.activity_id");
		assertNotNull(activity_id);
		logger.debug("---GetActivity method ends----");
		test.info("Getting an Activity of an user successfully");

	}

	@Test(description = "Follow an activity")
	public void FollowActivity() {

		logger.debug("---FollowActivity method stats----");
		httpRequest = RestAssured.given().param("type", "author").param("filter", "priyanka");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);
		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);
		Response response = restClientWrapper.put(Resources.FOLLOWACTIVITY, headerMap);
		logger.debug(response.asString());
		assertEquals(response.getStatusCode(), 200);
		logger.debug("---FollowActivity method ends----");
		test.info("Following an activity of an user");

	}

	@Test(description = "To delete any activity of a user", dependsOnMethods = { "GetActivity" })
	public void DeleteActivity() {

		logger.debug("---DeleteActivity method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);
		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);
		Response response = restClientWrapper.delete(Resources.GETACTIVITY + Integer.parseInt(activity_id), headerMap);
		logger.debug(response.asString());
		assertEquals(response.getStatusCode(), 200);
		logger.debug("---DeleteActivity method ends----");
		test.info("Deleting an activity of an user");
	}
}
