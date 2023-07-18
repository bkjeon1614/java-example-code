package com.example.bkjeon.base.services.api.v1.http;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UrlPathHelper;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("v1/http")
public class HttpController {

	@ApiOperation("URL Create (쿼리 스트링 포함)")
	@GetMapping("isCreateUrl")
	public String getCreateUrl(HttpServletRequest req) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String[] foUrlArr = "https://test.bkjeon.com".split(":");
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			.scheme(foUrlArr[0])
			.host(foUrlArr[1].replaceAll("/", ""))
			.path(urlPathHelper.getOriginatingRequestUri(req))
			.queryParam("p1", "test1")
			.queryParam("p2", "test2")
			.build(true);
		return uriComponents.toUriString();
	}

}
