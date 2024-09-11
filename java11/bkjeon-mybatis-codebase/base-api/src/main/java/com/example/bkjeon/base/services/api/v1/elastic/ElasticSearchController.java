package com.example.bkjeon.base.services.api.v1.elastic;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/elasticsearch")
public class ElasticSearchController {

    private final ESIndexService esIndexService;
    private final ESSearchService esSearchService;

    @ApiOperation(".kibana 인덱스 카운트 조회")
    @GetMapping("searchTotalCnt")
    public long getDocTotalCnt() {
        return esIndexService.getDocTotalCnt();
    }

    @ApiOperation(".kibana 리스트 조회")
    @GetMapping("searchList")
    public void getSearchList() {
        esSearchService.getKibanaList();
    }

}
