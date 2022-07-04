package com.bkjeon.admin.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bkjeon.admin.api.service.ApiSampleService;
import com.bkjeon.admin.entity.Sample;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {

    private final ApiSampleService apiSampleService;

    @GetMapping
    public List<Sample> getSamples() {
        return apiSampleService.getSamples();
    }

    @GetMapping("{sampleId}")
    public Sample getSample(
        @PathVariable Long sampleId
    ) {
        return apiSampleService.getSample(sampleId);
    }

    @PostMapping
    public Sample setSample(
        @RequestBody Sample command
    ) {
        return apiSampleService.setSample(command);
    }

    @PutMapping("{sampleId}")
    public Sample putSample(
        @PathVariable Long sampleId,
        @RequestBody Sample command
    ) {
        return apiSampleService.putSample(sampleId, command);
    }

    @DeleteMapping("{sampleId}")
    public void delSample(
        @PathVariable Long sampleId
    ) {
        apiSampleService.delSample(sampleId);
    }

}