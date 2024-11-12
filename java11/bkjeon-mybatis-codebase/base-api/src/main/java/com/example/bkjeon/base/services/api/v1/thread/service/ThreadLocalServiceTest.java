package com.example.bkjeon.base.services.api.v1.thread.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ThreadLocalServiceTest {

    private String nameStore;

    // 매개변수를 통해 넘어온 name을 전역 변수인 nameStore에 저장 후, 1초 뒤 조회 로그를 찍어주는 메소드
    public String logic(String name) {
        log.info("저장 name={} -> nameStore={}", name, nameStore);
        nameStore = name;
        sleep(1000);
        log.info("조회 nameStore={}",nameStore);
        return nameStore;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
