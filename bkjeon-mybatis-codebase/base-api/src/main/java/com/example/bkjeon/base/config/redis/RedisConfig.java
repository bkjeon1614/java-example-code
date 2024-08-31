package com.example.bkjeon.base.config.redis;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ReadFrom;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

	private final RedisReplicaProperties redisProperties;

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		LettuceConnectionFactory connectionFactory;

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration
			.builder()
			.readFrom(ReadFrom.REPLICA_PREFERRED)
			.commandTimeout(Duration.ofMillis(500))
			.build();

		RedisStaticMasterReplicaConfiguration staticMasterReplicaConfiguration =
			new RedisStaticMasterReplicaConfiguration(
				redisProperties.getHost(),
				redisProperties.getPort()
			);

		redisProperties.getReplicas()
			.forEach(replica -> staticMasterReplicaConfiguration.addNode(replica.getHost(), replica.getPort()));
		connectionFactory = new LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig);

		return connectionFactory;
	}

	@Bean
	@Primary
	public RedisCacheManager isLettuceCacheManager() {
		RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
			.defaultCacheConfig()
			.entryTtl(Duration.ofSeconds(60))
			.serializeKeysWith(
				RedisSerializationContext.SerializationPair.fromSerializer(
					new StringRedisSerializer()))
			.serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

		return RedisCacheManager.RedisCacheManagerBuilder
			.fromConnectionFactory(redisConnectionFactory())
			.cacheDefaults(redisCacheConfiguration)
			.build();
	}

}
