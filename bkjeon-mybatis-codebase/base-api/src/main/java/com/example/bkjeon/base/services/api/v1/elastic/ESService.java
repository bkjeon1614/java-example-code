package com.example.bkjeon.base.services.api.v1.elastic;

import org.elasticsearch.common.xcontent.XContentBuilder;

public interface ESService {

    long getDocTotalCnt();

    boolean createIndex(
        String indexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    );

    boolean createAliasIndex(
        String indexName,
        String aliasIndexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    );

    boolean setIndexAlias(String indexName, String aliasIndexName);

    boolean changeIndexAlias(
        String beforeIndexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder,
        String indexName,
        String aliasIndexName
    );

    boolean selectIndexCheck(String indexName);

    boolean deleteIndex(String indexName);

    void insertData(String indexName, String documentId, String dataString);

    void bulkInsertData();

}
