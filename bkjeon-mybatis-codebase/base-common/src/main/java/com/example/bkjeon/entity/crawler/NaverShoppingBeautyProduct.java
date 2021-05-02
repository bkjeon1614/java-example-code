package com.example.bkjeon.entity.crawler;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverShoppingBeautyProduct {

    private String logYmd;
    private String productId;
    private String productName;
    private String categoryId;
    private Integer productRank;

}
