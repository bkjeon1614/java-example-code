package com.example.bkjeon.job.crawler;

import com.example.bkjeon.common.utils.DateUtil;
import com.example.bkjeon.feature.crawler.NaverShoppingBeautyProduct;
import com.example.bkjeon.job.crawler.service.NaverShoppingBeautyProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class NaverShoppingBeautyProductJobConfiguration {

    private String logYmd;
    private String[] categoryNoArr;
    private String[] categoryNameArr;
    private int currentcategoryNoIndex = 0;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final NaverShoppingBeautyProductService naverShoppingBeautyProductService;

    @Bean
    public Job naverShoppingBeautyProductJob() {
        return jobBuilderFactory.get("naverShoppingBeautyProductJob")
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start Naver Shopping Beauty Product Crawling Before Job!!!");

                        logYmd = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("logYmd"),
                            DateUtil.date("yyyyMMdd", new Date())
                        );

                        categoryNoArr = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("categoryIds"),
                            "10003314,10003368,10003291,10003292,10003340,10003399"
                        ).split(",");

                        categoryNameArr = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("categoryNames"),
                            "마스크시트,핸드크림,스킨케어,클렌징,립스틱,맨즈케어"
                        ).split(",");

                        log.info("Log Ymd {}", logYmd);
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info(
                            ">>>>>>>>>>>>>>>>>>>>>>>>>>>> End Naver Shopping Beauty Product Crawling After Job!!! {}",
                            jobExecution.getExitStatus()
                        );
                    }
                })
                .incrementer(new RunIdIncrementer())
                .start(naverShoppingBeautyProductInitializeDBStep())
                .next(naverShoppingBeautyProductCrawlingStep())
                .on("CONTINUE")
                .to(naverShoppingBeautyProductCrawlingStep())
                .on("FINISHED")
                .end()
                .end()
                .build();
    }

    @Bean("naverShoppingBeautyProductInitializeDBStep")
    public Step naverShoppingBeautyProductInitializeDBStep() {
        return stepBuilderFactory.get("naverShoppingBeautyProductInitializeDBStep")
                .tasklet((contribution, chunkContext) -> {
                    // 삭제(=초기화) repo 필요
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> naverShoppingBeautyProductInitializeDBStep Finished");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step naverShoppingBeautyProductCrawlingStep() {
        return stepBuilderFactory.get("naverShoppingBeautyProductCrawlingStep")
                .<NaverShoppingBeautyProduct, NaverShoppingBeautyProduct>chunk(100)
                .reader(naverShoppingBeautyProductCrawlingStepReader())
                .writer(naverShoppingBeautyProductCrawlingStepWriter())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {

                    }

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        currentcategoryNoIndex++;
                        if (currentcategoryNoIndex < categoryNoArr.length) {
                            return new ExitStatus("CONTINUE");
                        } else {
                            return new ExitStatus("FINISHED");
                        }
                    }
                })
                .build();
    }

    @Bean("naverShoppingBeautyProductCrawlingStepReader")
    @StepScope
    public ListItemReader<NaverShoppingBeautyProduct> naverShoppingBeautyProductCrawlingStepReader() {
        return new ListItemReader<>(
                naverShoppingBeautyProductService.getNaverShoppingBeautyProductCrawling(
                    categoryNoArr,
                    currentcategoryNoIndex
                )
        );
    }

    @Bean("naverShoppingBeautyProductCrawlingStepWriter")
    @StepScope
    public ItemWriter<NaverShoppingBeautyProduct> naverShoppingBeautyProductCrawlingStepWriter() {
        return items -> items
                .stream()
                .forEach(item -> {
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Writer: {}", item.toString());
                    // naverShoppingBeautyProductService.setNaverShoppingBeautyProduct(item);
                });
    }

}
