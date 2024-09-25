package com.example.bkjeon.base.services.api.v1.cache;

import com.example.bkjeon.constants.BkjeonConstant;
import com.example.bkjeon.entity.cache.CacheExampleData;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.response.ApiResponse;
import com.example.bkjeon.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CacheService {

    public ResponseEntity getNoCacheExampleList(String exampleType) {
        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.OK.value(),
                    ResponseResult.SUCCESS.getText(),
                    null,
                    getExampleList(exampleType)
                ),
                HttpStatus.OK
            );
            ThreadUtil.threadSleep(2000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return responseEntity;
    }

    @Cacheable(value = "exampleCache", key = "#exampleType")
    public ResponseEntity getCacheExampleList(String exampleType) {
        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.OK.value(),
                    ResponseResult.SUCCESS.getText(),
                    null,
                    getExampleList(exampleType)
                ),
                HttpStatus.OK
            );
            ThreadUtil.threadSleep(2000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return responseEntity;
    }

    @CacheEvict(value = "exampleCache", key = "#exampleType")
    public ResponseEntity getClearCacheExampleList(String exampleType) {
        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.OK.value(),
                    ResponseResult.SUCCESS.getText(),
                    null,
                    getExampleList(exampleType)
                ),
                HttpStatus.OK
            );
            ThreadUtil.threadSleep(2000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return responseEntity;
    }

    public List<CacheExampleData> getExampleList(String exampleType) {
        List<CacheExampleData> exampleList = new ArrayList<>();

        for (int i=1; i<7; i++) {
            CacheExampleData cacheExampleData = CacheExampleData.builder()
                    .exampleNo(i)
                    .writer(exampleType)
                    .build();
            exampleList.add(cacheExampleData);
        }

        return exampleList;
    }

    @Cacheable(value = "foo", key = "#exampleType", cacheManager = "isLettuceCacheManager")
    public List<CacheExampleData> getRedisExampleList(String exampleType) {
        List<CacheExampleData> exampleList = new ArrayList<>();

        for (int i=1; i<7; i++) {
            CacheExampleData cacheExampleData = CacheExampleData.builder()
                .exampleNo(i)
                .writer(exampleType)
                .build();
            exampleList.add(cacheExampleData);
        }

        return exampleList;
    }

    @Cacheable(
        value = BkjeonConstant.REDIS_KEY_PREFIX_EXAMPLE_OBJ,
        keyGenerator = "cacheShardKeyGeneratorExample",
        cacheManager = "isLettuceCacheManager",
        condition = "#cacheExampleDataParam.getExampleNo() != 0 and #cacheExampleDataParam.getWriter() != ''")
    public List<CacheExampleData> getCacheGeneratorList(CacheExampleData cacheExampleDataParam) {
        List<CacheExampleData> exampleList = new ArrayList<>();
        for (int i=1; i<7; i++) {
            CacheExampleData cacheExampleData = CacheExampleData.builder()
                .exampleNo(i)
                .writer(cacheExampleDataParam.getWriter())
                .build();
            exampleList.add(cacheExampleData);
        }

        return exampleList;
    }

}
