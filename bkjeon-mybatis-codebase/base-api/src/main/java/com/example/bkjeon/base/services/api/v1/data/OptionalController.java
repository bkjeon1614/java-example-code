package com.example.bkjeon.base.services.api.v1.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.entity.data.stream.StreamUser;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/data/optional")
public class OptionalController {

    @ApiOperation("isPresent(), ifPresent() 사용")
    @GetMapping("isPresentOrIfPresent")
    public void isPresentOrIfPresent() {
        List<StreamUser> list = Arrays.asList(
            new StreamUser("A", 30),
            new StreamUser("BB", 20),
            new StreamUser("C", 10),
            new StreamUser("DD", 20),
            new StreamUser("E", 20)
        );

        // isPresent() 활용 -> Boolean 타입, Optional 객체가 값을 가지고 있다면 true, 값이 없다면 false
        Optional<StreamUser> userFindFirst = list.stream().findFirst();
        if (userFindFirst.isPresent()) {
            log.info(">>>>>>>>>>>>>>> 존재 O");
        } else {
            log.info(">>>>>>>>>>>>>>> 존재 X");
        }

        // ifPresent() 활용 -> void 타입, Optional 객체가 값을 가지고 있으면 실행 값이 없으면 넘어감
        Optional<List<StreamUser>> userList = Optional.of(list);
        userList.ifPresent(user -> {
            log.info(">>>>>>>>>>>>>>>>>>>> 존재");
        });
    }

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

        List<String> testDataList4 = null;
        log.info("============ testData4: {}", Optional.ofNullable(testDataList4).isPresent()); // false
    }

}
