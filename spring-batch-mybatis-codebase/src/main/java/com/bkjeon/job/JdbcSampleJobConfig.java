package com.bkjeon.job;

import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.rowmapper.sample.JdbcSampleRowMapper;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * --job.name=JDBC_SAMPLE_JOB requestDate=20240711
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class JdbcSampleJobConfig {

    private static final String JOB_NAME_PREFIX = "JDBC_SAMPLE";
    private static final int CHUNK_SIZE = 10;

    private final DataSource dataSource;

    @Bean
    public Job jdbcSampleJob(JobRepository jobRepository, Step jdbcSampleJobStep) {
        return new JobBuilder(JOB_NAME_PREFIX + "_JOB", jobRepository)
            .start(jdbcSampleJobStep)
            .build();
    }

    @Bean
    @JobScope
    public Step jdbcSampleJobStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
        @Value("#{jobParameters[requestDate]}") String requestDate) throws Exception {
        log.info(">>>>> requestDate: {}", requestDate);
        return new StepBuilder(JOB_NAME_PREFIX + "_JOB_STEP", jobRepository)
            .<Sample, Sample>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(jdbcSamplePagingItemReader())
            .writer(jdbcSamplePagingItemWriter())
            .build();
    }

    @Bean
    public JdbcPagingItemReader<Sample> jdbcSamplePagingItemReader() throws Exception {
        return new JdbcPagingItemReaderBuilder<Sample>()
            .pageSize(CHUNK_SIZE)
            .fetchSize(CHUNK_SIZE)
            .dataSource(dataSource)
            .rowMapper(new JdbcSampleRowMapper())
            .queryProvider(createQueryProvider(dataSource))
            .parameterValues(getParameterValues())
            .name(JOB_NAME_PREFIX + "_PAGING_ITEM_READER")
            .build();
    }

    @Bean
    public JdbcBatchItemWriter<Sample> jdbcSamplePagingItemWriter() {
        return new JdbcBatchItemWriterBuilder<Sample>()
            .dataSource(dataSource)
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("""
                    INSERT INTO sample_out (id, amount, tx_name, tx_date_time)
                    VALUES (:id, :amount, :txName, :txDateTime)
                """)
            .build();
    }

    private Map<String, Object> getParameterValues() {
        Map<String, Object> parameterValues = new HashMap<>();
        parameterValues.put("amount", 2000);
        return parameterValues;
    }

    @Bean
    public PagingQueryProvider createQueryProvider(DataSource dataSource) throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("id, amount, tx_name, tx_date_time");
        queryProvider.setFromClause("from sample");
        queryProvider.setWhereClause("where amount >= :amount");

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }

}
