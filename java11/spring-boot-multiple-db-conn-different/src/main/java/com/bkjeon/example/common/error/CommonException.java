package com.bkjeon.example.common.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonException extends RuntimeException {
  private final HttpStatus httpStatus;

  public CommonException() {
    this(null, null);
  }

  public CommonException(HttpStatus httpStatus) {
    this(null, httpStatus);
  }

  public CommonException(String message) {
   this(message, null);
  }

  public CommonException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
