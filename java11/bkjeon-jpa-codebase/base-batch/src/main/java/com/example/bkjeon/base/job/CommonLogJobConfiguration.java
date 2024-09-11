package com.example.bkjeon.base.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CommonLogJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job commonLogDelJob() {
        return jobBuilderFactory.get("commonLogDelJob")
            .start(commonLogDelStep1(null))
            .next(commonLogDelStep2(null))
            .build();
    }

    @Bean
    @JobScope
    public Step commonLogDelStep1(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("commonLogDelStep1")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>>>>>>> commonLogDelStep1 Start !!");
                log.info(">>>>>>>>>> requestDate {}", requestDate);
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    @JobScope
    public Step commonLogDelStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("commonLogDelStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>>>>>>> commonLogDelStep2 Start !!");
                log.info(">>>>>>>>>> requestDate {}", requestDate);
                return RepeatStatus.FINISHED;
            }).build();
    }

}
