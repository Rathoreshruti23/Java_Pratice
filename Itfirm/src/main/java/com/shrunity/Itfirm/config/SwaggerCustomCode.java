package com.shrunity.Itfirm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerCustomCode {

    @Bean
    public OpenAPI apiCustomization() {
        return new OpenAPI().info(
                new Info()
                        .title("Product Application")
                        .version("1.0")
                        .description("This is an e-commerce application created for practice purpose")
                        .summary("An e-commerce application enables users to browse products, add them to a cart, place orders, and make secure payments online. " +
                                "It typically includes modules for user authentication, product catalog management, order processing, inventory tracking, and payment integration. " +
                                "Admins can manage products, view sales analytics, and handle customer queries, " +
                                "while customers can track their orders and view purchase history — providing a complete digital shopping experience.")
        );
    }
}
