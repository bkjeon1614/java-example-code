package com.example.bkjeon.base.api.v1.service.example;

import com.example.bkjeon.base.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalService {

    public String getKakaoSampleData() {
        String result = null;
        try {
            String requestUrl = String.format("https://gift.kakao.com/a/v1/best/delivery/%d?page=%d&size=%d", 2, 0, 20);
            result = HttpUtil.getHttpResponse(requestUrl);
            log.debug("responseJson: {}", result);
        } catch (Exception e) {
            log.error("getOliveOneSampleData Error: {}", e);
        }

        return result;
    }

}