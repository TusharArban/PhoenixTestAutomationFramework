package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.dataproviders.api.bean.UserBean;

public class LoginAPITestDataDrivenTest {


	@Test(description = "Verifying if login api is working for FD user",
			groups = { "api", "regression", "datadriven" },
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "LoginAPIDataProvider")
	public void loginAPITest(UserBean userBean) {

		given()
		.spec(requestSpec(userBean))
		.when()
		.post("login")
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
