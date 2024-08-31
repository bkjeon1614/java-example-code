package com.example.bkjeon.base.services.api.v1.actuator.controller;

import com.example.bkjeon.base.services.api.v1.actuator.service.gauge.GaugeCounterMapService;
import com.example.bkjeon.base.services.api.v1.actuator.service.gauge.GaugeCounterService;
import com.example.bkjeon.base.services.api.v1.actuator.service.gauge.QueueManager;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/gauges")
@RequiredArgsConstructor
public class GaugeController {

    private static final String METRIC_NAME = "my.queue.size";

    private final QueueManager queueManager;
    private final MeterRegistry meterRegistry;
    private final GaugeCounterService gaugeCounterService;
    private final GaugeCounterMapService gaugeCounterMapService;

    @GetMapping("req")
    public double gaugeReq() {
        Gauge.builder(METRIC_NAME, queueManager, QueueManager::increment).register(meterRegistry);
        return Gauge.builder(METRIC_NAME, queueManager, QueueManager::getSize)
            .register(meterRegistry)
            .value();
    }

    @GetMapping("req2")
    public void gaugeReq2() {
        gaugeCounterService.increment();
        gaugeCounterMapService.increment("bkjeon");
    }

}
