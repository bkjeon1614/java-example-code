package com.example.bkjeon.base.services.api.v1.actuator.service.gauge;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GaugeCounterMapService {

    private static final String METRIC_NAME = "my.gauge.countMap.example";
    private static final String METRIC_TAG_NAME = "test";

    private final Map<String, Double> counterMap = new HashMap<>();

    private final MeterRegistry meterRegistry;

    private double getGaugeValue(String metaTagName) {
        return counterMap.getOrDefault(metaTagName, 0.0);
    }

    public double getSize(String metaTagName) {
        return counterMap.getOrDefault(metaTagName, 0.0);
    }

    public void increment(String metaTagName) {
        Gauge result = meterRegistry.find(METRIC_NAME)
            .tag(METRIC_TAG_NAME, metaTagName)
            .gauge();
        if (Objects.isNull(result)) {
            Gauge.builder(METRIC_NAME, this, v -> getGaugeValue(metaTagName))
                .tag(METRIC_TAG_NAME, metaTagName)
                .register(meterRegistry);
        }

        if (counterMap.containsKey(metaTagName)) {
            counterMap.put(metaTagName, counterMap.get(metaTagName) + 1);
        } else {
            counterMap.put(metaTagName, 1.0);
        }
    }

}