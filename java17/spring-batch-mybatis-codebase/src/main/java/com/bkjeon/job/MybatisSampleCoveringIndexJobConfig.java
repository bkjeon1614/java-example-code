package com.bkjeon.job;

import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.entity.sample.SampleOut;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * --job.name=MYBATIS_SAMPLE_COVERING_INDEX_JOB requestDate=20240701
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MybatisSampleCoveringIndexJobConfig {

    private static final String JOB_NAME_PREFIX = "MYBATIS_SAMPLE_COVERING_INDEX";
    private static final int CHUNK_SIZE = 3000;

    private final SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job mybatisSampleCoveringIndexJob(JobRepository jobRepository, Step mybatisSampleCoveringIndexJobStep) {
        return new JobBuilder(JOB_NAME_PREFIX + "_JOB", jobRepository)
            .start(mybatisSampleCoveringIndexJobStep)
            .build();
    }

    @Bean
    @JobScope
    public Step mybatisSampleCoveringIndexJobStep(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager, @Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(">>>>> requestDate: {}", requestDate);
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP", jobRepository)
            .<Sample, SampleOut>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(mybatisSampleCoveringIndexPagingItemReader())
            .processor(mybatisSampleCoveringIndexItemProcessor())
            .writer(mybatisSampleCoveringIndexItemWriter())
            .build();
    }

    @Bean
    public MyBatisPagingItemReader<Sample> mybatisSampleCoveringIndexPagingItemReader() {
        return new MyBatisPagingItemReaderBuilder<Sample>()
            .pageSize(CHUNK_SIZE)
            .sqlSessionFactory(sqlSessionFactory)
            .queryId("com.bkjeon.feature.mapper.sample.SampleMapper.selectZeroOffsetSampleList")
            .build();
    }

    @Bean
    public ItemProcessor<Sample, SampleOut> mybatisSampleCoveringIndexItemProcessor() {
        return item -> SampleOut.builder()
            .id(item.getId())
            .amount(item.getAmount())
            .txName(item.getTxName())
            .txDateTime(LocalDateTime.now())
            .build();
    }

    @Bean
    public MyBatisBatchItemWriter<SampleOut> mybatisSampleCoveringIndexItemWriter() {
        return new MyBatisBatchItemWriterBuilder<SampleOut>()
            .sqlSessionFactory(sqlSessionFactory)
            .statementId("com.bkjeon.feature.mapper.sample.SampleMapper.insertSample")
            .build();
    }

}
