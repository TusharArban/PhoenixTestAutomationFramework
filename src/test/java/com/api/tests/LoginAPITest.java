package com.api.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	
	
	@Test
	public void loginAPITest() {
		
		UserCredentials userCredentials = new UserCredentials("iamfd","password"); 
		
		given()
			.baseUri("http://64.227.160.186:9000/v1")
		.and()
			.contentType(ContentType.JSON)
		.and()
			.accept(ContentType.JSON)
		.and()
			.body(userCredentials)
			.log().uri()
			.log().body()
			.log().headers()
			.log().method()
		.when()
			.post("login")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(2000L))
			.body("message", equalTo("Success"))
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
