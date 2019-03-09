package com.qa.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestCliet;
import com.qa.data.Users;

public class PostAPITest extends TestBase {

	public PostAPITest() throws IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	TestBase testBase;
	String serviceurl;
	String apiurl;
	String url;
	RestCliet restclient;
	CloseableHttpResponse closeablehttpresponse;

	@BeforeMethod
	public void setup() throws IOException {
		testBase = new TestBase();

		serviceurl = prop.getProperty("URL");

		apiurl = prop.getProperty("serviceURL");

		url = serviceurl + apiurl;// complete url---end point url

	}

	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		restclient = new RestCliet();

		HashMap<String, String> headerMap = new HashMap<String, String>();

		headerMap.put("Content-Type", "application/json");

		// Jakson API
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus", "leader");//expected user object

		// object to JSON file--Marshelling

		mapper.writeValue(
				new File(
						"C:/Users/nitin.kumar2/workspace/BhanuPratapKeywordFramework/src/main/java/com/qa/data/users.json"),
				users);

		// Java Object to json in String--Marshelling

		String usersJsonString = mapper.writeValueAsString(users);

		System.out.println(usersJsonString);
		closeablehttpresponse = restclient.post(url, usersJsonString, headerMap);

		
		//validate response
		
		// 1. get status code
		int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();

		Assert.assertEquals(statuscode, Response_status_code_201);

		// 2. JsonString----UTF---this is a readable standard format

		String resposeString = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");

		// response String will be converted into JSON object and stored in
		// responseJson
		JSONObject responseJson = new JSONObject(resposeString);
		
		System.out.println("Response from API is"+ responseJson);
		
		
		//JSON to java object--unmarshelling
		 Users userResobj = mapper.readValue(resposeString, Users.class);//actual user object
		
		// System.out.println(userobj);

		 Assert.assertTrue(users.getName().equals(userResobj.getName()));
		 
		 Assert.assertTrue(users.getJob().equals(userResobj.getJob()));

	}
}
