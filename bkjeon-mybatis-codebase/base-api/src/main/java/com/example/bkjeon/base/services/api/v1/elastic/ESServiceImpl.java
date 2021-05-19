package com.example.bkjeon.base.services.api.v1.elastic;

import com.example.bkjeon.util.elastic.ESRequestUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ESServiceImpl implements ESService {

    /**
     * 인덱스 생성
     * @param client
     * @param indexName 생성할 인덱스명
     * @param settingShardCnt 샤드수
     * @param settingReplicaCnt 레플리카수
     * @param builder queryDSL
     * @return boolean
     */
    @Override
    public boolean createIndex(
        RestHighLevelClient client,
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
            CreateIndexResponse createIndexResponse = client.indices().create(
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
     * @param client
     * @param indexName 생성할 인덱스명
     * @param settingShardCnt 샤드수
     * @param settingReplicaCnt 레플리카수
     * @param builder queryDSL
     * @return boolean
     */
    @Override
    public boolean createAliasIndex(
        RestHighLevelClient client,
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
            CreateIndexResponse createIndexResponse = client.indices().create(
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
     * @param client
     * @param indexName 설정할 인덱스명
     * @param aliasIndexName 별칭
     * @return boolean
     */
    @Override
    public boolean setIndexAlias(
        RestHighLevelClient client,
        String indexName,
        String aliasIndexName
    ) {
        boolean result = true;

        try {
            IndicesAliasesRequest createAliasRequest = ESRequestUtil.getCreateAliasRequest(
                indexName,
                aliasIndexName
            );
            AcknowledgedResponse createAliasResponse = client
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
     * @param client
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
        RestHighLevelClient client,
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
            CreateIndexResponse createIndexResponse = client.indices().create(
                createIndexRequest,
                RequestOptions.DEFAULT
            );
            log.info("===================================================");
            log.info("index create acknowledged: {}", createIndexResponse.isAcknowledged());
            log.info("Create Index Name: {}", indexName);
            log.info("===================================================");

            // 기존 별칭 제거
            IndicesAliasesRequest removeAliasRequest = ESRequestUtil.getRemoveAliasRequest(
                beforeIndexName,
                aliasIndexName
            );
            AcknowledgedResponse removeAliasResponse = client
                .indices()
                .updateAliases(removeAliasRequest, RequestOptions.DEFAULT);

            // 신규 별칭 매핑
            IndicesAliasesRequest createAliasRequest = ESRequestUtil.getCreateAliasRequest(
                indexName,
                aliasIndexName
            );
            AcknowledgedResponse createAliasResponse = client
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
     * @param client
     * @param indexName
     * @return boolean
     */
    @Override
    public boolean selectIndexCheck(RestHighLevelClient client, String indexName) {
        boolean exists = false;
        GetIndexRequest indexCheckRequest = new GetIndexRequest(indexName);

        try {
            exists = client.indices().exists(indexCheckRequest, RequestOptions.DEFAULT);
        } catch (IOException ioe) {
            log.error("selectIndexCheck Error !!: {}", ioe);
        }

        return exists;
    }

    /**
     * 인덱스 제거
     * @param client
     * @param indexName
     * @return
     */
    @Override
    public boolean deleteIndex(RestHighLevelClient client, String indexName) {
        boolean result = true;

        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

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
     * @param client
     * @param indexName
     * @param dataString
     */
    @Override
    public void insertData(
        RestHighLevelClient client,
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
            client.bulkAsync(
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
