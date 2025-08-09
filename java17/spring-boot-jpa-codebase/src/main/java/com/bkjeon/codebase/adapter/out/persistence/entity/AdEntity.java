package com.bkjeon.codebase.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "advertisement")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdEntity {

    // 광고 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 광고명
    @Column(unique = true, nullable = false)
    private String name;

    // 광고 참여시 적립 액수
    @Column(name = "reward_amount", nullable = false)
    private Long rewardAmount;

    // 광고 참여 가능 횟수
    @Column(name = "participation_limit", nullable = false)
    private Long participationLimit;

    // 광고 문구
    @Column(nullable = false)
    private String description;

    // 광고 이미지 URL
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    // 광고 노출 시작일
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;
    
    // 광고 노출 종료일
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

}