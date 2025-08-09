package com.bkjeon.codebase.domain.model;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 광고 참여 도메인 객체
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AdParticipation {

    private Long id;
    private Long userId;
    private Long adId;
    private LocalDateTime participationTime;
    private String adName;
    private Long rewardAmount;

}