package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIResponse() {
		
		//request specification -> configure api request
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.and()
			.header("Authorization",AuthTokenProvider.getToken(Role.FD))
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.time(lessThan(2000L))
			.body("data",notNullValue())
			.body("data.size()",equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", notNullValue())
			.body("data.label", Matchers.not(Matchers.blankOrNullString()))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"))
			.body("data.key", containsInAnyOrder("created_today","pending_fst_assignment","pending_for_delivery"));
	
	
	}
	
	@Test
	public void countAPITest_MissingAuthToken() {
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.and()
			.log().uri()
			.log().method()
			.log().headers()
		.when()
			.get("/dashboard/count")
		.then()
			.log().all()
			.statusCode(401);
	}
	
	

}
