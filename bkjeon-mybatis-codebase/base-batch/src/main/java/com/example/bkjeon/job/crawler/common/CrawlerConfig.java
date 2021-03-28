package com.example.bkjeon.job.crawler.common;

import org.openqa.selenium.chrome.ChromeOptions;

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

}
