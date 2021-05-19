package com.example.bkjeon.base.services.api.v1.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

public interface ESService {

    boolean createIndex(
        RestHighLevelClient client,
        String indexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    );

    boolean createAliasIndex(
        RestHighLevelClient client,
        String indexName,
        String aliasIndexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    );

    boolean setIndexAlias(RestHighLevelClient client, String indexName, String aliasIndexName);

    boolean changeIndexAlias(
        RestHighLevelClient client,
        String beforeIndexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder,
        String indexName,
        String aliasIndexName
    );

    boolean selectIndexCheck(RestHighLevelClient client, String indexName);

    boolean deleteIndex(RestHighLevelClient client, String indexName);

    void insertData(RestHighLevelClient client, String indexName, String documentId,
        String dataString);

    void bulkInsertData();

}
