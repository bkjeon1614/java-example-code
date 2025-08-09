package com.bkjeon.codebase.application.port.out;

import com.bkjeon.codebase.domain.model.Ad;

import java.util.List;

public interface ReadAdPort {

    List<Ad> findTopAvailableAdsByLimit(int size);
    Ad findById(Long id);

}