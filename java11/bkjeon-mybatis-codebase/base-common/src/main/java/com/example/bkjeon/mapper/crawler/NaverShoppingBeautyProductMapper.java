package com.example.bkjeon.mapper.crawler;

import com.example.bkjeon.config.mybatis.MySqlConnMapper;
import com.example.bkjeon.entity.crawler.NaverShoppingBeautyProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MySqlConnMapper
public interface NaverShoppingBeautyProductMapper {

    int insertNaverShoppingBeautyProductList(List<NaverShoppingBeautyProduct> naverShoppingBeautyProductList);

    int updateNaverShoppingBeautyProductList(List<NaverShoppingBeautyProduct> naverShoppingBeautyProductList);

    int deleteNaverShoppingBeautyProduct(@Param("logYmd") String logYmd, @Param("categoryIds") String categoryIds);

}
