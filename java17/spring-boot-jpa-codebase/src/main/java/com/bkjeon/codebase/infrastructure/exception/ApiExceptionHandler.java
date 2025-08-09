package com.bkjeon.codebase.infrastructure.exception;

import com.bkjeon.codebase.adapter.in.web.v1.dto.common.reponse.ApiResponse;
import com.bkjeon.codebase.domain.model.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Validation ExceptionHandler
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ApiResponse<Object>> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = fieldErrors.get(0).getDefaultMessage() + " (" + fieldErrors.get(0).getField() + ")";

        log.warn("=================== handleMethodArgumentNotValidException Error !! " + e);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.builder().code(ResponseCode.BAD_REQUEST.getCode()).message(message).build());
    }

    /**
     * ModelAttribute Validation ExceptionHandler
     */
    @ExceptionHandler(BindException.class)
    private ResponseEntity<ApiResponse<Object>> handleBindException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String message = ResponseCode.BAD_REQUEST.getMessage() + ": " + fieldErrors.get(0).getDefaultMessage();

        log.warn("=================== handleBindException Error !!", e);

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.builder().code(ResponseCode.BAD_REQUEST.getCode()).message(message).build());
    }

    /**
     * 미 지원 HTTP method ExceptionHandler
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<ApiResponse<Object>> handleHttpRequestMethodNotSupportedException(
        HttpRequestMethodNotSupportedException e) {
        log.error("=================== handleHttpRequestMethodNotSupportedException Error !!", e);
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(ApiResponse.builder()
                .code(ResponseCode.METHOD_NOT_ALLOWED.getCode())
                .message(ResponseCode.METHOD_NOT_ALLOWED.getMessage())
                .build());
    }

    /**
     * NullPointerException ExceptionHandler
     */
    @ExceptionHandler(NullPointerException.class)
    private ResponseEntity<ApiResponse<Object>> handleNpeException(NullPointerException e) {
        log.error("=================== handleNpeException Error !!", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.builder()
                .code(ResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                .build());
    }

    /**
     * Exception ExceptionHandler
     */
    @ExceptionHandler(Exception.class)
    private ResponseEntity<ApiResponse<Object>> handleException(Exception e) {
        log.error("=================== handleException Error !!", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ApiResponse.builder()
                .code(ResponseCode.INTERNAL_SERVER_ERROR.getCode())
                .message(ResponseCode.INTERNAL_SERVER_ERROR.getMessage())
                .build());
    }

    /**
     * Request Type Validation ExceptionHandler
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidFormatException(HttpMessageNotReadableException e) {
        log.warn("=================== handleException Error !!", e);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .message(ResponseCode.BAD_REQUEST.getMessage())
                .build());
    }

}