package com.example.bkjeon.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket adminApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("v1")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.bkjeon.base.api.v1"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo("Code Base APIs V1(Latest)", "Code Base APIs..", "v1"))
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()));
    }

    @Bean
    public Docket adminApiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("v2")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.bkjeon.base.api.v2"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo("Code Base APIs V2(New)", "Code Base APIs..", "v2"))
            .securityContexts(Arrays.asList(securityContext()))
            .securitySchemes(Arrays.asList(apiKey()));
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

    private ApiKey apiKey() {
        return new ApiKey("JWT", "ACCESS-TOKEN", "header");
    }

    private SecurityContext securityContext() {
        return springfox
                .documentation
                .spi.service
                .contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

}
