package com.bkjeon.batch.sample.job;

import com.bkjeon.batch.sample.job.param.SampleParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SampleJobConfig {

    public static final String JOB_NAME = "SAMPLE_JOB";

    private final SampleParam sampleParam;

    @Bean
    public Job sampleJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(sampleStep(jobRepository, transactionManager))
            .build();
    }

    @Bean
    @JobScope
    public Step sampleStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>>> chunkSize = {}", sampleParam.getChunkSize());
        log.info(">>>>> date = {}", sampleParam.getDate());
        log.info(">>>>> requestDate = {}", sampleParam.getRequestDate());
        return new StepBuilder("sampleStep", jobRepository)
            .tasklet(testTasklet(), platformTransactionManager).build();
    }

    @Bean
    @StepScope
    public Tasklet testTasklet() {
        return ((contribution, chunkContext) -> {
            log.info(">>>>> This is Step1");
            return RepeatStatus.FINISHED;
        });
    }

}
