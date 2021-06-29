package com.example.bkjeon.base.api.v1.controller.example;

import com.example.bkjeon.base.api.v1.service.example.ExampleService;
import com.example.bkjeon.base.enums.ResponseResult;
import com.example.bkjeon.base.model.ApiResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/example")
@RequiredArgsConstructor
public class ExampleController {

    private final ExampleService exampleService;

    @ApiOperation("JPA 기본 예제")
    @GetMapping("boards")
    public ResponseEntity getJpaBoardList() {
        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                null,
                exampleService.getBoardList()
            ),
            HttpStatus.OK
        );
    }

    @ApiOperation("QueryDSL 예제")
    @GetMapping("queryDsl/boards")
    public ResponseEntity getQueryDslBoardList() {
        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                null,
                exampleService.getQueryDslBoardList()
            ),
            HttpStatus.OK
        );
    }

}