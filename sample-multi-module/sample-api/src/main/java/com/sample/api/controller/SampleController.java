package com.sample.api.controller;

import com.sample.api.service.SampleService;
import com.sample.common.entity.Sample;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/samples")
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @ApiOperation("샘플 리스트")
    @GetMapping
    public List<Sample> getSamples() {
        return sampleService.getSamples();
    }

}