package com.bkjeon.core.config.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.base")
@Getter
@Setter
public class RedisProperties {

    private String host;
    private int port;

}