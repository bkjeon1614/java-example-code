package com.example.bkjeon.base.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@EnableJpaAuditing
@Configuration
@EnableJpaRepositories(
    basePackages = "com.example.bkjeon.base.repository",
    entityManagerFactoryRef = "baseEntityManager",
    transactionManagerRef = "baseTransactionManager"
)
@RequiredArgsConstructor
public class DataSourceConfig {

    private static final String AES_KEY = "bkjeon!@";

    private final Environment env;

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean baseEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(new String[] {"kr.co.oliveyoung.auth.domain"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));

        String aesKey = System.getProperty("mysql.aeskey");
        if (aesKey == null || !AES_KEY.equals(aesKey)) {
            throw new IllegalArgumentException(String.format("aeskey error!!: [%s]", aesKey));
        }
        properties.put("AES_KEY", aesKey);

        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager baseTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(baseEntityManager().getObject());

        return transactionManager;
    }

}
