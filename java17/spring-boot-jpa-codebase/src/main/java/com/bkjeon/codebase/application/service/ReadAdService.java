package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.application.command.ReadAdCommand;
import com.bkjeon.codebase.application.port.in.ReadAdUseCase;
import com.bkjeon.codebase.application.port.out.ReadAdPort;
import com.bkjeon.codebase.domain.model.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdService implements ReadAdUseCase {

    private final ReadAdPort readAdPort;

    /**
     * 광고 조회
     * @param command 광고를 조회하기 위한 Command Object
     * @return 광고 조회 리스트
     */
    @Override
    public List<Ad> findTopAvailableAdvertisementsByReward(ReadAdCommand command) {
        return readAdPort.findTopAvailableAdsByLimit(command.getSize());
    }

}
