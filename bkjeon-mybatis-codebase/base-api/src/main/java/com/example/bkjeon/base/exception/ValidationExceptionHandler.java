package com.example.bkjeon.base.exception;

import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ValidationExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errorMap = new HashMap<>();
		ex.getBindingResult().getAllErrors()
			.forEach(c -> errorMap.put(((FieldError) c).getField(), c.getDefaultMessage()));

		log.warn("Validation Error. when {}, msg {}", ex.getMessage(), ex);

		return new ResponseEntity(
			ApiResponse.res(
				HttpStatus.BAD_REQUEST.value(),
				ResponseResult.FAIL.getText(),
				null,
				errorMap
			),
			HttpStatus.BAD_REQUEST
		);
	}

}