package com.example.bkjeon.base.actuator.gauge;

import com.example.bkjeon.base.services.api.v1.actuator.service.gauge.QueueManager;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GaugeConfigWithMeterBinder {

    private static final String METRIC_NAME = "my.queue.size";

    @Bean
    public MeterBinder gaugeMeterBinder(QueueManager queueManager) {
        return registry -> Gauge.builder(METRIC_NAME, queueManager, QueueManager::init)
            .register(registry);
    }

}
