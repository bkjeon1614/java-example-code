package com.example.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@StepScope
public class SimpleJobTasklet implements Tasklet {

	@Value("#{jobParameters[requestDate]}")
	private String requestDate;

	public SimpleJobTasklet() {
		log.info(">>>>>>> Tasklet 생성");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		log.info(">>>>>>>> This is Step1 {}", requestDate);
		return RepeatStatus.FINISHED;
	}

}
