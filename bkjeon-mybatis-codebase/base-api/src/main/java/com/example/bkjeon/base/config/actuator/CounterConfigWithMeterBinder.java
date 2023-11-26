package com.example.bkjeon.base.config.actuator;

import com.example.bkjeon.base.actuator.counter.ApplicationRequestWithoutMicrometer;
import io.micrometer.core.instrument.FunctionCounter;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfigWithMeterBinder {

    // MeterBinder 를 이용하면 @PostConstruct 없이 구현이 가능
    @Bean
    public MeterBinder applicationCounterWithMeterBinder(
            ApplicationRequestWithoutMicrometer manager) {
        return registry -> FunctionCounter.builder("application.function.counter2", manager,
                ApplicationRequestWithoutMicrometer::getCount).register(registry);
    }

}
