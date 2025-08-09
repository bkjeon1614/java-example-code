package com.bkjeon.codebase.adapter.out.persistence.repository;

import com.bkjeon.codebase.adapter.out.persistence.entity.AdEntity;

import java.util.List;

public interface AdCustomRepository {

    List<AdEntity> findTopAvailableAdsByLimit(int size);

}