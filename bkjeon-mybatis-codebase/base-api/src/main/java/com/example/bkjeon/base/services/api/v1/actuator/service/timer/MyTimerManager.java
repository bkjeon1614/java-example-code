package com.example.bkjeon.base.services.api.v1.actuator.service.timer;

import org.springframework.stereotype.Service;

@Service
public class MyTimerManager {

    public long getCount() {
        return System.currentTimeMillis();
    }

    public long getTotalTime() {
        return System.currentTimeMillis() * 2;
    }

}
