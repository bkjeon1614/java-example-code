package com.bkjeon.example.feature.example;

import com.bkjeon.example.config.mybatis.MySqlConnMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MySqlConnMapper
public interface ExampleMapper {

    List<Example> selectExampleList(
        @Param("size") Integer size,
        @Param("offset") Integer offset,
        @Param("param") ExampleParam param
    );
    int selectExampleListTotalCnt(@Param("param") ExampleParam param);
    Example selectExampleInfo(@Param("id") Integer id);
    void insertExample(@Param("data") Example data);
    void updateExample(@Param("data") Example data);
    void updateExampleName(@Param("data") Example data);
    void deleteExample(@Param("data") Example data);

}
