package com.bkjeon.codebase.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "advertisement_participation")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdParticipationEntity {

    // 광고 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 참여한 유저 ID
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // 참여한 광고 ID
    @Column(name = "ad_id", nullable = false)
    private Long adId;

    // 광고 참여 시각
    @Column(name = "participation_time", nullable = false)
    private LocalDateTime participationTime;

    // 적립 액수
    @Column(name = "reward_amount", nullable = false)
    private Long rewardAmount;

}