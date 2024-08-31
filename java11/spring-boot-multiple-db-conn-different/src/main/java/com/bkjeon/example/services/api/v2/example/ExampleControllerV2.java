package com.bkjeon.example.services.api.v2.example;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v2/example")
public class ExampleControllerV2 {

    @ApiOperation("Example Get API")
    @GetMapping
    public String getCallMethod() {
        return "Example Get";
    }

    @ApiOperation("Example Post API")
    @PostMapping
    public String setCallMethod() {
        return "Example Post";
    }

    @ApiOperation("Example Patch API")
    @PatchMapping
    public String patchCallMethod() {
        return "Example Patch";
    }

    @ApiOperation("Example Del API")
    @DeleteMapping
    public String delCallMethod() {
        return "Example Delete";
    }

}
