package com.example.bkjeon.base.config.actuator;

import com.example.bkjeon.base.actuator.MyStockManager;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyStockMeterBinderConfig {

    /* /actuator/metrics/my.stock 에서 확인할 수 있다.
    @Bean
    public MeterBinder myMeterBinder(MyStockManager myStockManager) {
        return registry -> Gauge.builder("my.stock", myStockManager, MyStockManager::getStockCount)
            .register(registry);
    }
     */

    // MyStockManager 에서 Supplier 인터페이스를 정의하면 무조건 get 을 호출하기 때문에 myStockManager 객체만 넘겨주면 된다. (위 주석 코드와 결과는 같음)
    @Bean
    public MeterBinder myMeterBinder(MyStockManager myStockManager) {
        return registry -> Gauge.builder("my.stock", myStockManager).register(registry);
    }

}
