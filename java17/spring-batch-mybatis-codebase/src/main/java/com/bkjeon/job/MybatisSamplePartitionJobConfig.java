package com.bkjeon.job;

import com.bkjeon.core.listener.CommonChunkListener;
import com.bkjeon.core.listener.CommonStepListener;
import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.entity.sample.SampleIdRangePartitioner;
import com.bkjeon.feature.entity.sample.SampleOut;
import com.bkjeon.feature.mapper.sample.SampleMapper;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisPagingItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * --job.name=MYBATIS_SAMPLE_PARTITION_JOB requestDate=20240701
 * MYBATIS_SAMPLE_JOB(MybatisSampleJobConfig.class 파티셔닝 개선)
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class MybatisSamplePartitionJobConfig {

    private static final String JOB_NAME = "MYBATIS_SAMPLE_PARTITION_JOB";
    private int chunkSize;

    @Value("${chunkSize:10}")
    public void setChunkSize(int chunkSize){
        this.chunkSize = chunkSize;
    }

    private int poolSize;

    @Value("${poolSize:5}")
    public void setPoolSize(int poolSize){
        this.poolSize = poolSize;
    }

    private final SqlSessionFactory sqlSessionFactory;
    private final SampleMapper sampleMapper;

    @Bean(name = JOB_NAME + "_PARTITION_HANDLER")
    public TaskExecutorPartitionHandler partitionHandler(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager) {
        // 멀티 스레드로 수행이 가능하도록 TaskExecutorPartitionHandler 구현체를 사용
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();

        // Worker 로 실행할 Step 을 지정
        // Partitioner가 만들어준 StepExecutions 환경에서 개별적으로 실행
        partitionHandler.setStep(mybatisSamplePartitionJobStep(jobRepository, platformTransactionManager));

        // 멀티쓰레드로 실행하기 위해 TaskExecutor 를 지정
        partitionHandler.setTaskExecutor(executor());

        // 쓰레드 개수와 gridSize를 맞추기 위해서 poolSize를 gridSize로 등록
        partitionHandler.setGridSize(poolSize);
        return partitionHandler;
    }

    @Bean(name = JOB_NAME + "_TASK_POOL")
    public TaskExecutor executor() {
        // SimpleAsyncTaskExecutor 를 사용할수도 있지만 해당 구현체는 레드를 계속해서 생성할 수 있기 때문에 실제 운영 환경에서는 대형 장애를 발생시킬 수 있음
        // 그래서 스레드풀내에서 지정된 갯수만큼 스레드만 생성할 수 있도록 ThreadPoolTaskExecutor 사용
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("partition-thread");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }

    @Bean(name = JOB_NAME)
    public Job mybatisSamplePartitionJob(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder(JOB_NAME, jobRepository)
            .start(mybatisSamplePartitionJobStepManager(jobRepository, platformTransactionManager))
            .build();
    }

    @Bean(name = JOB_NAME + "_STEP_MANAGER")
    public Step mybatisSamplePartitionJobStepManager(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder(JOB_NAME + "_STEP.MANAGER", jobRepository)
            .partitioner(JOB_NAME + "_STEP", partitioner()) // step1에 사용될 Partitioner 구현체를 등록
            .step(mybatisSamplePartitionJobStep(jobRepository, platformTransactionManager)) // 파티셔닝될 Step 을 등록, step1 이 Partitioner 로직에 따라 서로 다른 StepExecutions를 가진 여러개로 생성
            .partitionHandler(partitionHandler(jobRepository, platformTransactionManager))  // 사용할 PartitionHandler 를 등록
            .build();
    }

    @Bean(name = JOB_NAME + "_PARTITIONER")
    @StepScope
    public SampleIdRangePartitioner partitioner() {
        return new SampleIdRangePartitioner(sampleMapper);
    }

    @Bean(name = JOB_NAME + "_STEP")
    public Step mybatisSamplePartitionJobStep(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder(JOB_NAME + "_STEP", jobRepository)
            .<Sample, SampleOut>chunk(chunkSize, platformTransactionManager)
            .reader(mybatisSamplePartitionPagingItemReader(null, null))
            .processor(processor())
            .writer(mybatisSamplePartitionItemWriter(null, null))
            .listener(new CommonChunkListener())
            .listener(new CommonStepListener())
            .build();
    }

    @Bean(name = JOB_NAME + "_READER")
    @StepScope
    public MyBatisPagingItemReader<Sample> mybatisSamplePartitionPagingItemReader(
        @Value("#{stepExecutionContext[minId]}") Long minId,
        @Value("#{stepExecutionContext[maxId]}") Long maxId) {

        log.info("reader minId={}, maxId={}", minId, maxId);

        return new MyBatisPagingItemReaderBuilder<Sample>()
            .pageSize(chunkSize)
            .sqlSessionFactory(sqlSessionFactory)
            .parameterValues(new HashMap<>() {{
                put("minId", minId);
                put("maxId", maxId);
            }})
            .queryId("com.bkjeon.feature.mapper.sample.SampleMapper.selectSamplePartitionList")
            .build();
    }

    private ItemProcessor<Sample, SampleOut> processor() {
        return SampleOut::new;
    }

    @Bean(name = JOB_NAME + "_WRITER")
    @StepScope
    public MyBatisBatchItemWriter<SampleOut> mybatisSamplePartitionItemWriter(
        @Value("#{stepExecutionContext[minId]}") Long minId,
        @Value("#{stepExecutionContext[maxId]}") Long maxId) {
        log.info("stepExecutionContext minId={}", minId);
        log.info("stepExecutionContext maxId={}", maxId);
        return new MyBatisBatchItemWriterBuilder<SampleOut>()
            .sqlSessionFactory(sqlSessionFactory)
            .statementId("com.bkjeon.feature.mapper.sample.SampleMapper.insertSample")
            .build();
    }

    /*
    @Bean(name = JOB_NAME + "_WRITER")
    @StepScope
    public ItemWriter<SampleOut> mybatisSamplePartitionItemWriter(
        @Value("#{stepExecutionContext[minId]}") Long minId,
        @Value("#{stepExecutionContext[maxId]}") Long maxId) {
        return items -> {
            log.info("stepExecutionContext minId={}", minId);
            log.info("stepExecutionContext maxId={}", maxId);
        };
    }
     */

}
