package com.bkjeon.example.common.enums;

import lombok.Getter;

@Getter
public enum ResponseResult {

    SUCCESS("성공", "SUCCESS"),
    FAIL("실패", "FAIL");

    private String text;
    private String type;

    ResponseResult(String text, String type) {
        this.text = text;
        this.type = type;
    }
}
