package com.example.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class NextJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job nextJob() {
		return jobBuilderFactory.get("nextJob")
			.start(step1())
			.next(step2())
			.next(step3())
			.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
			.tasklet((contribution, chunkContext) -> {
				log.info(">>>>> Step1");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
			.tasklet((contribution, chunkContext) -> {
				log.info(">>>>> Step2");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

	@Bean
	public Step step3() {
		return stepBuilderFactory.get("step3")
			.tasklet((contribution, chunkContext) -> {
				log.info(">>>>> Step3");
				return RepeatStatus.FINISHED;
			})
			.build();
	}

}
