package com.bkjeon.codebase.domain.model;

import com.bkjeon.codebase.domain.exception.AdParticipationValidException;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 광고 도메인 객체
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ad {

    private Long id;                                        // 광고 ID
    private String name;                                    // 광고명
    private Long rewardAmount;                              // 광고 참여시 적립 액수
    private Long participationLimit;                        // 광고 참여 가능 횟수
    private String description;                             // 광고 문구
    private String imageUrl;                                // 광고 이미지 URL
    private LocalDateTime startDate;                        // 광고 노출 시작일
    private LocalDateTime endDate;                          // 광고 노출 종료일

    public void decrease() {
        validateParticipationLimit();
        this.participationLimit -= 1;
    }

    private void validateParticipationLimit() {
        if (participationLimit < 1) {
            throw new AdParticipationValidException();
        }
    }

}
