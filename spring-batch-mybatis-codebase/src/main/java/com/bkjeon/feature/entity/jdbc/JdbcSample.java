package com.bkjeon.feature.entity.jdbc;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@ToString
@Getter
@NoArgsConstructor
public class JdbcSample {

    private Long id;
    private Long amount;
    private String txName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime txDateTime;

    @Builder
    public JdbcSample(Long id, Long amount, String txName, LocalDateTime txDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }

}