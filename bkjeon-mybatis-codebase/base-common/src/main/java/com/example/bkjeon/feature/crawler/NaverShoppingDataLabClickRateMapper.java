package com.example.bkjeon.feature.crawler;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NaverShoppingDataLabClickRateMapper {

    int insertNaverShoppingDataLabClickRateList(List<NaverShoppingDataLabClickRate> naverShoppingDataLabClickRateList);

    int deleteNaverShoppingDataLabClickRate(@Param("logYmd") String logYmd, @Param("categoryIds") String categoryIds);

}
