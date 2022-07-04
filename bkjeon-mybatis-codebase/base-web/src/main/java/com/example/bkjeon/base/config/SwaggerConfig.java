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
    public Docket apiDocs() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("v1")
            .select()
            .apis(RequestHandlerSelectors.basePackage("kr.co.oliveyoung.pda.services.api"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo("Oliveyoung Android PDA APIs", "Oliveyoung Android PDA APIs..", "latest"));
    }

    private ApiInfo apiInfo(String title, String description, String version) {
        Contact contact = new Contact("CJ Oliveyoung", "https://oliveyoung.co.kr", "bongkeun.jeon@cj.net");

        return new ApiInfoBuilder()
            .title(title)
            .description(description)
            .version(version)
            .termsOfServiceUrl("terms of controller url")
            .license("all rights reserved CJ Oliveyoung")
            .licenseUrl("https://oliveyoung.co.kr")
            .contact(contact)
            .build();
    }


}
