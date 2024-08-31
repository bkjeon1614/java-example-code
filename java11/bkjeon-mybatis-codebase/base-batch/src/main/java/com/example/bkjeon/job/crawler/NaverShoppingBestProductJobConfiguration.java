package com.example.bkjeon.job.crawler;

import com.example.bkjeon.entity.crawler.NaverShoppingBestProduct;
import com.example.bkjeon.job.crawler.service.NaverShoppingBestProductService;
import com.example.bkjeon.util.DateUtil;
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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class NaverShoppingBestProductJobConfiguration {

    private String logYmd;
    private String categoryIds;
    private int currentCategoryIdIndex = 0;
    private int insertCnt = 0;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final NaverShoppingBestProductService naverShoppingBestProductService;

    @Bean
    public Job naverShoppingBestProductJob() {
        return jobBuilderFactory.get("naverShoppingBestProductJob")
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start Naver Shopping Best Product Crawling Before Job!!!");

                        logYmd = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("logYmd"),
                            DateUtil.date("yyyyMMdd", new Date())
                        ).trim();

                        // 입력받지 않았을 때 - 마스크팩,핸드크림,스킨케어,클렌징,립스틱,맨즈케어
                        categoryIds = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("categoryIds"),
                            "50000463,50000289,50000190,50000192,50000391,50000202"
                        ).trim();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info(
                            ">>>>>>>>>>>>>>>>>>>>>>>>>>>> End Naver Shopping Best Product Crawling After Job!!! {}",
                            jobExecution.getExitStatus()
                        );
                    }
                })
                .incrementer(new RunIdIncrementer())
                .start(naverShoppingBestProductJobInitializeDBStep())
                .next(naverShoppingBestProductCrawlingStep())
                .on("CONTINUE")
                .to(naverShoppingBestProductCrawlingStep())
                .on("FINISHED")
                .end()
                .end()
                .build();
    }

    @Bean("naverShoppingBestProductJobInitializeDBStep")
    public Step naverShoppingBestProductJobInitializeDBStep() {
        return stepBuilderFactory.get("naverShoppingBestProductJobInitializeDBStep")
                .tasklet((contribution, chunkContext) -> {
                    int delCnt = naverShoppingBestProductService.delNaverShoppingBestProduct(
                        logYmd,
                        categoryIds
                    );
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Delete logYmd: {}", logYmd);
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Delete categoryIds: {}", categoryIds);
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Delete Count: {}", delCnt);
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> naverShoppingBestProductJobInitializeDBStep Finished");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step naverShoppingBestProductCrawlingStep() {
        return stepBuilderFactory.get("naverShoppingBestProductCrawlingStep")
                .<NaverShoppingBestProduct, NaverShoppingBestProduct>chunk(100)
                .reader(naverShoppingBestProductCrawlingStepReader())
                .writer(naverShoppingBestProductCrawlingStepWriter())
                .listener(new StepExecutionListener() {
                    @Override
                    public void beforeStep(StepExecution stepExecution) {}

                    @Override
                    public ExitStatus afterStep(StepExecution stepExecution) {
                        currentCategoryIdIndex++;
                        if (currentCategoryIdIndex < categoryIds.split(",").length) {
                            return new ExitStatus("CONTINUE");
                        } else {
                            return new ExitStatus("FINISHED");
                        }
                    }
                })
                .build();
    }

    @Bean("naverShoppingBestProductCrawlingStepReader")
    @StepScope
    public ListItemReader<NaverShoppingBestProduct> naverShoppingBestProductCrawlingStepReader() {
        return new ListItemReader<>(
            naverShoppingBestProductService.getNaverShoppingBeautyProductCrawling(
                logYmd,
                categoryIds.split(","),
                currentCategoryIdIndex
            )
        );
    }

    @Bean("naverShoppingBestProductCrawlingStepWriter")
    @StepScope
    public ItemWriter<NaverShoppingBestProduct> naverShoppingBestProductCrawlingStepWriter() {
        return items -> {
            insertCnt += naverShoppingBestProductService.setNaverShoppingBestProduct((List<NaverShoppingBestProduct>) items);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Finished Insert Count: {}", insertCnt);
        };
    }

}
