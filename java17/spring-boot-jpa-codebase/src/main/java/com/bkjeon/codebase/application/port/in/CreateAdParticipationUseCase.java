package com.bkjeon.codebase.application.port.in;

import com.bkjeon.codebase.application.command.CreateAdParticipationCommand;
import com.bkjeon.codebase.domain.model.AdParticipation;

public interface CreateAdParticipationUseCase {

    AdParticipation createAdParticipation(CreateAdParticipationCommand createAdParticipationCommand);

}