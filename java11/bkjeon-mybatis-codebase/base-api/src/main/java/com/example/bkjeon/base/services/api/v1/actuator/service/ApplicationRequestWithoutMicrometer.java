package com.example.bkjeon.base.services.api.v1.actuator.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ApplicationRequestWithoutMicrometer {

    private AtomicLong count = new AtomicLong(0);

    public long getCount() {
        return count.get();
    }

    public void increase() {
        count.incrementAndGet();
    }

}
