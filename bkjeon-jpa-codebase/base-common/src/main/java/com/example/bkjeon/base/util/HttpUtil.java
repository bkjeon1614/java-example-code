package com.example.bkjeon.base.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class HttpUtil {

    private HttpUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getHttpResponse(String requestUrl) {
        String jsonResponse = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
            headers.setContentType(MediaType.APPLICATION_JSON);
            // headers.set("X-API-KEY", apiKey);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> respEntity = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                new HttpEntity<>("", headers),
                String.class
            );

            ObjectMapper objectMapper = new ObjectMapper();
            jsonResponse = objectMapper.readTree(respEntity.getBody()).toString();
        } catch (Exception e) {
            log.error("getHttpResponse Error: {}", e);
        }

        return jsonResponse;
    }

    public static ResponseEntity<String> postHttpResponse(
        MultiValueMap<String, String> body,
        String requestUrl
    ) {
        ResponseEntity<String> responseEntity = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", RandomUserAgent.getRandomUserAgent());
            HttpEntity requestEntity = new HttpEntity(body, headers);

            RestTemplate restTemplate = new RestTemplate();
            responseEntity = restTemplate.postForEntity(
                requestUrl,
                requestEntity,
                String.class
            );
        } catch (Exception e) {
            log.error("requestPostEntity Error: {}", e);
        }

        return responseEntity;
    }

}
