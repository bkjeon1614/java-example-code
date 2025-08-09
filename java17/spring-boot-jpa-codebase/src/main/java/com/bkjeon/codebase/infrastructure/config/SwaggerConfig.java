package com.bkjeon.codebase.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "APIs V1(Latest)",
        description = "APIs..",
        version = "v1",
        contact = @Contact(name = "전봉근",
            url = "https://bkjeon1614.tistory.com",
            email = "gcijdfdo@gmail.com")))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
            .group("v1")
            .packagesToScan("com.bkjeon.codebase.adapter.in.web.v1")
            .build();
    }

}