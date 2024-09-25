package com.example.bkjeon.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpUtil {

    private HttpUtil() {
        throw new IllegalStateException("Utility class");
    }

    // Object(=DTO) To Map
    public static MultiValueMap<String, String> dtoToMapConverter(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        try {
            Map<String, String> map = objectMapper.convertValue(object, new TypeReference<>() {});
            params.setAll(map);
        } catch (Exception e) {
            log.error("Url Parameter Error. requestDto={}", object, e);
        }

        return params;
    }

    public static String requestUrl(HttpMethodBase method) {
        try {
            HttpClientParams httpParams = new HttpClientParams();
            httpParams.setConnectionManagerClass(SimpleHttpConnectionManager.class);
            HttpClient client = new HttpClient(httpParams);
            try {
                int code = client.executeMethod(method);
                String response = IOUtils.toString(method.getResponseBodyAsStream(), "UTF-8");
                if (code != 200) {
                    throw new Exception("Unexpected result: " + code + " " + response);
                }
                return response;
            } catch (Exception e) {
            } finally {
                if (method != null) {
                    method.releaseConnection();
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String requestForm(String url, List<NameValuePair> params) {
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
            httpPost.setEntity(formEntity);
            httpPost.setHeader("User-Agent", RandomUserAgent.getRandomUserAgent());
            httpPost.setHeader("Host", "bkjeon1614.tistory.com");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
            org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getCleanData(String str) {
        if (str == null ) {
            return null;
        } else if (str.startsWith("\uFEFF")) {  //BOM remove
            str = str.substring(1);
        }

        Pattern emoticons = Pattern.compile("[\\uD83c-\\uDBFF\\uDC00-\\uDFFF]+");
        Matcher emoticonsMatcher = emoticons.matcher(str);
        str = emoticonsMatcher.replaceAll("");

        return str;
    }

    public static ResponseEntity<String> getRequestEntity(
        MultiValueMap<String, String> body,
        String requestUrl,
        String referer
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Referer", referer);
        headers.add("User-Agent", RandomUserAgent.getRandomUserAgent());
        HttpEntity requestEntity = new HttpEntity(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
            requestUrl,
            requestEntity,
            String.class
        );

        return responseEntity;
    }

}
