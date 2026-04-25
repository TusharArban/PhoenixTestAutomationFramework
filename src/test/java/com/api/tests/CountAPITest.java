package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

public class CountAPITest {
	
	@Test(description="Verify if the Count API is giving correct response", groups= {"api", "regression", "smoke"})
	public void verifyCountAPIResponse() {
		
		//request specification -> configure api request
		
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data",notNullValue())
			.body("data.size()",equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label", notNullValue())
			.body("data.label", Matchers.not(Matchers.blankOrNullString()))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema.json"))
			.body("data.key", containsInAnyOrder("created_today","pending_fst_assignment","pending_for_delivery"));
	
	
	}
	
	@Test(description="Verify if the Count API is giving correct status code", groups= {"api","negative","regression", "smoke"})
	public void countAPITest_MissingAuthToken() {
		given()
			.spec(requestSpec())
		.when()
			.get("/dashboard/count")
		.then()
			.spec(responseSpec_TEXT(401));
	}
	
	

}
