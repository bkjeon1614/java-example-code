package com.example.bkjeon.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
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
public class SampleJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job sampleJob() {
        return jobBuilderFactory.get("sampleJob")
            .start(sampleStep1())
                .on("FAILED")
                .to(sampleStep3())
                .on("*")
                .end()
            .from(sampleStep1())
                .on("*")
                .to(sampleStep2())
                .next(sampleStep3())
                .on("*")
                .end()
            .end()
            .build();
    }

    @Bean
    public Step sampleStep1() {
        return stepBuilderFactory.get("sampleStep1")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>>>>>>> sampleStep1 Start !!");
                contribution.setExitStatus(ExitStatus.FAILED);
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step sampleStep2() {
        return stepBuilderFactory.get("sampleStep2")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>>>>>>> sampleStep2 Start !!");
                return RepeatStatus.FINISHED;
            }).build();
    }

    @Bean
    public Step sampleStep3() {
        return stepBuilderFactory.get("sampleStep3")
            .tasklet((contribution, chunkContext) -> {
                log.info(">>>>>>>>>> sampleStep3 Start !!");
                return RepeatStatus.FINISHED;
            }).build();
    }

}
