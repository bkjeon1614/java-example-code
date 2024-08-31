package com.bkjeon.example.config.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
public class MybatisMySqlConfigurationSupport extends MybatisConfigurationSupport {

    private final MybatisProperties mybatisProperties;
    private final Interceptor[] interceptors;
    private final ResourceLoader resourceLoader;
    private final DatabaseIdProvider databaseIdProvider;

    public MybatisMySqlConfigurationSupport(MybatisProperties mybatisProperties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider) {
        super(mybatisProperties, interceptorsProvider, resourceLoader, databaseIdProvider);
        log.info("=============================== my sql ===============================");
        this.mybatisProperties = super.getMybatisProperties();
        this.interceptors = super.getInterceptors();
        this.resourceLoader = super.getResourceLoader();
        this.databaseIdProvider = super.getDatabaseIdProvider();
    }

    @Override
    public SqlSessionFactory build(DataSource dataSource) throws Exception {
        return super.build(dataSource);
    }
}
