package com.example.bkjeon.base.exception.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.bkjeon.enums.exception.ErrorCode;
import com.example.bkjeon.model.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

	/**
	 * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
	 * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
	 * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> errorMap = new HashMap<>();
		e.getBindingResult().getAllErrors()
			.forEach(c -> errorMap.put(((FieldError) c).getField(), c.getDefaultMessage()));

		StringBuilder sb = new StringBuilder();
		sb.append(ErrorCode.INVALID_INPUT_VALUE_BINDING_ERROR.getMessage());
		sb.append(" (");
		for (Object o: errorMap.entrySet()) {
			sb.append(o);
			break;
		}
		sb.append(")");

		log.error("=================== MethodArgumentNotValidException Error !!: {}", e);

		return ApiResponse.builder()
			.statusCode(ErrorCode.INVALID_INPUT_VALUE_BINDING_ERROR.getStatus())
			.responseMessage(sb.toString())
			.build();
	}

	/**
	 * @ModelAttribute 으로 binding error 발생시 BindException 발생한다.
	 * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
	 */
	@ExceptionHandler(BindException.class)
	private ApiResponse handleBindException(BindException e) {
		log.error("=================== BindException Error !!", e);
		final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE_BINDING_ERROR, e.getBindingResult());
		return ApiResponse.builder()
			.statusCode(response.getStatus())
			.responseMessage(response.getMessage())
			.build();
	}

	/**
	 * enum type 일치하지 않아 binding 못할 경우 발생
	 * 주로 @RequestParam Enum binding 못했을 경우 발생
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	private ApiResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		log.error("=================== MethodArgumentTypeMismatchException Error !!", e);
		return ApiResponse.builder()
			.statusCode(ErrorCode.METHOD_ARGUMENT_TYPE_ENUM_BINDING_MISMATCH.getStatus())
			.responseMessage(ErrorCode.METHOD_ARGUMENT_TYPE_ENUM_BINDING_MISMATCH.getMessage())
			.build();
	}

	/**
	 * 지원하지 않은 HTTP method 호출 할 경우 발생
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ApiResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		log.error("=================== HttpRequestMethodNotSupportedException Error !!", e);
		return ApiResponse.builder()
			.statusCode(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
			.responseMessage(ErrorCode.METHOD_NOT_ALLOWED.getMessage())
			.build();
	}

	/**
	 * 게시물 관련 Custom Exception
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BoardException.class)
	private ApiResponse handleBoardException(BoardException e) {
		log.error("=================== BoardException Error !!", e);
		final ErrorCode errorCode = e.getErrorCode();
		final ErrorResponse response = ErrorResponse.of(errorCode);

		return ApiResponse.builder()
			.statusCode(response.getStatus())
			.responseMessage(response.getMessage())
			.build();
	}

	@ExceptionHandler(NullPointerException.class)
	private ApiResponse handleException(NullPointerException e) {
		log.error("=================== NullPointerException Error !!", e);
		return ApiResponse.builder()
			.statusCode(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
			.responseMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
			.build();
	}

	/*
	@ExceptionHandler(Exception.class)
	private ApiResponse handleException(Exception e) {
		log.error("=================== Exception Error !!", e);
		return ApiResponse.builder()
			.statusCode(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
			.responseMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
			.build();
	}
	 */

}
