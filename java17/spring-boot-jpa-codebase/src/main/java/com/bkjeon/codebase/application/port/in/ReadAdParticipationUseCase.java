package com.bkjeon.codebase.application.port.in;

import com.bkjeon.codebase.application.command.ReadAdParticipationCommand;
import com.bkjeon.codebase.domain.model.AdParticipation;

import java.util.List;

public interface ReadAdParticipationUseCase {

    List<AdParticipation> findAdParticipationList(ReadAdParticipationCommand readAdParticipationCommand);

}