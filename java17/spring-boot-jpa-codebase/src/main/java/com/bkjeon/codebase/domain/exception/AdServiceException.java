package com.bkjeon.codebase.domain.exception;

import com.bkjeon.codebase.domain.model.enums.AdResponseCode;
import lombok.Getter;

@Getter
public class AdServiceException extends RuntimeException {

    private final int code;

    public AdServiceException(AdResponseCode responseCode) {
        super(responseCode.getMessage());
        this.code = responseCode.getCode();
    }

}
