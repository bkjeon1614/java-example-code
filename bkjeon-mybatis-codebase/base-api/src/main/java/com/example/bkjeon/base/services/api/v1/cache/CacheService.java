package com.example.bkjeon.base.services.api.v1.cache;

import com.example.bkjeon.entity.cache.CacheExampleData;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.ApiResponseMessage;
import com.example.bkjeon.util.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CacheService {

    public ApiResponseMessage getNoCacheExampleList(String exampleType) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "Cache 조회가 완료되었습니다."
        );

        try {
            result.setContents(getExampleList(exampleType));
            ThreadUtil.threadSleep(2000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return result;
    }

    @Cacheable(value = "exampleCache", key = "#exampleType")
    public ApiResponseMessage getCacheExampleList(String exampleType) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "Cache 조회가 완료되었습니다."
        );

        try {
            result.setContents(getExampleList(exampleType));
            ThreadUtil.threadSleep(2000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return result;
    }

    @CacheEvict(value = "exampleCache", key = "#exampleType")
    public ApiResponseMessage getClearCacheExampleList(String exampleType) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "NoCache 조회가 완료되었습니다."
        );

        try {
            result.setContents(getExampleList(exampleType));
            ThreadUtil.threadSleep(2000);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return result;
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

}
