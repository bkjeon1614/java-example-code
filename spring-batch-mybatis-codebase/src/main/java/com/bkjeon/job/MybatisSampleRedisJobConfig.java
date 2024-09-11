package com.bkjeon.job;

import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.entity.sample.SampleOut;
import com.bkjeon.feature.mapper.sample.SampleMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * --job.name=MYBATIS_SAMPLE_REDIS_JOB requestDate=20240701
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MybatisSampleRedisJobConfig {

    private static final String JOB_NAME_PREFIX = "MYBATIS_SAMPLE_REDIS";
    private static final String REDIS_KEY_PREFIX = "SAMPLE_";
    private static final int CHUNK_SIZE = 1000;

    private final StringRedisTemplate redisTemplate;
    private final SqlSessionFactory sqlSessionFactory;

    @Bean
    public Job mybatisSampleRedisJob(JobRepository jobRepository,
        Step mybatisSampleRedisJobStep1, Step mybatisSampleRedisJobStep2) {
        return new JobBuilder(JOB_NAME_PREFIX + "_JOB", jobRepository)
            .start(mybatisSampleRedisJobStep1)
            .next(mybatisSampleRedisJobStep2)
            .build();
    }

    @Bean
    @JobScope
    public Step mybatisSampleRedisJobStep1(JobRepository jobRepository,
        Tasklet mybatisSampleDataToRedisTasklet, PlatformTransactionManager platformTransactionManager) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> mybatisSampleRedisJobStep1");
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP2", jobRepository)
            .tasklet(mybatisSampleDataToRedisTasklet, platformTransactionManager).build();
    }

    /**
     * Redis Pipeline Test (pipeline 1000 단위로 처리)
     * [사양]
     * - version: 7.0.5
     * - set 명령기준으로 한거라 재 측정필요
     * 1. 10만건
     *    - asis: 117초
     *    - tobe: 7초(1000)
     * 2. 100만건
     *    - asis: 1225초
     *    - tobe: 64초(1000) - chunk size 를 늘려도 처리속도는 크게 변화가 없다. 레디스 스펙에 영향을 받나? -> Redis 사용량에 따라 성능이 달라질 수 있다.
     * * 약 19배 증가
     */
    @Bean
    public Tasklet mybatisSampleDataToRedisTasklet() {
        return ((contribution, chunkContext) -> {
            log.info(">>>>> This is mybatisSampleDataToRedisTasklet");

            // Mock Data
            List<Sample> sampleList = new ArrayList<>();
            for (long c=1; c <= 1000000; c++) {
                sampleList.add(
                    Sample.builder().id(c).txName("TEST" + c).amount(c * 1000).txDateTime(LocalDateTime.now()).build());
            }

            long beforeTime = System.currentTimeMillis();
            int size = sampleList.size();
            for (int i = 0; i < size; i += CHUNK_SIZE) {
                List<Sample> chunk = sampleList.subList(i, Math.min(size, i + CHUNK_SIZE));

                redisTemplate.executePipelined(
                    (RedisCallback<Object>) connection -> {
                        StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
                        for (Sample sample: chunk) {
                            stringRedisConn.set(REDIS_KEY_PREFIX + sample.getId(), "value" + sample.getTxName());
                        }
                        return null;
                    });
            }

            long afterTime = System.currentTimeMillis();
            long secDiffTime = (afterTime - beforeTime) / 1000;
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>> Redis 실행시간(s): {}", secDiffTime);
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    @JobScope
    public Step mybatisSampleRedisJobStep2(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager,
        @Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> mybatisSampleRedisJobStep2 requestDate:{} ", requestDate);
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP2", jobRepository)
            .<Sample, SampleOut>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(mybatisSampleRedisToRdbPagingItemReader())
            .processor(mybatisSampleToSampleOutItemProcessor())
            .writer(mybatisSampleRedisToRdbItemWriter())
            .build();
    }

    @Bean
    public MyBatisPagingItemReader<Sample> mybatisSampleRedisToRdbPagingItemReader() {
        return new MyBatisPagingItemReaderBuilder<Sample>()
            .pageSize(CHUNK_SIZE)
            .sqlSessionFactory(sqlSessionFactory)
            .queryId("com.bkjeon.feature.mapper.sample.SampleMapper.selectZeroOffsetSampleList")
            .build();
    }

    @Bean
    public ItemProcessor<Sample, SampleOut> mybatisSampleToSampleOutItemProcessor() {
        return item -> SampleOut.builder()
            .id(item.getId())
            .amount(item.getAmount())
            .txName(redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + item.getId()))
            .txDateTime(LocalDateTime.now())
            .build();
    }

    @Bean
    public MyBatisBatchItemWriter<SampleOut> mybatisSampleRedisToRdbItemWriter() {
        return new MyBatisBatchItemWriterBuilder<SampleOut>()
            .sqlSessionFactory(sqlSessionFactory)
            .statementId("com.bkjeon.feature.mapper.sample.SampleMapper.insertSample")
            .build();
    }

}
