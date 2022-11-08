package com.example.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
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
public class JdbcCursorItemReaderJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource;

	private static final int chunkSize = 10;

	@Bean
	public Job jdbcCursorItemReaderJob() {
		return jobBuilderFactory.get("jdbcCursorItemReaderJob")
			.start(jdbcCursorItemReaderStep())
			.build();
	}

	@Bean
	public Step jdbcCursorItemReaderStep() {
		return stepBuilderFactory.get("jdbcCursorItemReaderStep")
			.<Product, Product>chunk(chunkSize)
			.reader(jdbcCursorItemReader())
			.writer(jdbcCursorItemWriter())
			.build();
	}

	@Bean
	public JdbcCursorItemReader<Product> jdbcCursorItemReader() {
		return new JdbcCursorItemReaderBuilder<Product>()
			.fetchSize(chunkSize)
			.dataSource(dataSource)
			.rowMapper(new BeanPropertyRowMapper<>(Product.class))
			.sql("SELECT id, amount, tx_name, tx_date_time FROM product")
			.name("jdbcCursorItemReader")
			.build();
	}

	private ItemWriter<Product> jdbcCursorItemWriter() {
		return list -> {
			for (Product pay: list) {
				log.info("Current Product={}", pay);
			}
		};
	}

}
