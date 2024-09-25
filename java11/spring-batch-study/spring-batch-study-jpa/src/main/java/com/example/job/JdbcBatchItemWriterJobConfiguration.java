package com.example.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcBatchItemWriterJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource; // DataSource DI

	private static final int chunkSize = 10;

	@Bean
	public Job jdbcBatchItemWriterJob() {
		return jobBuilderFactory.get("jdbcBatchItemWriterJob")
			.start(jdbcBatchItemWriterStep())
			.build();
	}

	@Bean
	public Step jdbcBatchItemWriterStep() {
		return stepBuilderFactory.get("jdbcBatchItemWriterStep")
			.<Product, Product>chunk(chunkSize)
			.reader(jdbcBatchItemWriterReader())
			.writer(jdbcBatchItemWriter())
			.build();
	}

	@Bean
	public JdbcCursorItemReader<Product> jdbcBatchItemWriterReader() {
		return new JdbcCursorItemReaderBuilder<Product>()
			.fetchSize(chunkSize)
			.dataSource(dataSource)
			.rowMapper(new BeanPropertyRowMapper<>(Product.class))
			.sql("SELECT id, amount, tx_name, tx_date_time FROM Product")
			.name("jdbcBatchItemWriter")
			.build();
	}

	/**
	 * reader 에서 넘어온 데이터를 하나씩 출력하는 writer
	 */
	@Bean // beanMapped()을 사용할때는 필수
	public JdbcBatchItemWriter<Product> jdbcBatchItemWriter() {
		return new JdbcBatchItemWriterBuilder<Product>()
			.dataSource(dataSource)
			.sql("insert into product_new(amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime)")
			.beanMapped()
			.build();
	}

}