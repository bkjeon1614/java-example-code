package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.ReadAdParticipationRequest;
import com.bkjeon.codebase.application.command.ReadAdParticipationCommand;
import com.bkjeon.codebase.domain.model.AdParticipation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ReadAdParticipationServiceIntegrationTest {

    @Autowired
    private ReadAdParticipationService readAdParticipationService;

    @Test
    @DisplayName("광고 참여 조회 테스트")
    void findAdParticipationListTest() {
        // given
        ReadAdParticipationRequest request = ReadAdParticipationRequest.builder()
            .userId(1L)
            .startDate("2025-03-01 00:00:19")
            .endDate("2025-03-05 00:00:19")
            .page(1)
            .size(50)
            .build();
        ReadAdParticipationCommand command = ReadAdParticipationCommand.of(request);

        // when
        List<AdParticipation> result = readAdParticipationService.findAdParticipationList(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getUserId()).isEqualTo(1);
        assertThat(result.get(0).getAdId()).isEqualTo(4);
        assertThat(result.get(0).getAdName()).isEqualTo("가을맞이 할인");
        assertThat(result.get(0).getRewardAmount()).isEqualTo(250);
    }

}
