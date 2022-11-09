package com.example.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entity.Product;
import com.example.entity.ProductNew;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CustomItemWriterJobConfiguration {
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory entityManagerFactory;

	private static final int chunkSize = 10;

	@Bean
	public Job customItemWriterJob() {
		return jobBuilderFactory.get("customItemWriterJob")
			.start(customItemWriterStep())
			.build();
	}

	@Bean
	public Step customItemWriterStep() {
		return stepBuilderFactory.get("customItemWriterStep")
			.<Product, ProductNew>chunk(chunkSize)
			.reader(customItemWriterReader())
			.processor(customItemWriterProcessor())
			.writer(customItemWriter())
			.build();
	}

	@Bean
	public JpaPagingItemReader<Product> customItemWriterReader() {
		return new JpaPagingItemReaderBuilder<Product>()
			.name("customItemWriterReader")
			.entityManagerFactory(entityManagerFactory)
			.pageSize(chunkSize)
			.queryString("SELECT p FROM Product p")
			.build();
	}

	@Bean
	public ItemProcessor<Product, ProductNew> customItemWriterProcessor() {
		return product -> new ProductNew(product.getAmount(), product.getTxName(), product.getTxDateTime());
	}

	@Bean
	public ItemWriter<ProductNew> customItemWriter() {
		return items -> {
			for (ProductNew item : items) {
				System.out.println(item);
			}
		};
	}

}