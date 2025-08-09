package com.bkjeon.codebase.adapter.out.persistence;

import com.bkjeon.codebase.adapter.out.persistence.repository.AdParticipationRepository;
import com.bkjeon.codebase.application.port.out.CreateAdParticipationPort;
import com.bkjeon.codebase.application.port.out.ReadAdParticipationPort;
import com.bkjeon.codebase.domain.mapper.AdParticipationMapper;
import com.bkjeon.codebase.domain.model.AdParticipation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdParticipationRepositoryAdapter implements CreateAdParticipationPort, ReadAdParticipationPort {

    private final AdParticipationRepository adParticipationRepository;

    /**
     * 광고 참여 생성
     * @param adParticipation 광고 참여 도메인
     * @return 광고 참여 이력 데이터
     */
    @Override
    public AdParticipation createAdParticipation(AdParticipation adParticipation) {
        return AdParticipationMapper.INSTANCE.toAdParticipation(
            adParticipationRepository.save(AdParticipationMapper.INSTANCE.toAdParticipationEntity(adParticipation)));
    }

    /**
     * 광고 참여 조회
     * @param userId 유저 ID
     * @param startDate  광고 참여 시작일
     * @param endDate 광고 참여 종료일
     * @param offset 페이지 시작 위치
     * @param size 페이지 노출 사이즈
     * @return 광고 참여 이력 조회 데이터
     */
    @Override
    public List<AdParticipation> findAdParticipationList(
        long userId, LocalDateTime startDate, LocalDateTime endDate, int offset, int size) {
        return adParticipationRepository.findAdParticipationList(userId, startDate, endDate, offset, size);
    }

}