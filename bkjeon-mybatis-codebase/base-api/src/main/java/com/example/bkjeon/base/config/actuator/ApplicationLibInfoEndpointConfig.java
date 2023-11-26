package com.example.bkjeon.base.config.actuator;

import com.example.bkjeon.base.actuator.ApplicationHealthIndicator;
import com.example.bkjeon.base.actuator.ApplicationInfoContributor;
import com.example.bkjeon.base.actuator.ApplicationLibInfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationLibInfoEndpointConfig {

    @Bean
    public ApplicationLibInfoEndpoint applicationLibInfoEndpoint() {
        return new ApplicationLibInfoEndpoint();
    }

    @Bean
    public ApplicationHealthIndicator applicationHealthIndicator() {
        return new ApplicationHealthIndicator();
    }

    @Bean
    public ApplicationInfoContributor applicationInfoContributor() {
        return new ApplicationInfoContributor();
    }

}
