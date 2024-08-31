package com.example.bkjeon.base.config.actuator.gauge;

import com.example.bkjeon.base.services.api.v1.actuator.service.gauge.QueueManager;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

// GaugeConfigWithMeterBinder.java 로 사용하기 때문에 @Configuration 주석
// @Configuration
@RequiredArgsConstructor
public class GaugeConfig {

    private final QueueManager queueManager;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void register() {
        Gauge.builder("my.queue.size", queueManager, QueueManager::getQueueSize)
            .register(meterRegistry);
    }

}
