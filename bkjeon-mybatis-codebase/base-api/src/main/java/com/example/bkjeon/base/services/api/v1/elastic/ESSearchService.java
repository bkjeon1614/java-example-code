package com.example.bkjeon.base.services.api.v1.elastic;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ESSearchService {

    private final RestHighLevelClient restHighLevelClient;

    public void getKibanaList() {
        try {
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.filter(QueryBuilders.termQuery("_score", 1));

            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.from(0);
            sourceBuilder.size(10);
            sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            sourceBuilder.query(boolQueryBuilder);

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices("zipkin-span-2023-01-07");
            searchRequest.source(sourceBuilder);
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = response.getHits();

            // Gson gson = new GsonBuilder().create();
            for (SearchHit item: hits) {
                System.out.println(item.getSourceAsString());
                // displayShopESList.add(gson.fromJson(item.getSourceAsString(), DisplayShopES.class));
            }
        } catch (Exception e) {

        }
    }

}
