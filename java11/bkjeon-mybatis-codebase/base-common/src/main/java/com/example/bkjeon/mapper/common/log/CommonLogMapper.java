package com.example.bkjeon.mapper.common.log;

import com.example.bkjeon.config.mybatis.MySqlConnMapper;
import com.example.bkjeon.entity.common.log.CommonLog;

@MySqlConnMapper
public interface CommonLogMapper {

    void insertLog(CommonLog commonLog);

}
