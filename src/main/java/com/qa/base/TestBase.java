package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {
	
	public int Response_status_code_200 = 200;
	public int Response_status_code_201 = 201;


	public Properties prop;

	public TestBase() throws IOException {//constructor

		prop = new Properties();

		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties");

		prop.load(ip);
	}
	

}
