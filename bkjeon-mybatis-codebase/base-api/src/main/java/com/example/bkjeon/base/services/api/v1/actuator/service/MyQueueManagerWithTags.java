package com.example.bkjeon.base.services.api.v1.actuator.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyQueueManagerWithTags {

    private final MeterRegistry meterRegistry;

    public void push() {
        Counter.builder("my.queue.counter")
            .tag("type", "push")
            .tag("class", this.getClass().toString())    // tag 는 여러개를 넣을 수 있다.
            .register(meterRegistry).increment();
    }

    public void pop() {
        Counter.builder("my.queue.counter")
            .tag("type", "pop")
            .tag("class", this.getClass().toString())
            .register(meterRegistry).increment();
    }

}
