package com.example.bkjeon.base.filter;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggingFilter extends OncePerRequestFilter {

	private final MultipartResolver multipartResolver;

	@Override
	protected void doFilterInternal(
		@Nonnull HttpServletRequest request,
		@Nonnull HttpServletResponse response,
		@Nonnull FilterChain filterChain
	) throws ServletException, IOException {
		MDC.put("method", request.getMethod());
		MDC.put("uri", request.getQueryString() == null
			? request.getRequestURI()
			: request.getRequestURI() + "?" + request.getQueryString());

		if (multipartResolver.isMultipart(request)) {
			try {
				MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);
				doFilterWrapped(new RequestWrapper(multipartRequest), new ResponseWrapper(response), filterChain);
			} catch (MultipartException e) {
				throw new ServletException("Multipart request processing failed", e);
			}
		} else {
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