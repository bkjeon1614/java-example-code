package com.example.bkjeon.base.services.api.v1.actuator.service.gauge;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GaugeCounterService {

    private double counter = 0.0;

    private final MeterRegistry meterRegistry;

    @PostConstruct
    public void init() {
        Gauge.builder("my.gauge.count.example", this, GaugeCounterService::getGaugeValue)
            .register(meterRegistry);
    }

    private double getGaugeValue() {
        return counter;
    }

    public int getSize() {
        return (int) counter;
    }

    public void increment() {
        counter++;
    }

    public void decrement() {
        counter--;
    }

}
