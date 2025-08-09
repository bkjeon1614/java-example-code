package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdRequest;
import com.bkjeon.codebase.application.command.CreateAdCommand;
import com.bkjeon.codebase.domain.model.Ad;
import com.bkjeon.codebase.domain.util.AdDateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CreateAdServiceIntegrationTest {

    @Autowired
    private CreateAdService createAdService;

    @Test
    @DisplayName("광고 생성 테스트")
    void createAdTest() {
        // given
        String name = "하이퍼 세일 이벤트";
        long rewardAmount = 100;
        long participationLimit = 5;
        String description = "특별 할인 혜택을 지금 만나보세요!";
        String imageUrl = "https://example.com/ad1.jpg";
        String startDate = "2025-03-01 00:00:00";
        String endDate = "2025-03-31 23:59:59";

        CreateAdRequest request = CreateAdRequest.builder()
            .name(name)
            .rewardAmount(rewardAmount)
            .participationLimit(participationLimit)
            .description(description)
            .imageUrl(imageUrl)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        CreateAdCommand command = CreateAdCommand.of(request);

        // when
        Ad result = createAdService.createAd(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getRewardAmount()).isEqualTo(rewardAmount);
        assertThat(result.getParticipationLimit()).isEqualTo(participationLimit);
        assertThat(result.getDescription()).isEqualTo(description);
        assertThat(result.getImageUrl()).isEqualTo(imageUrl);
        assertThat(AdDateUtil.localDateTimeTostring(result.getStartDate())).isEqualTo(startDate);
        assertThat(AdDateUtil.localDateTimeTostring(result.getEndDate())).isEqualTo(endDate);
    }

}
