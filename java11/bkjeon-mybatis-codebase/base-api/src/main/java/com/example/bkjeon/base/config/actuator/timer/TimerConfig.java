package com.example.bkjeon.base.config.actuator.timer;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TimerConfig {

    private final MeterRegistry meterRegistry;

    @Bean
    public Timer myTimer() {
        return Timer.builder("my.timer").register(meterRegistry);
    }

}
