package com.example.bkjeon.base.services.api.v1.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/data/optional")
public class OptionalController {

    @ApiOperation("[], null 일 경우 예외처리 및 List<String> 일 때 String.join 함수를 통한 데이터 가공")
    @GetMapping("listStringEmptyFilterCommaValue")
    public void listStringEmptyFilterCommaValue() {
        List<String> testDataList1 = new ArrayList<>();
        String testData1 = Optional.ofNullable(testDataList1)
            .filter(s -> !s.isEmpty())
            .map(s -> String.join(",", s))
            .orElse(null);
        log.info("============ testData1: {}", testData1);

        List<String> testDataList2 = null;
        String testData2 = Optional.ofNullable(testDataList2)
            .filter(s -> !s.isEmpty())
            .map(s -> String.join(",", s))
            .orElse(null);
        log.info("============ testData2: {}", testData2);

        List<String> testDataList3 = new ArrayList<>();
        testDataList3.add("TEST1");
        testDataList3.add("TEST2");
        testDataList3.add("TEST3");
        String testData3 = Optional.ofNullable(testDataList3)
            .filter(s -> !s.isEmpty())
            .map(s -> String.join(",", s))
            .orElse(null);
        log.info("============ testData3: {}", testData3);
    }

}
