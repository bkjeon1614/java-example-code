package com.example.bkjeon.base.config.actuator.timer;

import com.example.bkjeon.base.services.api.v1.actuator.service.timer.MyTimerManager;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class FunctionTimerConfig {

    @Bean
    public FunctionTimer myFunctionTimer(MyTimerManager myTimerManager, MeterRegistry meterRegistry) {
        FunctionTimer functionTimer = FunctionTimer.builder(
            "my.timer5",
            myTimerManager,
            value -> {
                return value.getCount();
            },
            value -> {
                return value.getTotalTime();
            },
            TimeUnit.SECONDS
        )
        .register(meterRegistry);

        return functionTimer;
    }

}
