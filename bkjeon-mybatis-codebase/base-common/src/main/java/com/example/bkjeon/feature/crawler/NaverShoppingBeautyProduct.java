package com.example.bkjeon.feature.crawler;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverShoppingBeautyProduct {

    private String logYmd;
    private String productNo;
    private String productName;
    private String categoryNo;
    private Integer productRank;

}
