package com.bkjeon.codebase.application.service;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdParticipationRequest;
import com.bkjeon.codebase.application.command.CreateAdParticipationCommand;
import com.bkjeon.codebase.application.port.out.ReadAdPort;
import com.bkjeon.codebase.domain.model.Ad;
import com.bkjeon.codebase.domain.model.AdParticipation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class CreateAdParticipationServiceIntegrationTest {

    @Autowired
    private CreateAdParticipationService createAdParticipationService;

    @Autowired
    private ReadAdPort readAdPort;

    @Test
    @DisplayName("광고 참여 테스트")
    void createAdParticipationTest() {
        // given
        long adId = 1L;
        long userId = 1L;
        long rewardAmount = readAdPort.findById(adId).getRewardAmount();

        CreateAdParticipationRequest request = CreateAdParticipationRequest.builder()
            .adId(adId)
            .userId(userId)
            .build();
        CreateAdParticipationCommand command = CreateAdParticipationCommand.of(request);

        // when
        AdParticipation result = createAdParticipationService.createAdParticipation(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getAdId()).isEqualTo(adId);
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getRewardAmount()).isEqualTo(rewardAmount);
        assertThat(result.getParticipationTime()).isEqualTo(command.getParticipationTime());
    }

    @Test
    @DisplayName("광고 참여 동시성 테스트")
    void createAdParticipationDistributedLockTest() throws InterruptedException {
        // given
        int numberOfThreads = 100;
        long userId = 1L;
        long adId = 1L;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        // when
        for (int i=0; i<numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    CreateAdParticipationRequest request = CreateAdParticipationRequest.builder()
                        .adId(adId)
                        .userId(userId)
                        .build();
                    CreateAdParticipationCommand command = CreateAdParticipationCommand.of(request);
                    createAdParticipationService.createAdParticipation(command);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        Ad ad = readAdPort.findById(userId);
        assertThat(ad.getParticipationLimit()).isEqualTo(10);
    }

}
