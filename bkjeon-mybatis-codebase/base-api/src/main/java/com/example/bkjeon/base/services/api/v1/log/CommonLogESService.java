package com.example.bkjeon.base.services.api.v1.log;

import com.example.bkjeon.base.config.elastic.ESSearchConfig;
import com.example.bkjeon.base.services.api.v1.elastic.ESService;
import com.example.bkjeon.dto.common.log.CommonESLogSaveRequestDTO;
import com.example.bkjeon.entity.common.log.CommonESLog;
import com.example.bkjeon.enums.elastic.CreateIndexType;
import com.example.bkjeon.enums.elastic.ESFieldType;
import com.example.bkjeon.util.security.EncryptUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonLogESService {

    private final static String INDEX_NAME_PREFIX = "service-common-log";
    private final static Integer SETTING_SHARD_CNT = 1;
    private final static Integer SETTING_REPLICA_CNT = 1;

    private final ESSearchConfig esSearchConfig;
    private final ESService esService;

    /**
     * 로그 인덱스(-1 year) 유무 체크 (origin 인덱스로 체크)
     * @return
     */
    public boolean selectBeforeIndexCheck() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Integer nowDateY = Integer.parseInt(dateFormat.format(new Date()));

        String beforeIndexName = INDEX_NAME_PREFIX + "-" + (nowDateY - 1);

        return esService.selectIndexCheck(
            esSearchConfig.restHighLevelClient(),
            beforeIndexName
        );
    }

    /**
     * 로그 인덱스 유무 체크 (origin 인덱스로 체크)
     * @return
     */
    public boolean selectOriginIndexCheck() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Integer nowDateY = Integer.parseInt(dateFormat.format(new Date()));
        String nowIndexName = INDEX_NAME_PREFIX + "-" + nowDateY;

        return esService.selectIndexCheck(
            esSearchConfig.restHighLevelClient(),
            nowIndexName
        );
    }

    /**
     * 로그 인덱스 생성
     * @Param IndexCreateType (Enum: CreateIndexType.java 참고)
     * @return
     */
    public boolean createCommonLogIndex(String createIndexType) {
        boolean result = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Integer nowDateY = Integer.parseInt(dateFormat.format(new Date()));

        String nowIndexName = INDEX_NAME_PREFIX + "-" + nowDateY;
        String beforeIndexName = INDEX_NAME_PREFIX + "-" + (nowDateY - 1);

        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
                .field("dynamic", "strict")
                .startObject("properties")
                .startObject("log_id")
                .field("type", ESFieldType.KEYWORD.getType())
                .endObject()
                .startObject("log_type")
                .field("type", ESFieldType.KEYWORD.getType())
                .endObject()
                .startObject("call_url")
                .field("type", ESFieldType.TEXT.getType())
                .endObject()
                .startObject("call_mthd_sp_val")
                .field("type", ESFieldType.KEYWORD.getType())
                .endObject()
                .startObject("call_para_val")
                .field("type", ESFieldType.TEXT.getType())
                .endObject()
                .startObject("svc_call_nm")
                .field("type", ESFieldType.TEXT.getType())
                .endObject()
                .startObject("svc_mthd_nm")
                .field("type", ESFieldType.TEXT.getType())
                .endObject()
                .startObject("exec_tme")
                .field("type", ESFieldType.LONG.getType())
                .endObject()
                .startObject("log_desc")
                .field("type", ESFieldType.TEXT.getType())
                .endObject()
                .startObject("login_id")
                .field("type", ESFieldType.KEYWORD.getType())
                .endObject()
                .startObject("sys_reg_date_time")
                .field("type", ESFieldType.TEXT.getType())
                .endObject()
                .endObject()
                .endObject();

            if (CreateIndexType.INDEX_ALIAS_CHANGE.getType().equals(createIndexType)) {
                // 일반 인덱스 생성 후 별칭 교체
                result = esService.changeIndexAlias(
                    esSearchConfig.restHighLevelClient(),
                    beforeIndexName,
                    SETTING_SHARD_CNT,
                    SETTING_REPLICA_CNT,
                    builder,
                    nowIndexName,
                    INDEX_NAME_PREFIX
                );
            } else if (CreateIndexType.INDEX_ALIAS_CREATE.getType().equals(createIndexType)) {
                // 별칭 인덱스 생성
                result = esService.createAliasIndex(
                    esSearchConfig.restHighLevelClient(),
                    nowIndexName,
                    INDEX_NAME_PREFIX,
                    SETTING_SHARD_CNT,
                    SETTING_REPLICA_CNT,
                    builder
                );
            }

        } catch (Exception e) {
            log.error("createCommonLogIndex Error !!: {}", e);
            result = false;
        }

        return result;
    }

    /**
     * 로그 데이터 저장
     * @param commonESLogSaveRequestDTO
     */
    public void insertCommonLogData(CommonESLogSaveRequestDTO commonESLogSaveRequestDTO) {
        long newTime = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.KOREAN);
        String nowDateTime = dateFormat.format(new Date(newTime));

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // id(=pk) 가공
            String combinationId = commonESLogSaveRequestDTO.getLogType()
                + commonESLogSaveRequestDTO.getSvcCallNm()
                + commonESLogSaveRequestDTO.getLoginId()
                + nowDateTime;
            String encodingId = EncryptUtil.getStringToSHA256Encrypt(combinationId);

            CommonESLog commonLog = CommonESLog.builder()
                .logId(encodingId)
                .logType(commonESLogSaveRequestDTO.getLogType())
                .callUrl(commonESLogSaveRequestDTO.getCallUrl())
                .callMthdSpVal(commonESLogSaveRequestDTO.getCallMthdSpVal())
                .callParaVal(commonESLogSaveRequestDTO.getCallParaVal())
                .svcCallNm(commonESLogSaveRequestDTO.getSvcCallNm())
                .svcMthdNm(commonESLogSaveRequestDTO.getSvcMthdNm())
                .execTme(commonESLogSaveRequestDTO.getExecTme())
                .logDesc(commonESLogSaveRequestDTO.getLogDesc())
                .loginId(commonESLogSaveRequestDTO.getLoginId())
                .sysRegDateTime(nowDateTime)
                .build();

            esService.insertData(
                esSearchConfig.restHighLevelClient(),
                INDEX_NAME_PREFIX,
                combinationId,
                objectMapper.writeValueAsString(commonLog)
            );
        } catch (JsonProcessingException jpe) {
            log.error("insertData JsonProcessingException Error !!: {}", jpe);
        } catch (Exception e) {
            log.error("insertCommonLogData Exception Error !!: {}", e);
        }
    }

}