package com.api.tests;

import static org.hamcrest.Matchers.*;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constant.Role.*;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {
	
	@Test
	public void verifyCountAPIResponse() {
		
		//request specification -> configure api request
		
		given()
			.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success"))
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
			.spec(SpecUtil.requestSpec())
		.when()
			.get("/dashboard/count")
		.then()
			.spec(SpecUtil.responseSpec_TEXT(401));
	}
	
	

}
