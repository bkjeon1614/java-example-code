package com.example.bkjeon.base.config;

import com.example.bkjeon.enums.exception.ErrorCode;
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
            .useDefaultResponseMessages(false)  // false 로 설정하면 Swagger 에서 제공해주는 응답코드(200, 401, 403, 404)에 대한 기본 메시지를 제거해준다.
            .globalResponseMessage(RequestMethod.GET, getResponseMessageList())
            .globalResponseMessage(RequestMethod.POST, getResponseMessageList())
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
            .useDefaultResponseMessages(false)  // false 로 설정하면 Swagger 에서 제공해주는 응답코드(200, 401, 403, 404)에 대한 기본 메시지를 제거해준다.
            .globalResponseMessage(RequestMethod.GET, getResponseMessageList())
            .globalResponseMessage(RequestMethod.POST, getResponseMessageList())
            .groupName("v2")
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.bkjeon.base.services.api.v2"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo("Code Base APIs V2", "Code Base APIs..", "v2"));
    }

    private List<ResponseMessage> getResponseMessageList() {
        return Arrays.asList(
            new ResponseMessageBuilder().code(200).message(HttpStatus.OK.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(400).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(401).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(403).message(HttpStatus.FORBIDDEN.getReasonPhrase()).build(),
            new ResponseMessageBuilder().code(ErrorCode.BAD_REQUEST.getStatus()).message(ErrorCode.BAD_REQUEST.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.METHOD_ARGUMENT_TYPE_ENUM_BINDING_MISMATCH.getStatus()).message(ErrorCode.METHOD_ARGUMENT_TYPE_ENUM_BINDING_MISMATCH.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.UNAUTHORIZED.getStatus()).message(ErrorCode.UNAUTHORIZED.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.FORBIDDEN.getStatus()).message(ErrorCode.FORBIDDEN.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.NOT_FOUND.getStatus()).message(ErrorCode.NOT_FOUND.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.METHOD_NOT_ALLOWED.getStatus()).message(ErrorCode.METHOD_NOT_ALLOWED.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.CONFLICT.getStatus()).message(ErrorCode.CONFLICT.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.PRECONDITION_FAILED.getStatus()).message(ErrorCode.PRECONDITION_FAILED.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.TOO_MANY_REQUESTS.getStatus()).message(ErrorCode.TOO_MANY_REQUESTS.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.BAD_GATEWAY.getStatus()).message(ErrorCode.BAD_GATEWAY.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.SERVICE_UNAVAILABLE.getStatus()).message(ErrorCode.SERVICE_UNAVAILABLE.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.INVALID_INPUT_VALUE_BINDING_ERROR.getStatus()).message(ErrorCode.INVALID_INPUT_VALUE_BINDING_ERROR.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.BOARD_INSERT_FAILED.getStatus()).message(ErrorCode.BOARD_INSERT_FAILED.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.BOARD_UPDATE_FAILED.getStatus()).message(ErrorCode.BOARD_UPDATE_FAILED.getMessage()).build(),
            new ResponseMessageBuilder().code(ErrorCode.BOARD_EMPTY.getStatus()).message(ErrorCode.BOARD_EMPTY.getMessage()).build()
        );
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
