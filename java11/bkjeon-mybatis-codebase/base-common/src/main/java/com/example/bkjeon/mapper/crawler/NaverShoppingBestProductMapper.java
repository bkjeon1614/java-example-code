package com.example.bkjeon.mapper.crawler;

import com.example.bkjeon.config.mybatis.MySqlConnMapper;
import com.example.bkjeon.entity.crawler.NaverShoppingBestProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MySqlConnMapper
public interface NaverShoppingBestProductMapper {

    int insertNaverShoppingBestProductList(List<NaverShoppingBestProduct> naverShoppingBestProductList);

    int deleteNaverShoppingBestProduct(@Param("logYmd") String logYmd, @Param("categoryIds") String categoryIds);

}
