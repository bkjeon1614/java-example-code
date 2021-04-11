package com.example.bkjeon.feature.crawler;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverShoppingDataLabClickRate {

    private String categoryId;
    private String logYmd;
    private String period;
    private String clickRate;

}
