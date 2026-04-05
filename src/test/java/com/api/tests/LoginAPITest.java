package com.api.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import com.api.utils.SpecUtil;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;

public class LoginAPITest {
	
	
	@Test
	public void loginAPITest() throws IOException {
		
		UserCredentials userCredentials = new UserCredentials("iamfd","password"); 
		
		given()
			.spec(SpecUtil.requestSpec(userCredentials))
		.when()
			.post("login")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success"))
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
