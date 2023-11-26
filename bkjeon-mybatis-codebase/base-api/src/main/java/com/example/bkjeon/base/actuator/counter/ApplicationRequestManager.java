package com.example.bkjeon.base.actuator.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class ApplicationRequestManager {

    // MeterRegistry 는 기본적으로 Bean 으로 등록되기 때문에 바로 사용하면 된다.
    private final MeterRegistry meterRegistry;

    private Counter httpRequestCounter;

    @PostConstruct
    void init() {
        httpRequestCounter = Counter.builder("application.http.request").register(meterRegistry);
    }

    public void increase() {
        httpRequestCounter.increment();
    }

}
