package com.example.bkjeon.enums.elastic;

import lombok.Getter;

/**
 * 인덱스 생성 타입
 * INDEX_ALIAS_CHANGE: 일반 인덱스 생성 후 별칭 교체
 * INDEX_ALIAS_CREATE: 별칭 적용된 인덱스 생성
 */
@Getter
public enum CreateIndexType {

    INDEX_ALIAS_CHANGE("change"),
    INDEX_ALIAS_CREATE("create");

    private String type;

    CreateIndexType(String type){
        this.type = type;
    }

}
