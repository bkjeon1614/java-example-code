package com.example.bkjeon.job.crawler;

import com.example.bkjeon.entity.crawler.NaverShoppingDataLabClickRate;
import com.example.bkjeon.job.crawler.service.NaverShoppingDataLabClickRateService;
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
public class NaverShoppingDataLabClickRateJobConfiguration {

    private int insertCnt = 0;
    private int currentCategoryIdIndex = 0;
    private String logYmd;
    private String startDate;
    private String endDate;
    private String categoryIds;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final NaverShoppingDataLabClickRateService naverShoppingDataLabClickRateService;

    @Bean
    public Job naverShoppingDataLabClickRateJob() {
        return jobBuilderFactory.get("naverShoppingDataLabClickRateJob")
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start Naver Shopping DataLab Click Rate Crawling Before Job!!!");

                        logYmd = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("logYmd"),
                            DateUtil.date("yyyyMMdd", new Date())
                        ).trim();

                        startDate = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("startDate"),
                            DateUtil.date("yyyyMMdd", new Date())
                        ).trim();

                        endDate = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("endDate"),
                            DateUtil.date("yyyyMMdd", new Date())
                        ).trim();

                        categoryIds = StringUtils.defaultIfBlank(
                            jobExecution.getJobParameters().getString("categoryIds"),
                            "50000463,50000289,50000190,50000192,50000391,50000202"
                        ).trim();
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info(
                            ">>>>>>>>>>>>>>>>>>>>>>>>>>>> Start Naver Shopping DataLab Click Rate Crawling After Job!!! {}",
                            jobExecution.getExitStatus()
                         );
                    }
                })
                .incrementer(new RunIdIncrementer())
                .start(naverShoppingDataLabClickRateJobInitializeDBStep())
                .next(naverShoppingDataLabClickCrawlingStep())
                .on("CONTINUE")
                .to(naverShoppingDataLabClickCrawlingStep())
                .on("FINISHED")
                .end()
                .end()
                .build();
    }

    @Bean("naverShoppingDataLabClickRateJobInitializeDBStep")
    public Step naverShoppingDataLabClickRateJobInitializeDBStep() {
        return stepBuilderFactory.get("naverShoppingDataLabClickRateJobInitializeDBStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> logYmd: {}, startDate: {}, endDate: {}", logYmd, startDate, endDate);
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> categoryIds: {}", categoryIds);
                    int delCnt = naverShoppingDataLabClickRateService.delNaverShoppingDataLabClickRate(logYmd, categoryIds);
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Delete Count: {}", delCnt);
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> naverShoppingDataLabClickRateJobInitializeDBStep Finished");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step naverShoppingDataLabClickCrawlingStep() {
        return stepBuilderFactory.get("naverShoppingDataLabClickCrawlingStep")
                .<NaverShoppingDataLabClickRate, NaverShoppingDataLabClickRate>chunk(100)
                .reader(naverShoppingDataLabClickCrawlingStepReader())
                .writer(naverShoppingDataLabClickCrawlingStepWriter())
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

    @Bean("naverShoppingDataLabClickCrawlingStepReader")
    @StepScope
    public ListItemReader<NaverShoppingDataLabClickRate> naverShoppingDataLabClickCrawlingStepReader() {
        return new ListItemReader<>(
            naverShoppingDataLabClickRateService.getNaverShoppingDataLabClickRateCrawling(
                logYmd,
                startDate,
                endDate,
                categoryIds.split(","),
                currentCategoryIdIndex
            )
        );
    }

    @Bean("naverShoppingDataLabClickCrawlingStepWriter")
    @StepScope
    public ItemWriter<NaverShoppingDataLabClickRate> naverShoppingDataLabClickCrawlingStepWriter() {
        return items -> {
            insertCnt += naverShoppingDataLabClickRateService.setNaverShoppingDataLabClickRate((List<NaverShoppingDataLabClickRate>) items);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Finished Insert Count: {}", insertCnt);
        };
    }

}
