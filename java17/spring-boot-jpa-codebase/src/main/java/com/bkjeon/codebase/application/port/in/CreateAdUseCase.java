package com.bkjeon.codebase.application.port.in;

import com.bkjeon.codebase.application.command.CreateAdCommand;
import com.bkjeon.codebase.domain.model.Ad;

public interface CreateAdUseCase {

    Ad createAd(CreateAdCommand createAdCommand);

}