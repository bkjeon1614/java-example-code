package com.bkjeon.feature.entity.sample;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class SampleOut {

    private Long id;
    private Long amount;
    private String txName;
    private LocalDateTime txDateTime;

    @Builder
    public SampleOut(Long id, Long amount, String txName, LocalDateTime txDateTime) {
        this.id = id;
        this.amount = amount;
        this.txName = txName;
        this.txDateTime = txDateTime;
    }

    public SampleOut(Sample sample) {
        this.id = sample.getId();
        this.amount = sample.getAmount();
        this.txName = sample.getTxName();
        this.txDateTime = sample.getTxDateTime();
    }

}