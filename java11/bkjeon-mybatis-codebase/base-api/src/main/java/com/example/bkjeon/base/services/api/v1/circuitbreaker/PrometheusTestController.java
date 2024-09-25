package com.example.bkjeon.base.services.api.v1.circuitbreaker;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrometheusTestController {

	@GetMapping("/end-point1")
	public String endPoint1() {
		return "Metrics for endPoint1";
	}

	@GetMapping("/end-point2")
	public String endpoint2() {
		return "Metrics for endPoint2";
	}

}
