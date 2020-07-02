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

public class QuoteTest extends BaseTest {

	private final Logger logger = LoggerFactory.getLogger(QuoteTest.class);
	String authorId = null;

	@Test(description = "Add a new quote")
	public void AddQuote() throws IOException {
		logger.debug("---AddQuote method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);

		JSONReader jsonReader = new JSONReader();

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper.post(Resources.GETQUOTE, headerMap, jsonReader.readJSON("addQuote.json"));
		logger.debug(response.asString());
		authorId = response.jsonPath().getString("id");
		assertNotNull(authorId);
		logger.debug("---AddQuote method ends----");
		test.info(" A new quote added successfully");
	}

	@Test(description = "Hide a Quote")
	public void HideQuote() throws IOException {
		logger.debug("---HideQuote method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);
		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper.put(Resources.GETQUOTE + "/" + authorId + "/hide", headerMap);
		logger.debug(response.asString());
		String hidden = response.jsonPath().getString("user_details.hidden");
		assertEquals(hidden, "true");
		logger.debug("---HideQuote method ends----");
		test.info("Quote added successfully");

	}

	@Test(description = "Mark a quote as favourite", dependsOnMethods = { "AddQuote" })
	public void FavQuote() {
		logger.debug("---FavQuote method stats----");
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Token", userToken);

		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper.put(Resources.GETQUOTE + "/" + authorId + "/fav", headerMap);
		logger.debug(response.asString());
		String favorite = response.jsonPath().getString("user_details.favorite");
		assertEquals(favorite, "true");
		logger.debug("---FavQuote method stats----");
		test.info("Quote marked as favourite successfully");


	}
}
