package com.api.tests;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.utils.ConfigManager;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.constant.Role.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class MasterAPITest {
	
	
	@Test
	public void masterAPITest() {
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.header("Authorization",getToken(FD))
			.and()
			.contentType("")
			.log().uri()
			.log().headers()
			.log().method()
			.log().all()
		.when()
			.post("/master")
		.then()
			.log().all()
			.statusCode(200)
			.time(lessThan(2000L))
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
	
	
	@Test
	public void illegalAccessToken() {
		
		given()
			.baseUri(ConfigManager.getProperty("BASE_URI"))
			.and()
			.header("Authorization","")
			.contentType("")
			.log().all()
		.when()
			.post("master")
		.then()
			.log().all()
			.statusCode(401);
		
	}

}
