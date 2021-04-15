package com.example.bkjeon.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadUtil {

    private ThreadUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void threadSleep(int minSecond, int maxSecond) throws InterruptedException {
        int randomNum = ((int)(Math.random() * (maxSecond - minSecond + 1)) + minSecond) * 1000;
        Thread.sleep(randomNum);
    }

}
