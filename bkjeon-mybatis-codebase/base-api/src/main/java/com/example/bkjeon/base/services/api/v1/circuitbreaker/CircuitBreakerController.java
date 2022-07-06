package com.example.bkjeon.base.services.api.v1.circuitbreaker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/circuitBreaker")
@RequiredArgsConstructor
public class CircuitBreakerController {

	private final CircuitBreakerService circuitBreakerService;

	@GetMapping("circuitBreakerFail")
	public String hello() {
		return circuitBreakerService.getCircuitBreaker();
	}

	@GetMapping("retryFail")
	public String retryFail() {
		return circuitBreakerService.getRetry();
	}

}
