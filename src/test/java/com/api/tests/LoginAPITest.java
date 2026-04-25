package com.api.tests;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

public class LoginAPITest {
	
	private UserCredentials userCredentials;
	
	@BeforeMethod(description = "Create the payload for the Login API")
	public void setup() {
		userCredentials = new UserCredentials("iamfd","password"); 
	}
	
	@Test(description="Verifying if login api is working for FD user", groups = {"api", "regression","smoke"})
	public void loginAPITest() throws IOException {
		
		
		given()
			.spec(requestSpec(userCredentials))
		.when()
			.post("login")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
