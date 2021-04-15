package com.example.bkjeon.job.crawler.common;

import com.example.bkjeon.util.RandomUserAgent;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Random;

public class CrawlerConfig {

    public static ChromeOptions getDefaultChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("--disable-gpu");
        options.addArguments("no-sandbox");
        options.addArguments("disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-agent=" + RandomUserAgent.getRandomUserAgent());
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        System.setProperty("https.proxyHost", getRandomIpAddress());
        return options;
    }

    public static String getRandomIpAddress() {
        Random r = new Random();
        return r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256) + "." + r.nextInt(256);
    }

}
