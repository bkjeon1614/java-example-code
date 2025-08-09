package com.bkjeon.codebase.adapter.out.persistence.repository;

import com.bkjeon.codebase.adapter.out.persistence.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<AdEntity, Long>, AdCustomRepository {

    boolean existsByName(String name);

}