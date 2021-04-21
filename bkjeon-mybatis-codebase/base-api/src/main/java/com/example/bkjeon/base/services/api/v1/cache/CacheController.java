package com.example.bkjeon.base.services.api.v1.cache;

import com.example.bkjeon.model.ApiResponseMessage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/cache")
public class CacheController {

    private final CacheService cacheService;

    @ApiOperation("Cache Example Data List")
    @GetMapping("examples/cache")
    public ApiResponseMessage getCacheExampleList(
        @ApiParam(
            value = "bkjeon: bkjeon관련 데이터, example:example 관련 데이터",
            name = "exampleType",
            required = true
        ) @RequestParam String exampleType
    ) {
        return cacheService.getCacheExampleList(exampleType);
    }

    @ApiOperation("NoCache Example Data List")
    @GetMapping("examples/noCache")
    public ApiResponseMessage getNoCacheExampleList(
        @ApiParam(
            value = "bkjeon: bkjeon관련 데이터, example:example 관련 데이터",
            name = "exampleType",
            required = true
        ) @RequestParam String exampleType
    ) {
        return cacheService.getNoCacheExampleList(exampleType);
    }

    @ApiOperation("Clear Cache Example Data List")
    @GetMapping("examples/clearCache")
    public ApiResponseMessage getClearCacheExampleList(
        @ApiParam(
            value = "bkjeon: bkjeon관련 데이터, example:example 관련 데이터",
            name = "exampleType",
            required = true
        ) @RequestParam String exampleType
    ) {
        return cacheService.getClearCacheExampleList(exampleType);
    }

}
