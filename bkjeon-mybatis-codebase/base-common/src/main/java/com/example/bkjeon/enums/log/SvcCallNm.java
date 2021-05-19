package com.example.bkjeon.enums.log;

import com.example.bkjeon.enums.validation.EnumTypeValueInterface;
import lombok.Getter;

/**
 * 서비스명
 * OMT: omt(admin)
 * OYEZ: 올영EZ
 * LOUNGE: 올리브라운지
 */
@Getter
public enum SvcCallNm implements EnumTypeValueInterface {

    OMT("omt"),
    OYEZ("oyez"),
    LOUNGE("lounge");

    private String typeValue;

    SvcCallNm(String typeValue) {
        this.typeValue = typeValue;
    }

    public String value() {
        return typeValue;
    }

}