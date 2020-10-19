package com.example.bkjeon.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("v1")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.bkjeon.base.services.api.v1"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo("Code Base APIs V1(Latest)", "Code Base APIs..", "v1"));
    }

    @Bean
    public Docket internalApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("v2")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.bkjeon.base.services.api.v2"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo("Code Base APIs V2", "Code Base APIs..", "v2"));
    }

    private ApiInfo apiInfo(String title, String description, String version) {
        Contact contact = new Contact("bong keun - jeon", "https://bkjeon1614.tistory.com", "gcijdfdo@gmail.com");

        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
            .termsOfServiceUrl("terms of controller url")
            .license("all rights reserved bong keun - jeon")
            .licenseUrl("https://bkjeon1614.tistory.com")
            .contact(contact)
            .build();
    }


}
