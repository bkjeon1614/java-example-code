package com.example.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPagingItemReaderJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	private int chunkSize = 10;

	@Bean
	public Job jpaPagingItemReaderJob() {
		return jobBuilderFactory.get("jpaPagingItemReaderJob")
			.start(jpaPagingItemReaderStep())
			.build();
	}

	@Bean
	public Step jpaPagingItemReaderStep() {
		return stepBuilderFactory.get("jpaPagingItemReaderStep")
			.<Product, Product>chunk(chunkSize)
			.reader(jpaPagingItemReader())
			.writer(jpaPagingItemWriter())
			.build();
	}

	@Bean
	public JpaPagingItemReader<Product> jpaPagingItemReader() {
		return new JpaPagingItemReaderBuilder<Product>()
			.name("jpaPagingItemReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(chunkSize)
			.queryString("SELECT p FROM Product p WHERE amount >= 2000")
			.build();
	}

	private ItemWriter<Product> jpaPagingItemWriter() {
		return list -> {
			for (Product product: list) {
				log.info("Current Product={}", product);
			}
		};
	}

}