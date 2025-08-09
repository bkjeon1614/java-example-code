package com.bkjeon.codebase.adapter.out.persistence.repository;

import com.bkjeon.codebase.adapter.out.persistence.entity.AdParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdParticipationRepository extends JpaRepository<AdParticipationEntity, Long>,
    AdParticipationCustomRepository {

}
