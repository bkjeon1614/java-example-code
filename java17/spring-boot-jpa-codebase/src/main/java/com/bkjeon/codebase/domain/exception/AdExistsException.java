package com.bkjeon.codebase.domain.exception;

import com.bkjeon.codebase.domain.model.enums.AdResponseCode;

public class AdExistsException extends AdServiceException {

    public AdExistsException() {
        super(AdResponseCode.ADVERTISEMENT_NAME_ALREADY_EXISTS);
    }

}