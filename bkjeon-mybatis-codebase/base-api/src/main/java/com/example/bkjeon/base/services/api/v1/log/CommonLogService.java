package com.example.bkjeon.base.services.api.v1.log;

import com.example.bkjeon.dto.common.log.CommonESLogSaveRequestDTO;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.enums.elastic.CreateIndexType;
import com.example.bkjeon.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonLogService {

    private final CommonLogESService commonLogESService;

    /**
     * 로그 저장(ElasticSearch)
     * @param commonESLogSaveRequestDTO
     * @return
     */
    public ResponseEntity setESLog(CommonESLogSaveRequestDTO commonESLogSaveRequestDTO) {
        ResponseEntity responseEntity;

        try {
            // 인덱스 존재 유무 체크(origin)
            if (!commonLogESService.selectOriginIndexCheck()) {
                // 조건1: 기존 인덱스가 존재 할 때 일반 인덱스 생성 후 별칭 교체
                // 조건2: 기존 인덱스가 존재 안할 때 별칭 인덱스 생성
                String createIndexType = commonLogESService.selectBeforeIndexCheck()
                    ? CreateIndexType.INDEX_ALIAS_CHANGE.getType()
                    : CreateIndexType.INDEX_ALIAS_CREATE.getType();
                commonLogESService.createCommonLogIndex(createIndexType);
            }

            // 로그 데이터 저장
            commonLogESService.insertCommonLogData(commonESLogSaveRequestDTO);
            responseEntity = new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.OK.value(),
                    ResponseResult.SUCCESS.getText(),
                    commonESLogSaveRequestDTO,
                    null
                ),
                HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("setLog Error !!: {}", e);
            responseEntity = new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    commonESLogSaveRequestDTO,
                    null
                ),
                HttpStatus.BAD_REQUEST
            );
        }

        return responseEntity;
    }

}