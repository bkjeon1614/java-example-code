package com.bkjeon.codebase.adapter.in.web.v1.dto.common.reponse;

import com.bkjeon.codebase.domain.model.enums.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "API 공통 응답 모델")
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    @Schema(description = "상태 코드")
    private int code;

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "응답 결과")
    private T data;

    public static<T> ApiResponse<T> res(final T responseData) {
        int statusCode = ResponseCode.OK.getCode();
        String responseMessage = ResponseCode.OK.getMessage();

        if (responseData == null) {
            statusCode = ResponseCode.NOT_CONTENT.getCode();
            responseMessage = ResponseCode.NOT_CONTENT.getMessage();
        }

        return ApiResponse.<T>builder()
            .code(statusCode)
            .message(responseMessage)
            .data(responseData)
            .build();
    }

    public static<T> ApiResponse<T> res(final int statusCode, final String responseMessage) {
        return ApiResponse.<T>builder()
            .code(statusCode)
            .message(responseMessage)
            .build();
    }

    public static<T> ApiResponse<T> res(final int statusCode, final String responseMessage, final T responseData) {
        return ApiResponse.<T>builder()
            .code(statusCode)
            .message(responseMessage)
            .data(responseData)
            .build();
    }

}