package com.example.bkjeon;

import com.example.bkjeon.base.aspect.CommonLogAspect;
import com.example.bkjeon.base.aspect.SelectLogAspect;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan("com.example.bkjeon.feature")
public class BkjeonApplication {

    public static void main(String[] args) {
        SpringApplication.run(BkjeonApplication.class, args);
    }

    @Bean
    public CommonLogAspect commonLogAspect() {
        return new CommonLogAspect();
    }

    @Bean
    public SelectLogAspect selectLogAspect() {
        return new SelectLogAspect();
    }

}
