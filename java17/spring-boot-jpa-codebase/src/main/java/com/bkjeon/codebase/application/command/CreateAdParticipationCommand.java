package com.bkjeon.codebase.application.command;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdParticipationRequest;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAdParticipationCommand {

    private Long userId;
    private Long adId;
    private LocalDateTime participationTime;
    private Long rewardAmount;

    public static CreateAdParticipationCommand of(CreateAdParticipationRequest request) {
        return CreateAdParticipationCommand.builder()
            .userId(request.userId())
            .adId(request.adId())
            .participationTime(LocalDateTime.now())
            .build();
    }

    public void changeRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

}
