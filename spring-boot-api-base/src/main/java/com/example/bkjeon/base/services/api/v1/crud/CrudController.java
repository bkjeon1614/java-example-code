package com.example.bkjeon.base.services.api.v1.crud;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/crud")
public class CrudController {

    @ApiOperation("Request Get API")
    @GetMapping
    public String getCallMethod() {
        return "Request Get";
    }

    @ApiOperation("Request Post API")
    @PostMapping
    public String setCallMethod() {
        return "Request Post";
    }

    @ApiOperation("Request Put API")
    @PutMapping
    public String putCallMethod() {
        return "Request Put";
    }

    @ApiOperation("Request Patch API")
    @PatchMapping
    public String patchCallMethod() {
        return "Request Patch";
    }

    @ApiOperation("Request Del API")
    @DeleteMapping
    public String delCallMethod() {
        return "Request Delete";
    }

}
