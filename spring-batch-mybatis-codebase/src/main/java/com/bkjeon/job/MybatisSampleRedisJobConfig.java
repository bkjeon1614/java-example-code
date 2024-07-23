package com.bkjeon.job;

import com.bkjeon.core.listener.CommonChunkListener;
import com.bkjeon.core.listener.CommonStepListener;
import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.entity.sample.SampleOut;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
    private static final int CHUNK_SIZE = 2000;

    private final SqlSessionFactory sqlSeesionFactory;
    private final StringRedisTemplate redisTemplate;

    @Bean
    public Job mybatisSampleRedisJob(JobRepository jobRepository,
        Step mybatisSampleRedisJobStep1, Step mybatisSampleRedisJobStep2) {
        return new JobBuilder(JOB_NAME_PREFIX + "_JOB", jobRepository)
            .start(mybatisSampleRedisJobStep1)
            //.next(mybatisSampleRedisJobStep2)
            .build();
    }

    @Bean
    @JobScope
    public Step mybatisSampleRedisJobStep1(JobRepository jobRepository,
        Tasklet testTasklet,
        PlatformTransactionManager platformTransactionManager) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> mybatisSampleRedisJobStep1");
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP2", jobRepository)
            .tasklet(testTasklet, platformTransactionManager).build();
    }

    /**
     * Redis Pipeline Test (pipeline 3000 단위로 처리)
     * [사양]
     * - version: 7.0.5
     * 1. 10만건
     *    - asis: 149초
     *    - tobe: 17초(1000)
     * 2. 100만건
     *    - asis: 1421초
     *    - tobe: 173초(1000) - chunk size 를 늘려도 처리속도는 크게 변화가 없다. 레디스 스펙에 영향을 받나? -> 사용량에 따라 성능이 달라질 수 있다.
     * 3. ec-test-comm 100만건 테스트
     *    - tobe: 390초
     */
    @Bean
    public Tasklet mybatisSampleRedisTasklet() {
        return ((contribution, chunkContext) -> {
            log.info(">>>>> This is mybatisSampleRedisTasklet");

            List<String> cartList = new ArrayList<>();
            for (int c=0; c <= 1000000; c++) {
                cartList.add("cart" + c);
            }

            long beforeTime = System.currentTimeMillis();

            // CASE1
            // cartList.forEach(cart -> redisTemplate.opsForSet().add("TEST_KEY", cart));

            // CASE2
            int size = cartList.size();
            int chunkSize = 1000;
            for (int i = 0; i < size; i += chunkSize) {
                List<String> chunk = cartList.subList(i, Math.min(size, i + chunkSize));

                redisTemplate.executePipelined(
                    (RedisCallback<Object>) connection -> {
                        StringRedisConnection stringRedisConn = (StringRedisConnection) connection;
                        for (String s: chunk) {
                            stringRedisConn.sAdd("TEST_KEY_PIPE", s);
                        }
                        return null;
                    });
            }

            long afterTime = System.currentTimeMillis();
            long secDiffTime = (afterTime - beforeTime) / 1000;
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>> 시간차이(s): {}", secDiffTime);
            return RepeatStatus.FINISHED;
        });
    }

    @Bean
    @JobScope
    public Step mybatisSampleRedisJobStep2(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager,
        @Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> mybatisSampleRedisJobStep2 requestDate: {}, chunk: {}", requestDate, CHUNK_SIZE);
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP1", jobRepository)
            .<Sample, SampleOut>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(mybatisSampleRedisPagingItemReader())
            .processor(mybatisSampleRedisItemProcessor())
            .writer(mybatisSampleRedisItemWriter())
            .listener(new CommonChunkListener())
            .listener(new CommonStepListener())
            .build();
    }

    @Bean
    public MyBatisPagingItemReader<Sample> mybatisSampleRedisPagingItemReader() {
        return new MyBatisPagingItemReaderBuilder<Sample>()
            .pageSize(CHUNK_SIZE)
            .sqlSessionFactory(sqlSeesionFactory)
            .queryId("com.bkjeon.feature.mapper.sample.SampleMapper.selectSampleList")
            .build();
    }

    @Bean
    public ItemProcessor<Sample, SampleOut> mybatisSampleRedisItemProcessor() {
        return item -> {
            int randomNum = (int) (Math.random() * 100) + 1;
            return SampleOut.builder()
                .id(item.getId())
                .amount(item.getAmount() * randomNum)
                .txName(item.getTxName() + "TEST" + randomNum)
                .txDateTime(LocalDateTime.now())
                .build();
        };
    }

    @Bean
    public MyBatisBatchItemWriter<SampleOut> mybatisSampleRedisItemWriter() {
        return new MyBatisBatchItemWriterBuilder<SampleOut>()
            .sqlSessionFactory(sqlSeesionFactory)
            .statementId("com.bkjeon.feature.mapper.sample.SampleMapper.insertSample")
            .build();
    }

}
