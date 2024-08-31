package com.example.bkjeon.base.config.actuator;

import com.example.bkjeon.base.services.api.v1.actuator.service.ApplicationRequestWithoutMicrometer;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class ApplicationFunctionCounterConfig {

    private final ApplicationRequestWithoutMicrometer manager;
    private final MeterRegistry meterRegistry;

    @PostConstruct
    void init() {
        FunctionCounter.builder("application.function.counter", manager,
                ApplicationRequestWithoutMicrometer::getCount).register(meterRegistry);
    }

}
