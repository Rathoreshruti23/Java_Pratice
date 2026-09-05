package com.shrunity.Itfirm;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ItfirmApplicationTests {

	@BeforeAll
	static void setup() {
		RestAssured.baseURI = "http://localhost:8080/api/product";
	}

	@Test
	void testGetProducts() {
		given()
				.when().get("/")
				.then()
				.statusCode(200);
	}
}
