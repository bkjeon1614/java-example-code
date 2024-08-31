package com.example.bkjeon.component.redis;

import com.example.bkjeon.entity.cache.CacheExampleData;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

@Component
public class CacheShardKeyGeneratorExample implements KeyGenerator {

    @Value("${spring.profiles}")
    private String profiles;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        CacheExampleData cacheExampleData = (CacheExampleData) params[0];
        StringBuilder key = new StringBuilder()
            .append(StringUtils.defaultString(profiles, "null"))
            .append(":").append(StringUtils.defaultString("shard-" + (int)(Math.random() * 2),
                "null"))
            .append(":").append(StringUtils.defaultString(
                String.valueOf(cacheExampleData.getExampleNo()), "null"))
            .append(":").append(StringUtils.defaultString(
                cacheExampleData.getWriter(), "null"));

        return key.toString();
    }

}
