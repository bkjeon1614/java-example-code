package com.example.bkjeon.base.exception.auth;

import com.example.bkjeon.base.common.enums.ResponseResult;
import com.example.bkjeon.base.common.model.ApiResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.example.bkjeon.base.services.api")
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ApiResponse bindException(AuthException exception) {
        ApiResponse result = new ApiResponse(ResponseResult.FAIL, null, null);
        // result.setMessage(exception.getMessage());
        // result.setStatusCode(exception.getStatusCode());
        return result;
    }

}