package com.bkjeon.codebase.application.port.out;

import com.bkjeon.codebase.domain.model.AdParticipation;

import java.time.LocalDateTime;
import java.util.List;

public interface ReadAdParticipationPort {

    List<AdParticipation> findAdParticipationList(
        long userId, LocalDateTime startDate, LocalDateTime endDate, int offset, int size);

}