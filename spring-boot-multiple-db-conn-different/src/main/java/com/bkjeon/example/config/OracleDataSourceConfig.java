package com.bkjeon.example.config;

import com.bkjeon.example.config.mybatis.MybatisOracleConfigurationSupport;
import com.bkjeon.example.config.mybatis.OracleConnMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan(
    basePackages = "com.bkjeon.example.feature",
    annotationClass = OracleConnMapper.class,
    sqlSessionFactoryRef = "oracleSessionFactory")
public class OracleDataSourceConfig {

  @Autowired
  private MybatisOracleConfigurationSupport myBatisConfigurationSupport;

  @Primary
  @Bean(name = "oracleDataSource")
  @ConfigurationProperties(prefix = "spring.oracle.datasource")
  public DataSource oracleDataSource() {
    return DataSourceBuilder.create().build();
  }

  @Primary
  @Bean(name = "oracleSessionFactory")
  public SqlSessionFactory oracleSessionFactory(
      @Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
    return myBatisConfigurationSupport.build(dataSource);
  }

  @Bean(name="oracleSession")
  public SqlSessionTemplate mySqlSession(@Qualifier("oracleSessionFactory") SqlSessionFactory oracleSessionFactory) throws Exception {
    return new SqlSessionTemplate(oracleSessionFactory);
  }
}
