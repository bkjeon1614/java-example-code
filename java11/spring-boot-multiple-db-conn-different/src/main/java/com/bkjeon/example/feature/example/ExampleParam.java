package com.bkjeon.example.feature.example;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleParam {

    @ApiParam(value = "example name", name = "name")
    private String name;

    @ApiParam(value = "나이", name = "age")
    private Integer age;

}
