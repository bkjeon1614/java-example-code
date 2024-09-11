package com.bkjeon.example.common.error;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse<T> {

  private String message;
  private List<T> errors;

  public ErrorResponse(String message) {
    this.message = message ;
  }
}
