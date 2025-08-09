package com.bkjeon.codebase.domain.model;

import com.bkjeon.codebase.domain.exception.AdParticipationValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Tag("unit")
class AdUnitTest {

    @Test
    @DisplayName("광고참여 횟수 감소 테스트")
    void decreaseAdParticipationCountTest() {
        // Given
        Ad ad = Ad.builder().participationLimit(2L).build();

        // When
        ad.decrease();
        ad.decrease();

        // Then
        assertThatThrownBy(ad::decrease)
            .isInstanceOf(AdParticipationValidException.class)
            .hasMessage("광고 참여 가능 횟수가 0이면 참여할 수 없습니다.");
    }

}
