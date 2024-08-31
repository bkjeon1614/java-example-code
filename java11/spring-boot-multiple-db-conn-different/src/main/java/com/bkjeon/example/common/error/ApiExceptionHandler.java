package com.bkjeon.example.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Optional;

@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

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
  protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {
    log.error(e.getMessage(), e);
    BindingResult result = e.getBindingResult();
    final List<FieldError> fieldErrors = result.getFieldErrors();
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(fieldErrors.toArray(new FieldError[fieldErrors.size()]));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.error(e.getMessage(), e);
    BindingResult result = e.getBindingResult();
    final List<FieldError> fieldErrors = result.getFieldErrors();

    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(fieldErrors.toArray(new FieldError[fieldErrors.size()]));
  }

  private ResponseEntity<ErrorResponse> getErrorResponseEntity(String message, HttpStatus httpStatus) {
    return ResponseEntity.status(httpStatus).body(new ErrorResponse(message));
  }
}
