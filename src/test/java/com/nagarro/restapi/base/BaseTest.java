package com.nagarro.restapi.base;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.nagarro.restapi.util.ExtentManager;
import com.nagarro.restapi.util.JSONReader;
import com.nagarro.restapi.util.PropertiesUtils;
import com.nagarro.restapi.util.Resources;
import com.nagarro.restapi.util.RestClientWrapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTest extends PropertiesUtils {

	public static String baseUrl = null;
	public static PropertiesUtils readingPropertiesFile = new PropertiesUtils();
	public static ExtentReports extent;
	public static ExtentTest test;
	protected static RequestSpecification httpRequest;
	public static String userToken;
	private final Logger logger = LoggerFactory.getLogger(BaseTest.class);


	// Set up test infrastructure
	@BeforeSuite
	public void setupTest() throws IOException {
		extent = ExtentManager.getInstance(System.getProperty("user.dir") + "/reports/ExtentReports.html");
		logger.debug("Objects for extent report created succsessfully");
		baseUrl = readingPropertiesFile.getPropInstance().getProperty("baseUrl");
		userToken = createUserSession();

	}

	//Initialize request object and extent test object
	@BeforeMethod
	public static void startTest(Method method) {
		httpRequest = RestAssured.given();
		test = extent.createTest(method.getName());
	}

	// Getting userToken
	private String createUserSession() throws IOException {

		logger.debug("-----createUserSession method starts----");
		httpRequest = RestAssured.given();
		HashMap<String, String> headerMap = new HashMap<String, String>();

		// Creating object for JSONReader class to read JSON file
		JSONReader jsonReader = new JSONReader();
		
		RestClientWrapper restClientWrapper = new RestClientWrapper(baseUrl, httpRequest);

		Response response = restClientWrapper.post(Resources.CREATESESSION,
				headerMap,
				jsonReader.readJSON("createUserSession.json"));

		String userToken = response.jsonPath().getString("User-Token");
		logger.debug("User token:" + userToken);
		return userToken;
	}
	
	
	@AfterMethod
	public void reportFlush()
	{
		extent.flush();
		logger.debug("Extent report object flushed successfully.");
	}

	@AfterMethod
	public void extentReportLog(ITestResult testResult) throws Exception {

		// test = extent.createTest(testResult.getName());
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String TestCaseName = this.getClass().getSimpleName() + " Test Case Failure";
			test.log(Status.FAIL, TestCaseName);
		} else if (testResult.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, this.getClass().getSimpleName() + "-" + testResult.getName() + " Test Case Success");
		} else if (testResult.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, this.getClass().getSimpleName() + "-" + testResult.getName() + " Test Case Skipped");
		}
		test.info("Inside extentReportLog");
	}

}
