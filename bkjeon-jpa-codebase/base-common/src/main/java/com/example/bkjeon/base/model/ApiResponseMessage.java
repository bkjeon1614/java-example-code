package com.example.bkjeon.base.model;

import com.example.bkjeon.base.enums.ResponseResult;
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

    public ApiResponseMessage(ResponseResult result, String message, Object contents, Object params) {
        this.result = result;
        this.message  = message;
        this.contents = contents;
        this.params = params;
    }

    public ApiResponseMessage(ResponseResult result, String message, Object params) {
        this.result = result;
        this.message  = message;
        this.params = params;
    }

    public ApiResponseMessage(ResponseResult result, String message) {
        this.result = result;
        this.message  = message;
    }

}
