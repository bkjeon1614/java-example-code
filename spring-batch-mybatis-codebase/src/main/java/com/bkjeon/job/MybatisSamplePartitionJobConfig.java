package com.bkjeon.job;

import com.bkjeon.core.listener.CommonChunkListener;
import com.bkjeon.core.listener.CommonStepListener;
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
 * --job.name=MYBATIS_SAMPLE_PARTITION_JOB requestDate=20240701
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MybatisSamplePartitionJobConfig {

    private static final String JOB_NAME_PREFIX = "MYBATIS_SAMPLE_PARTITION";
    private static final int CHUNK_SIZE = 2000;

    private final SqlSessionFactory sqlSeesionFactory;

    @Bean
    public Job mybatisSamplePartitionJob(JobRepository jobRepository, Step mybatisSamplePartitionJobStep) {
        return new JobBuilder(JOB_NAME_PREFIX + "_JOB", jobRepository)
            .start(mybatisSamplePartitionJobStep)
            .build();
    }

    @Bean
    @JobScope
    public Step mybatisSamplePartitionJobStep(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager,
        @Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> mybatisSamplePartitionJobStep requestDate: {}, chunk: {}", requestDate, CHUNK_SIZE);
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP", jobRepository)
            .<Sample, SampleOut>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(mybatisSamplePartitionPagingItemReader())
            .processor(mybatisSamplePartitionItemProcessor())
            .writer(mybatisSamplePartitionItemWriter())
            .listener(new CommonChunkListener())
            .listener(new CommonStepListener())
            .build();
    }

    @Bean
    public MyBatisPagingItemReader<Sample> mybatisSamplePartitionPagingItemReader() {
        return new MyBatisPagingItemReaderBuilder<Sample>()
            .pageSize(CHUNK_SIZE)
            .sqlSessionFactory(sqlSeesionFactory)
            .queryId("com.bkjeon.feature.mapper.sample.SampleMapper.selectSampleList")
            .build();
    }

    @Bean
    public ItemProcessor<Sample, SampleOut> mybatisSamplePartitionItemProcessor() {
        return item -> {
            // log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> process item: {}", item);
            return SampleOut.builder()
                .id(item.getId())
                .amount(item.getAmount())
                .txName(item.getTxName())
                .txDateTime(LocalDateTime.now())
                .build();
        };
    }

    @Bean
    public MyBatisBatchItemWriter<SampleOut> mybatisSamplePartitionItemWriter() {
        return new MyBatisBatchItemWriterBuilder<SampleOut>()
            .sqlSessionFactory(sqlSeesionFactory)
            .statementId("com.bkjeon.feature.mapper.sample.SampleMapper.insertSample")
            .build();
    }

}
