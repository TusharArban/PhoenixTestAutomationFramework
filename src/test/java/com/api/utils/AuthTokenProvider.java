package com.api.utils;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static com.api.utils.ConfigManager.*;

import static com.api.constant.Role.*;

import com.api.constant.Role;
import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	private AuthTokenProvider() {
		
	}
	

	public static String getToken(Role role) {
		
		UserCredentials userCreds = null;
		
		if(role == FD) {
			userCreds = new UserCredentials("iamfd","password");
		}
		else if(role == SUP) {
			userCreds = new UserCredentials("iamsup", "password");
		}
		else if(role == ENG) {
			userCreds = new UserCredentials("iameng", "password");
		}
		else if(role == QC) {
			userCreds = new UserCredentials("iamqc", "password");
		}
		
		
		
		String token = given()
			.baseUri(getProperty("BASE_URI"))
		.and()
			.contentType(ContentType.JSON)
		.and()
			.accept(ContentType.JSON)
		.and()
			.body(userCreds)
		.when()
			.post("login")
		.then()
			.log().ifValidationFails()
			.statusCode(200)
		.and()
			.body("message", equalTo("Success"))
		.and()
			.body("data.token", notNullValue())
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"))
			.time(lessThan(1500L))
		.and()
			.extract().jsonPath().getString("data.token");
		
		return token;

	}

}
