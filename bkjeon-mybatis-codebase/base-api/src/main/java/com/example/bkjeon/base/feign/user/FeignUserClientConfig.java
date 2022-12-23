package com.example.bkjeon.base.feign.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

/**
 * Feign Configuration
 */
public class FeignUserClientConfig {

	@Bean
	public RequestInterceptor requestHeader(@Value("${external.api.user.token}") String apiToken) {
		return new FeignUserClientInterceptor(apiToken);
	}

}
