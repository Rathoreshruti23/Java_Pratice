package com.shrunity.Itfirm;

import com.shrunity.Itfirm.DTO.ProductDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItfirmApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItfirmApplication.class, args);
		System.out.println("hello");
		new ProductDTO(1, "Laptop", "abc@example.com", 1234567890);

	}

}
