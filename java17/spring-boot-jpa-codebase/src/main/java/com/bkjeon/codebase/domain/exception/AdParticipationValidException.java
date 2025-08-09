package com.bkjeon.codebase.domain.exception;

import com.bkjeon.codebase.domain.model.enums.AdResponseCode;

public class AdParticipationValidException extends AdServiceException {

    public AdParticipationValidException() {
        super(AdResponseCode.ADVERTISEMENT_PARTICIPATION_NOT_ALLOWED);
    }

}