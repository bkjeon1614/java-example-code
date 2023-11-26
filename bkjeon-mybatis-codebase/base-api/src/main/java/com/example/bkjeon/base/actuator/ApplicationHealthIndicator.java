package com.example.bkjeon.base.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ApplicationHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean status = getStatus();   // 해당 Status 가 DOWN 이면 최상단의 Status 도 동일하게 DOWN 으로 된다.
        if (status) {
            return Health.up().withDetail("key1", "value2").withDetail("key2", "value2").build();
        }

        return Health.down().withDetail("key3", "value3").withDetail("key4", "value4").build();
    }

    boolean getStatus() {
        return System.currentTimeMillis() % 2 == 0;
    }

}
