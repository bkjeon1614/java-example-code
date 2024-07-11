package com.bkjeon.batch.sample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * --job.name=SAMPLE_JOB requestDate=20240711
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BkjeonJobConfig {

    public static final String JOB_NAME = "BKJEON_JOB";

    @Bean
    public Job bkjeonJob(JobRepository jobRepository, Step bkjeonStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(bkjeonStep)
            .build();
    }

    @Bean
    @JobScope
    public Step bkjeonStep(JobRepository jobRepository, Tasklet testTasklet,
        PlatformTransactionManager platformTransactionManager,
        @Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(">>>>> requestDate: {}", requestDate);
        return new StepBuilder("bkjeonStep", jobRepository)
            .tasklet(testTasklet, platformTransactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet bkjeonTasklet(){
        return ((contribution, chunkContext) -> {
            log.info(">>>>> This is Step1");
            return RepeatStatus.FINISHED;
        });
    }

}
