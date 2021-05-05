package com.example.bkjeon.base.services.api.v1.example;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/crud")
@AllArgsConstructor
public class CrudController {

    private final CrudService crudService;

    @ApiOperation("Request Get API")
    @GetMapping("examples")
    public String getCallMethod() {
        return crudService.getCallMethod();
    }

    @ApiOperation("Request Post API")
    @PostMapping("examples")
    public String setCallMethod() {
        return crudService.setCallMethod();
    }

    @ApiOperation("Request Put API")
    @PutMapping("examples")
    public String putCallMethod() {
        return crudService.putCallMethod();
    }

    @ApiOperation("Request Patch API")
    @PatchMapping("examples")
    public String patchCallMethod() {
        return crudService.patchCallMethod();
    }

    @ApiOperation("Request Del API")
    @DeleteMapping("examples")
    public String delCallMethod() {
        return crudService.delCallMethod();
    }

}
