package com.example.bkjeon.base.config.actuator;

import com.example.bkjeon.base.services.api.v1.actuator.ApplicationLibInfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationLibInfoEndpointConfig {

    @Bean
    public ApplicationLibInfoEndpoint applicationLibInfoEndpoint() {
        return new ApplicationLibInfoEndpoint();
    }

}
