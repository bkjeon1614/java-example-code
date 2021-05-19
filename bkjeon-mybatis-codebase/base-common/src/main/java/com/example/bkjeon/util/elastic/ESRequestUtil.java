package com.example.bkjeon.util.elastic;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest.AliasActions.Type;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;

@Slf4j
public class ESRequestUtil {

    private ESRequestUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 인덱스 생성 요청 유틸
     * @param indexName
     * @param settingShardCnt
     * @param settingReplicaCnt
     * @param builder
     * @return
     */
    public static CreateIndexRequest getCreateIndexRequest(
        String indexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    ) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            .put("index.number_of_shards", settingShardCnt)
            .put("index.number_of_replicas", settingReplicaCnt)
        );
        createIndexRequest.mapping(builder);

        return createIndexRequest;
    }

    /**
     * 별칭 인덱스 생성 요청 유틸
     * @param indexName
     * @param settingShardCnt
     * @param settingReplicaCnt
     * @param builder
     * @param aliasIndexName
     * @return
     */
    public static CreateIndexRequest getCreateIndexAliasRequest(
        String indexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder,
        String aliasIndexName
    ) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        createIndexRequest.settings(Settings.builder()
            .put("index.number_of_shards", settingShardCnt)
            .put("index.number_of_replicas", settingReplicaCnt)
        );
        createIndexRequest.alias(new Alias(aliasIndexName));
        createIndexRequest.mapping(builder);

        return createIndexRequest;
    }

    /**
     * 인덱스 별칭 설정 요청 유틸
     * @param indexName
     * @param aliasIndexName
     * @return
     */
    public static IndicesAliasesRequest getCreateAliasRequest(
        String indexName,
        String aliasIndexName
    ) {
        IndicesAliasesRequest createAliasRequest = new IndicesAliasesRequest();
        AliasActions createAliasActions = new AliasActions(Type.ADD)
            .index(indexName)
            .alias(aliasIndexName);
        createAliasRequest.addAliasAction(createAliasActions);

        return createAliasRequest;
    }

    public static IndicesAliasesRequest getRemoveAliasRequest(
        String beforeIndexName,
        String aliasIndexName
    ) {
        IndicesAliasesRequest removeAliasRequest = new IndicesAliasesRequest();
        AliasActions removeAliasActions = new AliasActions(Type.REMOVE)
            .index(beforeIndexName)
            .alias(aliasIndexName);
        removeAliasRequest.addAliasAction(removeAliasActions);

        return removeAliasRequest;
    }

}