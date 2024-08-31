package com.example.bkjeon;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Spring Batch Job Application
 */
@Slf4j
@EnableBatchProcessing
@MapperScan(basePackages = {"com.example.bkjeon.mapper"})
@SpringBootApplication
public class BatchApplication extends DefaultBatchConfigurer {

    @Value("${spring.batch.job.names:NONE}")
    private String jobNames;

    public static void main(String[] args) {
        int exitCode = SpringApplication.exit(SpringApplication.run(BatchApplication.class, args));
        log.info("exitCode={}", exitCode);
        System.exit(exitCode);
    }

    @PostConstruct
    public void validateJobNames() {
        log.info("jobNames: {}", jobNames);
        if (jobNames.isEmpty() || jobNames.equals("NONE")) {
            // ex) --job.name=sampleJob, --job.name=sampleJob, sampleJob2
            throw new IllegalStateException("Job Name Empty !!");
        }
    }

    @Override
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

}
