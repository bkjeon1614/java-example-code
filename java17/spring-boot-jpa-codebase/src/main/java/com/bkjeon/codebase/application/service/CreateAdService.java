package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.application.command.CreateAdCommand;
import com.bkjeon.codebase.application.port.in.CreateAdUseCase;
import com.bkjeon.codebase.application.port.out.CreateAdPort;
import com.bkjeon.codebase.application.port.out.ExistAdPort;
import com.bkjeon.codebase.domain.exception.AdExistsException;
import com.bkjeon.codebase.domain.mapper.AdMapper;
import com.bkjeon.codebase.domain.model.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdService implements CreateAdUseCase {

    private final CreateAdPort createAdPort;
    private final ExistAdPort existAdPort;

    /**
     * 광고 생성
     * @param command 광고를 생성하기 위한 Command Object
     * @return 광고 생성 데이터
     */
    @Override
    @Transactional
    public Ad createAd(CreateAdCommand command) {
        if (existAdPort.existsByName(command.getName())) {
            throw new AdExistsException();
        }

        return createAdPort.createAd(AdMapper.INSTANCE.toAd(command));
    }

}
