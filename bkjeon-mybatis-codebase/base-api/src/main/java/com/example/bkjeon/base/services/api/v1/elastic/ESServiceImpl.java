package com.example.bkjeon.base.services.api.v1.elastic;

import java.io.IOException;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import com.example.bkjeon.base.config.elastic.ESSearchConfig;
import com.example.bkjeon.util.elastic.ESRequestUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ESServiceImpl implements ESService {

    private final ESSearchConfig esSearchConfig;

    public long getDocTotalCnt() {
        long totalCnt = 0;
        CountRequest countRequest = ESRequestUtil.getTotalCntSearchRequest("indexName");

        try {
            CountResponse countResponse = esSearchConfig
                .restHighLevelClient()
                .count(countRequest, RequestOptions.DEFAULT);
            totalCnt = countResponse.getCount();
        } catch (Exception e) {
            log.error("getDocTotalCnt Error: {}", e);
        }

        return totalCnt;
    }

    /**
     * 인덱스 생성
     * @param indexName 생성할 인덱스명
     * @param settingShardCnt 샤드수
     * @param settingReplicaCnt 레플리카수
     * @param builder queryDSL
     * @return boolean
     */
    @Override
    public boolean createIndex(
        String indexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    ) {
        boolean acknowledged = false;

        try {
            // 신규 인덱스 생성
            CreateIndexRequest createIndexRequest = ESRequestUtil.getCreateIndexRequest(
                indexName,
                settingShardCnt,
                settingReplicaCnt,
                builder
            );
            CreateIndexResponse createIndexResponse = esSearchConfig.restHighLevelClient().indices().create(
                createIndexRequest,
                RequestOptions.DEFAULT
            );
            acknowledged = createIndexResponse.isAcknowledged();

            log.info("===================================================");
            log.info("index create acknowledged: {}", acknowledged);
            log.info("Create Index Name: {}", indexName);
            log.info("===================================================");
        } catch (IOException e) {
            log.error("createIndex Error !!: {}", e);
        }

        return acknowledged;
    }

    /**
     * 인덱스 생성(별칭포함)
     * @param indexName 생성할 인덱스명
     * @param settingShardCnt 샤드수
     * @param settingReplicaCnt 레플리카수
     * @param builder queryDSL
     * @return boolean
     */
    @Override
    public boolean createAliasIndex(
        String indexName,
        String aliasIndexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder
    ) {
        boolean acknowledged = false;

        try {
            // 신규 인덱스 생성
            CreateIndexRequest createIndexRequest = ESRequestUtil.getCreateIndexAliasRequest(
                indexName,
                settingShardCnt,
                settingReplicaCnt,
                builder,
                aliasIndexName
            );
            CreateIndexResponse createIndexResponse = esSearchConfig.restHighLevelClient().indices().create(
                createIndexRequest,
                RequestOptions.DEFAULT
            );
            acknowledged = createIndexResponse.isAcknowledged();

            log.info("===================================================");
            log.info("index create (Alias Set) acknowledged: {}", acknowledged);
            log.info("Create Index Name (Alias Set): {}", indexName);
            log.info("===================================================");
        } catch (IOException e) {
            log.error("createIndex Error !!: {}", e);
        }

        return acknowledged;
    }

    /**
     * 인덱스 별칭 설정
     * @param indexName 설정할 인덱스명
     * @param aliasIndexName 별칭
     * @return boolean
     */
    @Override
    public boolean setIndexAlias(
        String indexName,
        String aliasIndexName
    ) {
        boolean result = true;

        try {
            IndicesAliasesRequest createAliasRequest = ESRequestUtil.getCreateAliasRequest(
                indexName,
                aliasIndexName
            );
            AcknowledgedResponse createAliasResponse = esSearchConfig.restHighLevelClient()
                .indices()
                .updateAliases(createAliasRequest, RequestOptions.DEFAULT);

            log.info("===================================================");
            log.info("Set Alias Index Name Response: {}", createAliasResponse.isAcknowledged());
            log.info("===================================================");
        } catch (IOException ioe) {
            log.error("setIndexAlias Error !!: {}", ioe);
            result = false;
        }

        return result;
    }

    /**
     * 신규 인덱스 생성 후 인덱스 별칭 교체
     * @param beforeIndexName 이전 인덱스
     * @param indexName 생성할 인덱스명
     * @param settingShardCnt 샤드수
     * @param settingReplicaCnt 레플리카수
     * @param indexName 인덱스명
     * @param aliasIndexName  별칭
     * @return boolean
     */
    @Override
    public boolean changeIndexAlias(
        String beforeIndexName,
        int settingShardCnt,
        int settingReplicaCnt,
        XContentBuilder builder,
        String indexName,
        String aliasIndexName
    ) {
        boolean result = true;

        try {
            // 신규 인덱스 생성
            CreateIndexRequest createIndexRequest = ESRequestUtil.getCreateIndexRequest(
                indexName,
                settingShardCnt,
                settingReplicaCnt,
                builder
            );
            CreateIndexResponse createIndexResponse = esSearchConfig
                .restHighLevelClient().indices().create(
                createIndexRequest,
                RequestOptions.DEFAULT
            );
            log.info("===================================================");
            log.info("index create acknowledged: {}", createIndexResponse.isAcknowledged());
            log.info("Create Index Name: {}", indexName);
            log.info("===================================================");

            // 기존 별칭 제거
            IndicesAliasesRequest removeAliasRequest = ESRequestUtil
                .getRemoveAliasRequest(
                beforeIndexName,
                aliasIndexName
            );
            AcknowledgedResponse removeAliasResponse = esSearchConfig
                .restHighLevelClient()
                .indices()
                .updateAliases(removeAliasRequest, RequestOptions.DEFAULT);

            // 신규 별칭 매핑
            IndicesAliasesRequest createAliasRequest = ESRequestUtil
                .getCreateAliasRequest(
                indexName,
                aliasIndexName
            );
            AcknowledgedResponse createAliasResponse = esSearchConfig.restHighLevelClient()
                .indices()
                .updateAliases(createAliasRequest, RequestOptions.DEFAULT);
            log.info("===================================================");
            log.info("Delete Alias Index Name Response: {}", removeAliasResponse.isAcknowledged());
            log.info("Add Alias Index Name Response: {}", createAliasResponse.isAcknowledged());
            log.info("===================================================");
        } catch (IOException ioe) {
            log.error("changeIndexAlias Error !!: {}", ioe);
            result = false;
        }

        return result;
    }

    /**
     * 인덱스 존재 유무 체크
     * @param indexName
     * @return boolean
     */
    @Override
    public boolean selectIndexCheck(String indexName) {
        boolean exists = false;
        GetIndexRequest indexCheckRequest = new GetIndexRequest(indexName);

        try {
            exists = esSearchConfig
                .restHighLevelClient()
                .indices()
                .exists(indexCheckRequest, RequestOptions.DEFAULT);
        } catch (IOException ioe) {
            log.error("selectIndexCheck Error !!: {}", ioe);
        }

        return exists;
    }

    /**
     * 인덱스 제거
     * @param indexName
     * @return
     */
    @Override
    public boolean deleteIndex(String indexName) {
        boolean result = true;

        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            AcknowledgedResponse response = esSearchConfig
                .restHighLevelClient()
                .indices()
                .delete(request, RequestOptions.DEFAULT);

            log.info("===================================================");
            log.info("Delete Index Name: {}", indexName);
            log.info("Delete Index Name Response: {}", response.isAcknowledged());
            log.info("===================================================");
        } catch (IOException ioe) {
            log.error("deleteIndex Error !!: {}", ioe);
            result = false;
        }

        return result;
    }

    /**
     * 단일 데이터 insert (향후 다량의 데이터를 저장해야하는 경우 BulkProcess 클래스를 사용하여 메소드 추가 필요)
     * @param indexName
     * @param dataString
     */
    @Override
    public void insertData(
        String indexName,
        String documentId,
        String dataString
    ) {
        BulkRequest bulkRequest = new BulkRequest();
        IndexRequest indexRequest = new IndexRequest(indexName);

        try {
            log.info("==================== insert Request indexName: {}", indexName);
            log.info("==================== insert Request Data: {}", dataString);

            indexRequest.source(dataString, XContentType.JSON);
            bulkRequest.add(indexRequest);
            esSearchConfig.restHighLevelClient().bulkAsync(
                bulkRequest,
                RequestOptions.DEFAULT,
                new ActionListener<>() {
                    @Override
                    public void onResponse(BulkResponse bulkResponse) {
                        log.info(
                            "==================== insert Request ResultType: {}",
                            bulkResponse.status()
                        );
                    }

                    @Override
                    public void onFailure(Exception e) {
                        log.error(
                            "==================== bulkResponse error: {}",
                            e
                        );
                    }
                }
            );
        } catch (Exception e) {
            log.error("insertData Exception Error !!: {}", e);
        }
    }

    @Override
    public void bulkInsertData() {

        // Ex: 클래스 생성필요
        /**
         * public class BulkData {
         *
         *     public enum Type {
         *         CREATE, UPDATE, DELETE
         *     }
         *
         *     private String indexName;
         *     private String typeName;
         *     private String id;
         *     private Map<String, Object> jsonMap;
         *     private Type actionType;
         * }
         */
    }

}
