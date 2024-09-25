package com.bkjeon.feature.entity.sample;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class Sample {

    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

    @Builder
    public Sample(Long id, Long amount, String txName, LocalDateTime txDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }

}