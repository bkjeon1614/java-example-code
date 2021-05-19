package com.example.bkjeon.enums.log;

import com.example.bkjeon.enums.validation.EnumTypeValueInterface;
import lombok.Getter;

/**
 * 로그 저장 타입
 * ACTION: 사용자 액션 로그
 */
@Getter
public enum LogType implements EnumTypeValueInterface {

    ACTION_SELECT("select"),
    ACTION_CREATE("create"),
    ACTION_UPDATE("update"),
    ACTION_DELETE("delete");

    private String typeValue;

    LogType(String typeValue) {
        this.typeValue = typeValue;
    }

    public String value() {
        return typeValue;
    }

}