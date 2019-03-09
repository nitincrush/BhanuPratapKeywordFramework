package com.qa.test;

import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestCliet;
import com.qa.util.TestUtil;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.TotalDigitsDocument.TotalDigits;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GetAPITest extends TestBase {

	public GetAPITest() throws IOException {
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

	@Test(priority = 1)
	public void getTest() throws ClientProtocolException, IOException {
		restclient = new RestCliet();
		closeablehttpresponse = restclient.get(url);

		// a. get status code
		int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();

		System.out.println("Statuscode: " + statuscode);

		Assert.assertEquals(statuscode, Response_status_code_200);//actual vs expected
		
		// b. JSON string
		String responsestring = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");

		
		//response String will be converted into JSON object and stored in responseJson 
		JSONObject responseJson = new JSONObject(responsestring);

		System.out.println("Response Jason from api: " + responseJson);
		// single value assertion
		
		// per page
		String perpagevalue = TestUtil.getValueByJPath(responseJson, "/per_page");//get the per_page response in the JSON object-resposeJson
		System.out.println("value per page:" + perpagevalue);
		Assert.assertEquals(Integer.parseInt(perpagevalue), 3);// actual vs expected

		// total
		String totalvalue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("value of total: " + totalvalue);
		Assert.assertEquals(Integer.parseInt(totalvalue), 12);// actual vs expected

		// get the value from JSON array
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		// c. all headers
		Header[] headerarray = closeablehttpresponse.getAllHeaders();

		HashMap<String, String> allheaders = new HashMap<String, String>();

		for (Header header : headerarray) {

			allheaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers array:" + allheaders);

	}

	@Test(priority = 2)
	public void getAPITestwithheader() throws ClientProtocolException, IOException {
		restclient = new RestCliet();

		HashMap<String, String> headerMap = new HashMap<String, String>();

		headerMap.put("Content-Type", "application/json");

		closeablehttpresponse = restclient.get(url, headerMap);

		// a. get status code
		int statuscode = closeablehttpresponse.getStatusLine().getStatusCode();
		System.out.println("Statuscode: " + statuscode);
		Assert.assertEquals(statuscode, Response_status_code_200);

		// b. Json String
		String responsestring = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("Response Jason from api: " + responseJson);
		
		// single value assertion
		// per page
		String perpagevalue = TestUtil.getValueByJPath(responseJson, "/per_page");
		System.out.println("value per page:" + perpagevalue);
		Assert.assertEquals(Integer.parseInt(perpagevalue), 3);// actual,
																// expected

		// total
		String totalvalue = TestUtil.getValueByJPath(responseJson, "/total");
		System.out.println("value of total: " + totalvalue);
		Assert.assertEquals(Integer.parseInt(totalvalue), 12);// actual,
																// expected

		// get the value from JSON array
		String lastName = TestUtil.getValueByJPath(responseJson, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJson, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJson, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJson, "/data[0]/first_name");

		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		// c. all headers
		Header[] headerarray = closeablehttpresponse.getAllHeaders();

		HashMap<String, String> allheaders = new HashMap<String, String>();

		for (Header header : headerarray) {

			allheaders.put(header.getName(), header.getValue());
		}
		System.out.println("Headers array:" + allheaders);

	}

}
