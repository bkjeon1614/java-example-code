package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.application.command.ReadAdParticipationCommand;
import com.bkjeon.codebase.application.port.in.ReadAdParticipationUseCase;
import com.bkjeon.codebase.application.port.out.ReadAdParticipationPort;
import com.bkjeon.codebase.domain.model.AdParticipation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAdParticipationService implements ReadAdParticipationUseCase {

    private final ReadAdParticipationPort readAdParticipationPort;

    /**
     * 광고 참여 조회
     * @param command 광고 참여 이력을 조회하기 위한 Command Object
     * @return 광고 참여 이력 조회 리스트
     */
    @Override
    public List<AdParticipation> findAdParticipationList(ReadAdParticipationCommand command) {
        return readAdParticipationPort.findAdParticipationList(
            command.getUserId(), command.getStartDate(), command.getEndDate(), command.getOffset(), command.getSize());
    }

}
