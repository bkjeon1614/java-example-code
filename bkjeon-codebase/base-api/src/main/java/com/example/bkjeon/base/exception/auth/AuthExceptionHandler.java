package com.example.bkjeon.base.exception.auth;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice("com.example.bkjeon.base.services.api")
public class AuthExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public void bindException(AuthException exception) {
        // ApiResponse result = new ApiResponse(ResponseResult.FAIL, null, null);
        // result.setMessage(exception.getMessage());
        // result.setStatusCode(exception.getStatusCode());
        // return result;
    }

}