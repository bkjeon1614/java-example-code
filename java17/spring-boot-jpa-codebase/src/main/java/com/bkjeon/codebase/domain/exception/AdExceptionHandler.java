package com.bkjeon.codebase.domain.exception;

import com.bkjeon.codebase.adapter.in.web.v1.dto.common.reponse.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AdExceptionHandler {

    @ExceptionHandler(AdServiceException.class)
    private ResponseEntity<ApiResponse<Object>> handleAdServiceException(AdServiceException e) {
        log.error("=================== handleAdServiceException Error: " + e);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.builder().code(e.getCode()).message(e.getMessage()).build());
    }

}