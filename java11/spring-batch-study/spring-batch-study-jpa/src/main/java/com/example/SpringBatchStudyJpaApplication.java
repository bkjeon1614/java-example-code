package com.example;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing	//	Spring Batch Enable
@SpringBootApplication
public class SpringBatchStudyJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchStudyJpaApplication.class, args);
	}

}