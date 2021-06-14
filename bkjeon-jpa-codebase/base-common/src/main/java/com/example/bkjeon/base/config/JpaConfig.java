package com.example.bkjeon.base.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
