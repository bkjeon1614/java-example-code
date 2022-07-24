package com.example.bkjeon.base.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleClientException(CommonException e) {
        HttpStatus httpStatus = Optional.ofNullable(e.getHttpStatus()).orElse(HttpStatus.BAD_REQUEST);
        log.info(e.getMessage());
        return getErrorResponseEntity(e.getMessage(), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error(e.getMessage(), e);
        return getErrorResponseEntity(e.getMessage(), httpStatus);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity handleMongoWriteExceptionException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return getErrorResponseEntity("Duplicated key", HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers,HttpStatus status, WebRequest request) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(fieldErrors.toArray(new FieldError[fieldErrors.size()]));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(fieldErrors.toArray(new FieldError[fieldErrors.size()]));
    }

    private ResponseEntity<ErrorResponse> getErrorResponseEntity(String message, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
    }

}
