package com.example.bkjeon.base.exception;

import com.example.bkjeon.common.enums.ResponseResult;
import com.example.bkjeon.common.model.ApiResponseMessage;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponseMessage handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getAllErrors()
            .forEach(c -> errorMap.put(((FieldError) c).getField(), c.getDefaultMessage()));

        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.FAIL,
            "Validation Error",
            errorMap
        );

        return result;
    }

}
