package com.example.bkjeon.base.config.actuator.timer;

import com.example.bkjeon.base.services.api.v1.actuator.service.timer.MyTimerManager;
import io.micrometer.core.instrument.FunctionTimer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class TimerConfigWithMeterBinder {

    @Bean
    public MeterBinder myTimerMeterBinder(MyTimerManager myTimerManager) {
        return new MeterBinder() {
            @Override
            public void bindTo(MeterRegistry registry) {
                FunctionTimer functionTimer = FunctionTimer.builder(
                    "my.timer6",
                    myTimerManager,
                    value -> {
                        return value.getCount();
                    },
                    value -> {
                        return value.getTotalTime();
                    },
                    TimeUnit.SECONDS
                )
                .register(registry);
            }
        };
    }

}
