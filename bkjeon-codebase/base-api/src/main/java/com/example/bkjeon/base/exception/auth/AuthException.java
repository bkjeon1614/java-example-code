package com.example.bkjeon.base.exception.auth;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthException extends RuntimeException {

    private String exceptionMsg;
    private int exceptionCode;

    public String getMessage() {
        return this.exceptionMsg;
    }

    public int getStatusCode() {
        return this.exceptionCode;
    }

}
