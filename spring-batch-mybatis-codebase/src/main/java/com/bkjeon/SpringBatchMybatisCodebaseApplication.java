package com.bkjeon;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBatchMybatisCodebaseApplication {

	@Value("${spring.batch.job.name:NONE}")
	private String jobName;

	public static void main(String[] args) {
		int exitCode = SpringApplication.exit(SpringApplication.run(SpringBatchMybatisCodebaseApplication.class, args));
		log.info("exitCode={}", exitCode);
		System.exit(exitCode);
	}

	@PostConstruct
	public void validateJobNames() {
		log.info("jobName: {}", jobName);
		if (jobName.isEmpty() || jobName.equals("NONE")) {
			throw new IllegalStateException("Job Name Empty !!");
		}
	}

}
