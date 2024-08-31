package com.example.bkjeon.base.exception.error;

import com.example.bkjeon.enums.exception.ErrorCode;

import lombok.Getter;

@Getter
public class BoardException extends RuntimeException {

	private ErrorCode errorCode;

	public BoardException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BoardException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

}