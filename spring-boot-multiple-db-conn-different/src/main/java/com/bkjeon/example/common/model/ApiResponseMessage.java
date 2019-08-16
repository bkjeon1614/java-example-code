package com.bkjeon.example.common.model;

import com.bkjeon.example.common.enums.ResponseResult;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResponseMessage implements Serializable {
    private static final long serialVersionUID = 9189489544166339048L;
    private ResponseResult result;
    private String message;
    private String redirectUrl;
    private Integer totalCnt;
    private Object contents;
    private String key;
    private Object params;

    public ApiResponseMessage() {}

    public ApiResponseMessage(
        ResponseResult result,
        String message,
        String redirectUrl
    ) {
        this.result = result;
        if (ResponseResult.FAIL.equals(result)) {
            this.message = "ERROR";
        } else {
            this.message  = message;
        }
        this.redirectUrl = redirectUrl;
    }
}
