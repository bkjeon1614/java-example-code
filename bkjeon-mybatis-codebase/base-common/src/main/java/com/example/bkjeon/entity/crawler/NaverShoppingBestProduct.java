package com.example.bkjeon.entity.crawler;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverShoppingBestProduct {

    private String logYmd;
    private String productId;
    private String productName;
    private String categoryId;
    private Integer productRank;
    private String brandName;
    private String productRegisterYmd;
    private int zzimCount;
    private float reviewScore;
    private int sellerCount;
    private int shippingPrice;
    private int productPrice;

}
