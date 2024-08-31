package com.example.bkjeon.base.services.api.v1.cache;

import com.example.bkjeon.entity.cache.CacheExampleData;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/redis")
public class RedisController {

    private final CacheService cacheService;

    @ApiOperation("Cache Example Data List")
    @GetMapping("examples/cache")
    public List<CacheExampleData> getRedisExampleList(
        @ApiParam(
            value = "bkjeon: bkjeon관련 데이터, example:example 관련 데이터",
            name = "exampleType",
            required = true
        ) @RequestParam String exampleType
    ) {
        return cacheService.getRedisExampleList(exampleType);
    }

    @ApiOperation("Key Generator 사용")
    @GetMapping("examples/cacheGenerator")
    public List<CacheExampleData> getCacheGenerator() {
        return cacheService.getCacheGeneratorList(CacheExampleData.builder().exampleNo(1).writer("admin").build());
    }

}
