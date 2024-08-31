package com.example.bkjeon.enums.crawler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CrawlingRequestType {
    NAVER("N");

    private String CrawlingRequestTypeCode;
}
