package com.bkjeon.codebase.adapter.out.persistence.repository;

import com.bkjeon.codebase.domain.model.AdParticipation;

import java.time.LocalDateTime;
import java.util.List;

public interface AdParticipationCustomRepository {

    List<AdParticipation> findAdParticipationList(
        long userId, LocalDateTime startDate, LocalDateTime endDate, int offset, int size);

}
