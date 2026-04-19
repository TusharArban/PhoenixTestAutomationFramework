package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {

		
		//creating all payload object
		Customer customer = new Customer("Tushar", "arban", "9022839909", "", "tushararbansdet@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("Sector 17", "Shyam", "kamothe", "Navi Mumbai", "Panvel",
				"410209", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-09-30T18:30:00.000Z", "12929801993566",
				"12929801993566", "12929801993566", "2025-09-30T18:30:00.000Z", 1, 2);
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemsArray = new ArrayList<Problems>();
		problemsArray.add(problems);
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsArray);

		given()
			.spec(SpecUtil.requestSpecWithAuth(FD, createJobPayload))
			.log().all()
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK())
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
			.body("message", equalTo("Job created successfully. "))
			.body("data", notNullValue())
			.body("data.mst_service_location_id", equalTo(1))
			.body("data.job_number", startsWith("JOB_"));
	
	}

}
