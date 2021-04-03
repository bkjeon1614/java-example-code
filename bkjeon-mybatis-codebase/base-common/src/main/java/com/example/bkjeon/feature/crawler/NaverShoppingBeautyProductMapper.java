package com.example.bkjeon.feature.crawler;

import org.apache.ibatis.annotations.Param;

public interface NaverShoppingBeautyProductMapper {

    int insertNaverShoppingBeautyProduct(NaverShoppingBeautyProduct naverShoppingBeautyProduct);

    int deleteNaverShoppingBeautyProduct(@Param("logYmd") String logYmd, @Param("categoryIds") String categoryIds);

}
