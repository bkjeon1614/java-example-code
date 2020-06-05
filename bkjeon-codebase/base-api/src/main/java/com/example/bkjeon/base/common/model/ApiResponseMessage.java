package com.example.bkjeon.base.common.model;

import com.example.bkjeon.base.common.enums.ResponseResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class ApiResponseMessage implements Serializable {
    private static final long serialVersionUID = 1607074836000632326L;
    private ResponseResult result;
    private String message;
    private String redirectUrl;
    private Object contents;
    private String key;
    private Object params;

    public ApiResponseMessage(ResponseResult result, String message, String redirectUrl) {
        this.result = result;
        if (ResponseResult.FAIL.equals(result)) {
            this.message = "[ERROR] 오류가 발생하였습니다.";
        } else {
            this.message  = message;
        }
        this.redirectUrl = redirectUrl;
    }
}
