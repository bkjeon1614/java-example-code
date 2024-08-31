package com.example.bkjeon.base.services.api.v1.actuator.service.gauge;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class QueueManager {

    private final AtomicLong count = new AtomicLong(0);

    // TEST 용
    public long getQueueSize() {
        return System.currentTimeMillis();
    }

    public long init() {
        return count.get();
    }

    public long getSize() {
        return count.get();
    }

    public long increment() {
        return count.incrementAndGet();
    }

    public long decrement() {
        return count.decrementAndGet();
    }

}
