package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.application.command.ReadAdCommand;
import com.bkjeon.codebase.domain.model.Ad;
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
class ReadAdServiceIntegrationTest {

    @Autowired
    private ReadAdService readAdService;

    @Test
    @DisplayName("광고 조회 테스트")
    void findTopAvailableAdvertisementsByRewardTest() {
        // given
        ReadAdCommand command = ReadAdCommand.from(10);

        // when
        List<Ad> result = readAdService.findTopAvailableAdvertisementsByReward(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.get(0).getId()).isEqualTo(7);
        assertThat(result.get(0).getName()).isEqualTo("크리스마스 기념 이벤트");
        assertThat(result.get(0).getDescription()).isEqualTo("산타의 선물! 특별한 할인!");
        assertThat(result.get(0).getImageUrl()).isEqualTo("https://example.com/ad7.jpg");
        assertThat(result.get(0).getRewardAmount()).isEqualTo(400);
    }

}
