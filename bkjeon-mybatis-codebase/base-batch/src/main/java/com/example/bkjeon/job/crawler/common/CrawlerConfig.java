package com.example.bkjeon.job.crawler.common;

import com.example.bkjeon.enums.crawler.CrawlingRequestType;
import com.example.bkjeon.util.RandomUserAgent;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.http.HttpHeaders;

public class CrawlerConfig {

    public static ChromeOptions getDefaultChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("--disable-gpu");
        options.addArguments("no-sandbox");
        options.addArguments("disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        return options;
    }

    public static HttpHeaders createRequestHeaders(String type) {
        HttpHeaders headers = new HttpHeaders();

        if (CrawlingRequestType.NAVER.getCrawlingRequestTypeCode().equals(type)) {
            headers.add("Referer", "https://datalab.naver.com");
            headers.add("User-Agent", RandomUserAgent.getRandomUserAgent());
        }

        return headers;
    }

}
