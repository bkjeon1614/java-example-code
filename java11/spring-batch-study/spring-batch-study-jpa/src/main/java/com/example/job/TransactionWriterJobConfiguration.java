package com.example.job;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.entity.Teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TransactionWriterJobConfiguration {

	public static final String JOB_NAME = "transactionWriterBatch";
	public static final String BEAN_PREFIX = JOB_NAME + "_";

	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;
	private final EntityManagerFactory emf;

	@Value("${chunkSize:1000}")
	private int chunkSize;

	@Bean(JOB_NAME)
	public Job job() {
		return jobBuilderFactory.get(JOB_NAME)
			.preventRestart()
			.start(step())
			.build();
	}

	@Bean(BEAN_PREFIX + "step")
	@JobScope
	public Step step() {
		return stepBuilderFactory.get(BEAN_PREFIX + "step")
			.<Teacher, Teacher>chunk(chunkSize)
			.reader(jpaItemReader())
			.writer(teacherItemWriter())
			.build();
	}

	@Bean
	public JpaPagingItemReader<Teacher> jpaItemReader() {
		return new JpaPagingItemReaderBuilder<Teacher>()
			.name(BEAN_PREFIX + "reader")
			.entityManagerFactory(emf)
			.pageSize(chunkSize)
			.queryString("SELECT t FROM Teacher t")
			.build();
	}

	private ItemWriter<Teacher> teacherItemWriter() {
		return items -> {
			log.info(">>>>>>>>>>> Item Write");
			for (Teacher item : items) {
				log.info("teacher={}, student Size={}", item.getName(), item.getStudents().size());
			}
		};
	}

}