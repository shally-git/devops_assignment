package com.nagarro.restapi.util;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClientWrapper extends PropertiesUtils {

	public RequestSpecification httpRequest;
	private Response restResponse;
	public static PropertiesUtils readingPropertiesFile = new PropertiesUtils();
	public static String token = readingPropertiesFile.getPropInstance().getProperty("token");

	public RestClientWrapper(String baseUrl, RequestSpecification httpRequest)
	{
		this.httpRequest = httpRequest;
		this.httpRequest.baseUri(baseUrl);
	}

	public Response get(String resource, HashMap<String, String> headerMap) {
		
		addHeader(headerMap);
		restResponse = httpRequest.when().get(resource);
		return restResponse;
	}

	public Response post(String resource, HashMap<String, String> headerMap, String body) {

		addHeader(headerMap);

		restResponse = httpRequest.when().body(body).post(resource).then().assertThat().statusCode(200).extract()
				.response();

		return restResponse;
	}

	public Response put(String resource, HashMap<String, String> headerMap, String body) {

		addHeader(headerMap);

		restResponse = httpRequest.when().body(body).put(resource);
		return restResponse;
	}



	public Response delete(String resource, HashMap<String, String> headerMap) {

		addHeader(headerMap);

		restResponse = httpRequest.when().delete(resource);
		return restResponse;
	}

	public Response put(String resource, HashMap<String, String> headerMap) {

		addHeader(headerMap);
		restResponse = httpRequest.when().put(resource);
		return restResponse;
	}

	private void addHeader(HashMap<String, String> headerMap) {
		httpRequest.header("Content-Type", "application/json");
		headerMap.put("Authorization", "Token token=" + token + "\"");
		for (Map.Entry<String, String> entry : headerMap.entrySet()) {
			httpRequest.header(entry.getKey(), entry.getValue());
		}
	}




}
