package com.example.bkjeon.config.mybatis;

import javax.sql.DataSource;
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

@Primary
@Configuration
@MapperScan(
    value = "com.example.bkjeon.mapper",
    annotationClass = MySqlConnMapper.class,
    sqlSessionFactoryRef = "mySqlSessionFactory"
)
public class MySqlDataSourceConfig {

    private static final String AES_KEY = "bkjeon!@";

    @Autowired
    private MybatisMySqlConfigurationSupport mybatisMySqlConfigurationSupport;

    @Bean(name = "mySqlDataSource")
    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource mySqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mySqlSessionFactory")
    public SqlSessionFactory mySqlSessionFactory(@Qualifier("mySqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactory factory = mybatisMySqlConfigurationSupport.build(dataSource);

        String aesKey = System.getProperty("mysql.aeskey");
        if (aesKey == null || !AES_KEY.equals(aesKey)) {
            aesKey = System.getenv("mysql.aeskey");
        }

        factory.getConfiguration().getVariables().put("AES_KEY", aesKey);
        return mybatisMySqlConfigurationSupport.build(dataSource);
    }

    @Bean(name="mySqlSession")
    public SqlSessionTemplate mySqlSession(@Qualifier("mySqlSessionFactory") SqlSessionFactory mySqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(mySqlSessionFactory);
    }

}
