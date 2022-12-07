package com.example.bkjeon.base.filter;

import static javax.ws.rs.core.MediaType.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 로깅 필터
 * [MDC]
 * 실행 쓰레드들에 공통값을 주입하여 의미있는 정보를 추가해 로깅 할 수 있도록 제공
 * ( Ex: 멀티 스레딩 환경시 실행되는 task 는 로그가 섞여 제대로 확인하기 힘들어서
 * 스레드로컬 변수에 값을 할당하여 트래킹에 용이하게 만드나 매번 해당 값을 주입하기는 번거로워 logback, log4j 등 MDC 를 제공)
 *
 * [doFilterInternal]
 * doFilter 와 동일하지만 단일 요청 스레드 내에서 요청당 한 번만 호출되도록 보장된다.
 */
@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		@Nullable HttpServletResponse response,
		@Nullable FilterChain filterChain
	) throws ServletException, IOException {
		MDC.put("method", request.getMethod());
		MDC.put("uri", request.getQueryString() == null
			? request.getRequestURI()
			: request.getRequestURI() + "?" + request.getQueryString());

		if (filterChain != null) {
			doFilterWrapped(new RequestWrapper(request), new ResponseWrapper(response), filterChain);
		}

		MDC.clear();
	}

	protected void doFilterWrapped(
		RequestWrapper request,
		ContentCachingResponseWrapper response,
		FilterChain filterChain
	) throws ServletException, IOException {
		try {
			logRequest(request);
			filterChain.doFilter(request, response);
		} finally {
			response.copyBodyToResponse();
		}
	}

	private static void logRequest(RequestWrapper request) throws IOException {
		boolean mediaTypeChk = isMediaType(MediaType.valueOf(request.getContentType() == null
			? APPLICATION_JSON
			: request.getContentType()));

		// inputStream 을 byte 배열로 반환
		byte[] content = StreamUtils.copyToByteArray(request.getInputStream());
		if (mediaTypeChk && content.length > 0) {
			MDC.put("payload", new String(content));
		}
	}

	private static boolean isMediaType(MediaType mediaType) {
		final List<MediaType> mediaTypeList = Arrays.asList(
			MediaType.valueOf("text/*"),
			MediaType.APPLICATION_FORM_URLENCODED,
			MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML,
			MediaType.valueOf("application/*+json"),
			MediaType.valueOf("application/*+xml"),
			MediaType.MULTIPART_FORM_DATA
		);

		return mediaTypeList.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
	}

}