package com.example.bkjeon.enums.actuator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Metric 관련 Enum
 * metricName: 메트릭명
 * tagName: 호출된 메소드명
 */
@Getter
@AllArgsConstructor
public enum MetricType {

    CUSTOM_METRIC_INFO("custom.metric.count", "isCustomCounterReq");

    private final String metricName;
    private final String tagName;

}
