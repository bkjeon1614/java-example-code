package com.example.bkjeon.base.services.api.v1.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
public class AsyncService {

    @Async("threadPoolTaskExecutor")
    public Future<String> isFeatureReturn(String message) throws InterruptedException {
        log.info("============= Feature Task Start - {}", message);
        Thread.sleep(3000);
        return new AsyncResult<>("bkjeon-" + message);
    }

    @Async("threadPoolTaskExecutor")
    public ListenableFuture<String> isListenableFutureReturn(String message) throws InterruptedException {
        log.info("============= ListenableFuture Task Start - {}", message);
        Thread.sleep(3000);
        return new AsyncResult<>("bkjeon-" + message);
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> isCompletableFutureReturn(String message) throws InterruptedException {
        log.info("============= CompletableFuture Task Start - {}", message);
        Thread.sleep(3000);
        return new AsyncResult<>("bkjeon-" + message).completable();
    }

}
