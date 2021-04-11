package com.example.bkjeon.feature.crawler;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NaverShoppingBeautyProductMapper {

    int insertNaverShoppingBeautyProductList(List<NaverShoppingBeautyProduct> naverShoppingBeautyProductList);

    int updateNaverShoppingBeautyProductList(List<NaverShoppingBeautyProduct> naverShoppingBeautyProductList);

    int deleteNaverShoppingBeautyProduct(@Param("logYmd") String logYmd, @Param("categoryIds") String categoryIds);

}
