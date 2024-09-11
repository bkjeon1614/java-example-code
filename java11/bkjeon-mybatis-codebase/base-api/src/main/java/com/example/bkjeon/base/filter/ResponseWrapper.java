package com.example.bkjeon.base.filter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * HTTP 응답 캐싱
 * [ContentCachingResponseWrapper]
 * httpServletRequest의 getInputStream()은 한번 밖에 사용 못하므로 ContentCachingResponseWrapper 를 사용해야함
 * ContentCachingResponseWrapper 는 출력 스트림 및 기록된 모든 콘텐츠를 캐시하고 바이트 배열을 통해 이 콘텐츠를 검색할 수 있도록 하는
 * HttpServletResponse Wrapper 이다.
 */
public class ResponseWrapper extends ContentCachingResponseWrapper {
	public ResponseWrapper(HttpServletResponse response) {
		super(response);
	}
}