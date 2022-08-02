package com.example.bkjeon.enums.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

/**
 * 에러 코드 정의
 * > 코드값은 _ 앞의 첫자리 대문자를 따서 코드값 + Http Status Code 로 합쳐서 만듬
 * > 에러 메세지는 Common 과 각 도메인별로 관리
 */
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

	// Common
	BAD_REQUEST(HttpStatus.BAD_REQUEST.value(), "ECBR400", "Bad Request"),
	METHOD_ARGUMENT_TYPE_ENUM_BINDING_MISMATCH(HttpStatus.BAD_REQUEST.value(), "ECEBM400", "Bad Request (Method Argument Type Mismatch Error)"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "ECU401", "Unauthorized"),
	FORBIDDEN(HttpStatus.FORBIDDEN.value(), "ECF403", "Forbidden"),
	NOT_FOUND(HttpStatus.NOT_FOUND.value(), "ECN404", "Not Found"),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED.value(), "ECMNA405", "Method Not Allowed"),
	CONFLICT(HttpStatus.CONFLICT.value(), "ECC409", "Conflict"),
	PRECONDITION_FAILED(HttpStatus.PRECONDITION_FAILED.value(), "ECPF412", "Precondition Failed"),
	TOO_MANY_REQUESTS(HttpStatus.TOO_MANY_REQUESTS.value(), "ECTMR429", "Too Many Requests"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "ECISE500", "Internal Server Error"),
	BAD_GATEWAY(HttpStatus.BAD_GATEWAY.value(), "ECBG502", "Bad Gateway"),
	SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE.value(), "ECSU503", "Service Unavailable"),
	INVALID_INPUT_VALUE_BINDING_ERROR(HttpStatus.BAD_REQUEST.value(), "ECIIVBE400", "Invalid Input Value Binding"),

	// Board
	BOARD_INSERT_FAILED(HttpStatus.BAD_REQUEST.value(), "EBBIF001", "Board Data Insert Error"),
	BOARD_UPDATE_FAILED(HttpStatus.BAD_REQUEST.value(), "EBBUF002", "Board Data Update Error"),
	BOARD_EMPTY(HttpStatus.BAD_REQUEST.value(), "EBBE003", "Board Data Empty");

	private final String code;
	private final String message;
	private int status;

	ErrorCode(final int status, final String code, final String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

}