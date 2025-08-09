package com.bkjeon.codebase.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답 코드
 */
@Getter
@AllArgsConstructor
public enum AdResponseCode {

    ADVERTISEMENT_NAME_ALREADY_EXISTS(HttpStatus.BAD_REQUEST.value(), "이미 존재하는 광고명입니다."),
    ADVERTISEMENT_PARTICIPATION_NOT_ALLOWED(
        HttpStatus.FORBIDDEN.value(), "광고 참여 가능 횟수가 0이면 참여할 수 없습니다."),
    ADVERTISEMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "해당 광고 ID가 존재하지 않습니다.");

    private final int code;
    private final String message;

}