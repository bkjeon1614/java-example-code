package com.example.bkjeon.base.controller.external;

import com.example.bkjeon.base.service.external.ExternalService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ExternalApiSample {

    private final ExternalService externalService;

    @ApiOperation("카카오톡 선물하기 리스트 API 호출 예제")
    @GetMapping
    public String getKakaoSampleData() {
        return externalService.getKakaoSampleData();
    }

}
