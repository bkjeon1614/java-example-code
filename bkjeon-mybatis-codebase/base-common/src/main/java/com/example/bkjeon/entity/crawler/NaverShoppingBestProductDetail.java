package com.example.bkjeon.entity.crawler;

import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverShoppingBestProductDetail {

    private String logYmd;
    private String categoryId;
    private String productId;
    private String brandName;
    private String productRegisterYmd;
    private int zzimCount;
    private float reviewScore;
    private int sellerCount;
    private int shippingPrice;
    private int productPrice;

}
