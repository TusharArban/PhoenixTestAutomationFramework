package com.api.tests;

import static com.api.constant.Role.FD;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;
import com.api.utils.SpecUtil;

import io.restassured.http.ContentType;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {

		
		//creating all payload object
		Customer customer = new Customer("Tushar", "arban", "9022839909", "", "tushararbansdet@gmail.com", "");
		CustomerAddress customerAddress = new CustomerAddress("Sector 17", "Shyam", "kamothe", "Navi Mumbai", "Panvel",
				"410209", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2025-09-30T18:30:00.000Z", "12929801993562",
				"12929801993562", "12929801993562", "2025-09-30T18:30:00.000Z", 1, 2);
		Problems problems = new Problems(1, "Battery Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0] = problems;
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsArray);

		given()
			.spec(SpecUtil.requestSpecWithAuth(FD, createJobPayload))
			.log().all()
		.when()
			.post("/job/create")
		.then()
			.spec(SpecUtil.responseSpec_OK());
	}

}
