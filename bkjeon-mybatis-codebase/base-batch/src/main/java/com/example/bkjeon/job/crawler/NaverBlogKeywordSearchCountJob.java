package com.example.bkjeon.job.crawler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class NaverBlogKeywordSearchCountJob {

    private String[] naverBlogKeywordSearchAndCategoryIdArr;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    /**
    @Bean
    public Job naverBlogKeywordSearchCountJob() {
        return jobBuilderFactory.get("naverBlogKeywordSearchCountJob")
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start Naver Blog Keyword Search Count Crawling Before Job!!!");

                        naverBlogKeywordSearchAndCategoryIdArr = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("searchKeywordAndCategoryNo"),
                            "남자화장품추천 | 남성올인원추천 | 남자메이크업:50000202"
                        ).split(",");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info(
                            ">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start Naver Blog Keyword Search Count Crawling After Job!!! {}",
                            jobExecution.getExitStatus()
                         );
                    }
                })
    }
    */

}
