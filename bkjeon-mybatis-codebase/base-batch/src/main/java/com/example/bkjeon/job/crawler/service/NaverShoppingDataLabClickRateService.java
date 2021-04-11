package com.example.bkjeon.job.crawler.service;

import com.example.bkjeon.enums.crawler.CrawlingRequestType;
import com.example.bkjeon.util.DateUtil;
import com.example.bkjeon.feature.crawler.NaverShoppingDataLabClickRate;
import com.example.bkjeon.feature.crawler.NaverShoppingDataLabClickRateMapper;
import com.example.bkjeon.job.crawler.common.CrawlerConfig;
import com.example.bkjeon.job.crawler.common.CrawlerConstant;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverShoppingDataLabClickRateService {

    private final NaverShoppingDataLabClickRateMapper naverShoppingDataLabClickRateMapper;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public List<NaverShoppingDataLabClickRate> getNaverShoppingDataLabClickRateCrawling(
        String logYmd,
        String startDate,
        String endDate,
        String categoryId
    ) {
        List<NaverShoppingDataLabClickRate> naverDataLabClickRateList = new ArrayList<>();

        try {
            MultiValueMap<String, String> body = createRequestBody(startDate, endDate, categoryId);
            HttpEntity requestEntity = new HttpEntity(
                body,
                CrawlerConfig.createRequestHeaders(CrawlingRequestType.NAVER.getCrawlingRequestTypeCode())
            );

            ResponseEntity<String> response = restTemplate().postForEntity(
                CrawlerConstant.naverShoppingDataLabClickRateUrl,
                requestEntity,
                String.class
            );

            JsonElement rootNode = JsonParser.parseString(response.getBody());
            if (rootNode.isJsonObject()) {
                JsonObject jsonObject = rootNode.getAsJsonObject();
                JsonElement data = jsonObject.get("result")
                        .getAsJsonArray()
                        .get(0)
                        .getAsJsonObject()
                        .get("data");
                JsonArray jsonArray = data.getAsJsonArray();
                for (JsonElement object : jsonArray) {
                    NaverShoppingDataLabClickRate naverShoppingDataLabClickRate = NaverShoppingDataLabClickRate
                        .builder()
                        .logYmd(logYmd)
                        .categoryId(categoryId)
                        .period(object.getAsJsonObject().get("period").getAsString())
                        .clickRate(object.getAsJsonObject().get("value").getAsString())
                        .build();
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>> {}", naverShoppingDataLabClickRate.toString());
                    naverDataLabClickRateList.add(naverShoppingDataLabClickRate);
                }
            }
        } catch (Exception e) {
            log.error("네이버 데이터랩 클릭 카운팅 수집 크롤링 에러 {}", e);
        }

        return naverDataLabClickRateList;
    }

    private MultiValueMap<String, String> createRequestBody(String startYmd, String endYmd, String... cid) throws Exception {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("cid", StringUtils.join(cid, ","));
        body.add("timeUnit", "month");
        body.add("startDate", DateUtil.convertFormat(startYmd, "yyyyMMdd", "yyyy-MM-dd"));
        body.add("endDate", DateUtil.convertFormat(endYmd, "yyyyMMdd", "yyyy-MM-dd"));
        return body;
    }

    @Transactional
    public int delNaverShoppingDataLabClickRate(String logYmd, String categoryIds) {
        int delCnt = 0;

        try {
            delCnt = naverShoppingDataLabClickRateMapper.deleteNaverShoppingDataLabClickRate(logYmd, categoryIds);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>> delNaverShoppingDataLabClickRate Error !! {}", e);
        }

        return delCnt;
    }

    @Transactional
    public int setNaverShoppingDataLabClickRate(List<NaverShoppingDataLabClickRate> naverShoppingDataLabClickRateList) {
        int insertCnt = 0;

        try {
            insertCnt = naverShoppingDataLabClickRateMapper.insertNaverShoppingDataLabClickRateList(naverShoppingDataLabClickRateList);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>> delNaverShoppingDataLabClickRate Error !! {}", e);
        }

        return insertCnt;
    }

}
