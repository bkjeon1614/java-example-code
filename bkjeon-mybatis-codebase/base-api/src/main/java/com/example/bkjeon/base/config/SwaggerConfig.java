package com.example.bkjeon.base.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
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
        final List<ResponseMessage> globalResponses = Arrays.asList(
            new ResponseMessageBuilder().code(200).message(HttpStatus.OK.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(400).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(401).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(403).message(HttpStatus.FORBIDDEN.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(500).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).build());

        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)  // false 로 설정하면 Swagger 에서 제공해주는 응답코드(200, 401, 403, 404)에 대한 기본 메시지를 제거해준다.
            .globalResponseMessage(RequestMethod.GET, globalResponses)
            .globalResponseMessage(RequestMethod.POST, globalResponses)
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
