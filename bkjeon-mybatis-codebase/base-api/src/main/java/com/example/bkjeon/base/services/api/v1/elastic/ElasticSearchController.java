package com.example.bkjeon.base.services.api.v1.elastic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/elasticsearch")
public class ElasticSearchController {

    private final ESService esService;

    @ApiOperation("게시글 리스트 조회")
    @GetMapping
    public long getDocTotalCnt() {
        return esService.getDocTotalCnt();
    }

}
