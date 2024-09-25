package com.example.bkjeon.base.services.api.v1.circuitbreaker;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.bkjeon.constants.Resilience4jCode;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CircuitBreakerService {

	private void runtimeException() {
		int randomInt = new Random().nextInt(10);

		if(randomInt <= 7) {
			throw new RuntimeException("failed");
		}
	}

	@CircuitBreaker(name = Resilience4jCode.CIRCUIT_TEST_70000, fallbackMethod = "getCircuitBreakerFallback")
	public String getCircuitBreaker() {
		runtimeException();
		return "hello world!";
	}
	private String getCircuitBreakerFallback(Throwable t) {
		return "getCircuitBreakerFallback! exception type: " + t.getClass() + "exception, message: " + t.getMessage();
	}

	@Retry(name = Resilience4jCode.RETRY_TEST_3000 , fallbackMethod = "getRetryFallback")
	public String getRetry() {
		log.info("=============== getRetry Request !!");
		runtimeException();
		return "hello world!";
	}
	private String getRetryFallback(Throwable t) {
		return "getRetryFallback! exception type: " + t.getClass() + "exception, message: " + t.getMessage();
	}

}
