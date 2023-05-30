package com.example.bkjeon.base.feign.user;

import com.google.common.base.Preconditions;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Header Setting Interceptor
 */
public class FeignUserClientInterceptor implements RequestInterceptor {

	private String apiToken;

	public FeignUserClientInterceptor(String apiToken) {
		Preconditions.checkNotNull(apiToken, "Api Token Should Not Be Null");
		this.apiToken = apiToken;
	}

	@Override
	public void apply(RequestTemplate template) {
		template.header("Authorization", "Bearer " + apiToken);
		template.header("Content-Type", "application/json");
	}

}
