package com.example.bkjeon.base.feign.user.model;

import lombok.Builder;
import lombok.ToString;

@ToString
public class FeignUserRequest {

	private String testCode;

	@Builder
	public FeignUserRequest(String testCode) {
		this.testCode = testCode;
	}

}
