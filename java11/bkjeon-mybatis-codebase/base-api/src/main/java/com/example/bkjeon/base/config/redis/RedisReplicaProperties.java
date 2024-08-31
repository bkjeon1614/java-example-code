package com.example.bkjeon.base.config.redis;

import java.util.List;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.base")
@Getter
@Setter
public class RedisReplicaProperties {

	private String host;
	private int port;
	private RedisProperties main;
	private List<RedisProperties> replicas;

}
