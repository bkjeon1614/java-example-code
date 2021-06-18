package com.example.bkjeon.base.api.v1.controller.example;

import com.example.bkjeon.base.enums.ResponseResult;
import com.example.bkjeon.base.model.ApiResponseMessage;
import com.example.bkjeon.base.api.v1.service.example.ExampleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    public ApiResponseMessage getJpaBoardList() {
        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "조회가 완료되었습니다.",
            exampleService.getBoardList(),
            null
        );
    }


    @ApiOperation("QueryDSL 예제")
    @GetMapping("queryDsl/boards")
    public ApiResponseMessage getQueryDslBoardList() {
        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "조회가 완료되었습니다.",
            exampleService.getQueryDslBoardList(),
            null
        );
    }

}