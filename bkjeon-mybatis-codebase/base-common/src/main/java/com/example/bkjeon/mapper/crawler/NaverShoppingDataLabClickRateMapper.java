package com.example.bkjeon.mapper.crawler;

import com.example.bkjeon.config.mybatis.MySqlConnMapper;
import com.example.bkjeon.entity.crawler.NaverShoppingDataLabClickRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MySqlConnMapper
public interface NaverShoppingDataLabClickRateMapper {

    int insertNaverShoppingDataLabClickRateList(List<NaverShoppingDataLabClickRate> naverShoppingDataLabClickRateList);

    int deleteNaverShoppingDataLabClickRate(@Param("logYmd") String logYmd, @Param("categoryIds") String categoryIds);

}
