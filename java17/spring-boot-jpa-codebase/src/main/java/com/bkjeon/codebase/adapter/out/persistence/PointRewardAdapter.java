package com.bkjeon.codebase.adapter.out.persistence;

import com.bkjeon.codebase.application.port.out.PointRewardPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PointRewardAdapter implements PointRewardPort {

    @Async
    @Override
    public void processPointReward(Long userId, Long adId) {
        log.info("포인트 적립 API 호출 (Mock) - userId: {}, adId: {}", userId, adId);
    }

}
