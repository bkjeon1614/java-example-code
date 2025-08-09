package com.bkjeon.codebase.domain.exception;

import com.bkjeon.codebase.domain.model.enums.AdResponseCode;

public class AdEmptyException extends AdServiceException {

    public AdEmptyException() {
        super(AdResponseCode.ADVERTISEMENT_NOT_FOUND);
    }

}