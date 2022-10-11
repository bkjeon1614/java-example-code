package com.example.bkjeon.base.services.api.v1.setting;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/crud")
@RequiredArgsConstructor
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
