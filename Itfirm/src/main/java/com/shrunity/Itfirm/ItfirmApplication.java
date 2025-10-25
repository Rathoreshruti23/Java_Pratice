package com.shrunity.Itfirm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static io.restassured.RestAssured.given;

@SpringBootApplication
public class ItfirmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItfirmApplication.class, args);
		System.out.println("hello");
		given().baseUri("http://localhost:8080").header("Content-Type","application/json").header("Authorization","Bearer <token>")
				.when().get("/api/product").then().statusCode(200);


	}

}
