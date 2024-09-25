package com.example.bkjeon.base.api.v1.controller.example;

import com.example.bkjeon.base.api.v1.service.example.ExternalService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/external")
@RequiredArgsConstructor
public class ExternalSample {

    private final ExternalService externalService;

    @ApiOperation("카카오톡 선물하기 리스트 API 호출 예제")
    @GetMapping("kakaoSamples")
    public String getKakaoSampleData() {
        return externalService.getKakaoSampleData();
    }

}
