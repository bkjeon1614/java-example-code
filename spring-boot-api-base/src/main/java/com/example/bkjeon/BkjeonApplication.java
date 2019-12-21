package com.example.bkjeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BkjeonApplication {

    public static void main(String[] args) {
        SpringApplication.run(BkjeonApplication.class, args);
    }

}
