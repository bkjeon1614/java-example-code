package com.example.bkjeon.base.services.api.v2.crud;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v2/crud")
public class CrudControllerV2 {

    @ApiOperation("Request Get API")
    @GetMapping("examples")
    public String getCallMethod() {
        return "Request Get";
    }

    @ApiOperation("Request Post API")
    @PostMapping("examples")
    public String setCallMethod() {
        return "Request Post";
    }

    @ApiOperation("Request Put API")
    @PutMapping("examples")
    public String putCallMethod() {
        return "Request Put";
    }

    @ApiOperation("Request Patch API")
    @PatchMapping("examples")
    public String patchCallMethod() {
        return "Request Patch";
    }

    @ApiOperation("Request Del API")
    @DeleteMapping("examples")
    public String delCallMethod() {
        return "Request Delete";
    }

}
