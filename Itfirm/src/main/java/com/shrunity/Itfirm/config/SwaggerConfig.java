package com.shrunity.Itfirm.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

        @Bean
        public GroupedOpenApi v1Api() {
            return GroupedOpenApi.builder()
                    .group("v1")
                    .pathsToMatch("/api/v1/**")
                    .build();
        }

        @Bean
        public GroupedOpenApi v2Api() {
            return GroupedOpenApi.builder()
                    .group("v2")
                    .pathsToMatch("/api/v2/**")
                    .build();
        }
    }
