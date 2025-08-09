package com.bkjeon.codebase.application.port.in;

import com.bkjeon.codebase.application.command.ReadAdCommand;
import com.bkjeon.codebase.domain.model.Ad;

import java.util.List;

public interface ReadAdUseCase {

    List<Ad> findTopAvailableAdvertisementsByReward(ReadAdCommand readAdCommand);

}