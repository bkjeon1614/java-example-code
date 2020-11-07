package com.example.bkjeon.common.model;

import com.example.bkjeon.common.enums.ResponseResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseMessage {
    private ResponseResult result;
    private String message;
    private Integer totalCnt;
    private Object contents;
    private Object params;

    public ApiResponseMessage(ResponseResult result, String message, Object contents) {
        this.result = result;
        this.message  = message;
        this.contents = contents;
    }
}
