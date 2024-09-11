package com.bkjeon.example.config.mybatis;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Slf4j
@Getter
public class MybatisConfigurationSupport {

  private final MybatisProperties mybatisProperties;
  private final Interceptor[] interceptors;
  private final ResourceLoader resourceLoader;
  private final DatabaseIdProvider databaseIdProvider;

  public MybatisConfigurationSupport(
      MybatisProperties mybatisProperties,
      ObjectProvider<Interceptor[]> interceptorsProvider,
      ResourceLoader resourceLoader,
      ObjectProvider<DatabaseIdProvider> databaseIdProvider) {
    this.mybatisProperties = mybatisProperties;
    this.interceptors = interceptorsProvider.getIfAvailable();
    this.resourceLoader = resourceLoader;
    this.databaseIdProvider = databaseIdProvider.getIfAvailable();
    log.info("=============================== mybatis ===============================");
  }

  public SqlSessionFactory build(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource);
    sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
    if (StringUtils.hasText(mybatisProperties.getConfigLocation())) {
      sqlSessionFactoryBean
          .setConfigLocation(resourceLoader.getResource(mybatisProperties.getConfigLocation()));
    }
    if (mybatisProperties.getConfiguration() != null) {
      Configuration configuration = new Configuration();
      BeanUtils.copyProperties(mybatisProperties.getConfiguration(), configuration);
      sqlSessionFactoryBean.setConfiguration(configuration);
    }
    if (mybatisProperties.getConfigurationProperties() != null) {
      sqlSessionFactoryBean
          .setConfigurationProperties(mybatisProperties.getConfigurationProperties());
    }
    if (!ObjectUtils.isEmpty(interceptors)) {
      sqlSessionFactoryBean.setPlugins(interceptors);
    }
    if (databaseIdProvider != null) {
      sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
    }
    if (StringUtils.hasLength(mybatisProperties.getTypeAliasesPackage())) {
      sqlSessionFactoryBean.setTypeAliasesPackage(mybatisProperties.getTypeAliasesPackage());
    }
    if (StringUtils.hasLength(mybatisProperties.getTypeHandlersPackage())) {
      sqlSessionFactoryBean.setTypeHandlersPackage(mybatisProperties.getTypeHandlersPackage());
    }
    if (!ObjectUtils.isEmpty(mybatisProperties.resolveMapperLocations())) {
      sqlSessionFactoryBean.setMapperLocations(mybatisProperties.resolveMapperLocations());
    }
    return sqlSessionFactoryBean.getObject();
  }

}
