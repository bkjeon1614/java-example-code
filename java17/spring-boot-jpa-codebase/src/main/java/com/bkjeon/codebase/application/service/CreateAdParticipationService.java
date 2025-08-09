package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.application.command.CreateAdParticipationCommand;
import com.bkjeon.codebase.application.port.in.CreateAdParticipationUseCase;
import com.bkjeon.codebase.application.port.out.CreateAdParticipationPort;
import com.bkjeon.codebase.application.port.out.PointRewardPort;
import com.bkjeon.codebase.application.port.out.ReadAdPort;
import com.bkjeon.codebase.application.port.out.UpdateAdPort;
import com.bkjeon.codebase.domain.mapper.AdParticipationMapper;
import com.bkjeon.codebase.domain.model.Ad;
import com.bkjeon.codebase.domain.model.AdParticipation;
import com.bkjeon.codebase.infrastructure.aspect.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateAdParticipationService implements CreateAdParticipationUseCase {

    private final CreateAdParticipationPort createAdParticipationPort;
    private final PointRewardPort pointRewardPort;
    private final ReadAdPort readAdPort;
    private final UpdateAdPort updateAdPort;

    /**
     * 광고 참여
     * @param command 광고 참여 이력을 저장하기 위한 Command Object
     * @return 광고 참여 데이터
     */
    @DistributedLock(key = "'adParticipation:' + #command.adId")
    @Override
    public AdParticipation createAdParticipation(CreateAdParticipationCommand command) {
        Ad ad = readAdPort.findById(command.getAdId());

        // 광고의 참여 가능 횟수 차감 및 체크
        ad.decrease();

        // 광고 참여 저장
        command.changeRewardAmount(ad.getRewardAmount());
        AdParticipation adParticipation = createAdParticipationPort.createAdParticipation(
            AdParticipationMapper.INSTANCE.toAdParticipation(command));
        if (adParticipation != null) {
            // 포인트 적립 (적립 API)
            pointRewardPort.processPointReward(command.getUserId(), command.getAdId());

            // 광고의 참여 가능 횟수 차감 반영
            updateAdPort.updateAd(ad);
        }

        return adParticipation;
    }

}
