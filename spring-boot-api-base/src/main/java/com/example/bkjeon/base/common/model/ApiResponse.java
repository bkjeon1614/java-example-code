package com.example.bkjeon.base.common.model;

import com.example.bkjeon.base.common.enums.ResponseResult;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Setter
public class ApiResponse implements Serializable {
    private Integer statusCode = 200;
    private ResponseResult result;
    private String message;
    private String redirectUrl;
    private Integer totalCnt;
    private Object contents;
    private Object params;

    public ApiResponse(ResponseResult result) {
        this.result = result;
    }

    public ApiResponse(ResponseResult result, String message) {
        this.result = result;
        if (ResponseResult.FAIL.equals(result)) {
            this.message = "[ERROR] 오류가 발생하였습니다.";
        }
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        }
    }

    public ApiResponse(
        ResponseResult result,
        String message,
        String redirectUrl
    ) {
        this.result = result;
        if (ResponseResult.FAIL.equals(result)) {
            this.message = "[ERROR] 오류가 발생하였습니다.";
        } else {
            this.message = message;
        }
        this.redirectUrl = redirectUrl;
    }

}
