package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

public class MasterAPITest {
	
	
	@Test(description="Verify if the Master API is giving correct response", groups= {"api", "regression", "smoke"})
	public void masterAPITest() {
		
		given()
			.spec(requestSpecWithAuth(FD))
		.when()
			.post("/master")
		.then()
			.spec(responseSpec_OK())
			.body("message", equalTo("Success"))
			.body("data", notNullValue())
			.body("data",hasKey("mst_oem"))
			.body("data", hasKey("mst_model"))
			.body("$", hasKey("message"))
			.body("$", hasKey("data"))
			.body("data.mst_oem.size()", greaterThan(0))
			.body("data.mst_model.size()", greaterThan(0))
			.body("data.mst_oem.id", everyItem(notNullValue()))
			.body("data.mst_oem.name", notNullValue())
			.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));			
			
		
	}
	
	
	@Test(description="Verify if the Master API is giving correct status code for invalid token", groups= {"api", "negative", "regression", "smoke"})
	public void illegalAccessToken() {
		
		given()
		.spec(SpecUtil.requestSpec())
	.when()
		.post("master")
	.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
		
	}

}
