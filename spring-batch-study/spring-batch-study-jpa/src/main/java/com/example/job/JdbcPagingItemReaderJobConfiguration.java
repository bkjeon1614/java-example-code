package com.example.job;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.entity.Product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JdbcPagingItemReaderJobConfiguration {

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final DataSource dataSource; // DataSource DI

	private static final int chunkSize = 10;

	@Bean
	public Job jdbcPagingItemReaderJob() throws Exception {
		return jobBuilderFactory.get("jdbcPagingItemReaderJob")
			.start(jdbcPagingItemReaderStep())
			.build();
	}

	@Bean
	public Step jdbcPagingItemReaderStep() throws Exception {
		return stepBuilderFactory.get("jdbcPagingItemReaderStep")
			.<Product, Product>chunk(chunkSize)
			.reader(jdbcPagingItemReader())
			.writer(jdbcPagingItemWriter())
			.build();
	}

	@Bean
	public JdbcPagingItemReader<Product> jdbcPagingItemReader() throws Exception {
		Map<String, Object> parameterValues = new HashMap<>();
		parameterValues.put("amount", 2000);

		return new JdbcPagingItemReaderBuilder<Product>()
			.pageSize(chunkSize)
			.fetchSize(chunkSize)
			.dataSource(dataSource)
			.rowMapper(new BeanPropertyRowMapper<>(Product.class))
			.queryProvider(createQueryProvider())
			.parameterValues(parameterValues)
			.name("jdbcPagingItemReader")
			.build();
	}

	private ItemWriter<Product> jdbcPagingItemWriter() {
		return list -> {
			for (Product pay: list) {
				log.info("Current Pay={}", pay);
			}
		};
	}

	@Bean
	public PagingQueryProvider createQueryProvider() throws Exception {
		SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
		queryProvider.setDataSource(dataSource); // Database 에 맞는 PagingQueryProvider 를 선택하기 위해
		queryProvider.setSelectClause("id, amount, tx_name, tx_date_time");
		queryProvider.setFromClause("from product");
		queryProvider.setWhereClause("where amount >= :amount");

		Map<String, Order> sortKeys = new HashMap<>(1);
		sortKeys.put("id", Order.ASCENDING);

		queryProvider.setSortKeys(sortKeys);

		return queryProvider.getObject();
	}

}
