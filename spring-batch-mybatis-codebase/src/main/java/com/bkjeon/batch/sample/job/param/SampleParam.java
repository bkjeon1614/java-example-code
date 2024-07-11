package com.bkjeon.batch.sample.job.param;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@NoArgsConstructor
@JobScope
@Component
public class SampleParam {

    private LocalDate date;
    private String requestDate;

    @Value("${chunk-size:1000}")
    private int chunkSize;

    @Value("#{jobParameters[requestDate]}")
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    @Value("#{jobParameters[date]}")
    public void setDate(String date) {
        this.date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

}
