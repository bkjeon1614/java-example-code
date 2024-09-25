package com.example.bkjeon.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiResponse<T> {

    private int statusCode;
    private String responseMessage;
    private T param;
    private T data;

    public static<T> ApiResponse<T> res(
        final int statusCode,
        final String responseMessage,
        final T param,
        final T responseData
    ) {
        return ApiResponse.<T>builder()
            .statusCode(statusCode)
            .responseMessage(responseMessage)
            .param(param)
            .data(responseData)
            .build();
    }

}
