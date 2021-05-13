package com.example.bkjeon.enums.validation;

import lombok.Getter;

/**
 * 예제 타입
 * EXAMPLE_CREATE: 저장
 * EXAMPLE_UPDATE: 수정
 * EXAMPLE_DELETE: 삭제
 */
@Getter
public enum ExampleEnumCustomType implements EnumTypeValueInterface {

    EXAMPLE_CREATE("create"),
    EXAMPLE_UPDATE("update"),
    EXAMPLE_DELETE("delete");

    private String typeValue;

    ExampleEnumCustomType(String typeValue) {
        this.typeValue = typeValue;
    }

    public String value() {
        return typeValue;
    }

}
