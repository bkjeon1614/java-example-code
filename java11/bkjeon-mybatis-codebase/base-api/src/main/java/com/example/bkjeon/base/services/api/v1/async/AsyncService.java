package com.example.bkjeon.base.services.api.v1.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    public void isCompletableFutureReturn(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> orderInfoFuture = CompletableFuture
            .supplyAsync(() -> getAsyncTestInfo(message));
        log.info("============= CompletableFuture Return - {}", orderInfoFuture.get());
    }

    public void isCompletableFutureReturnThenApply(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> testInfoFuture = CompletableFuture
            .supplyAsync(() -> getAsyncTestInfo(message));
        CompletableFuture<String> testInfoFuture2 = testInfoFuture.thenApply(this::getAsyncTestInfo2);
        log.info("============= CompletableFuture Then Apply Return - {}", testInfoFuture2.get());  // 이전 연산 완료 후(2초 후) 다음 연산 처리
    }

    public void isCompletableFutureReturnThenAccept(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> testInfoFuture = CompletableFuture
            .supplyAsync(() -> getAsyncTestInfo(message));
        CompletableFuture<Void> thenAccept = testInfoFuture.thenAccept(this::voidTest);
        thenAccept.get();   // Completed: bkjeon async
    }

    public void isCompletableFutureReturnThenRun(String message) {
        CompletableFuture<String> testInfoFuture = CompletableFuture
            .supplyAsync(() -> getAsyncTestInfo(message));
        testInfoFuture.thenRun(() -> voidTest(message));    // Completed: test thenRun()
    }

    public void isCompletableFutureReturnThenCompose(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> testInfoFuture = CompletableFuture
            .supplyAsync(() -> getAsyncTestInfo(message)).thenCompose(s -> CompletableFuture.supplyAsync(() -> getAsyncTestInfo2(s)));
        log.info("============= CompletableFuture Then Compose Return - {}", testInfoFuture.get());
    }

    public void isCompletableFutureReturnThenCombine(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> testInfoFuture = CompletableFuture.supplyAsync(() -> getAsyncTestInfo(message))
            .thenCombine(CompletableFuture.supplyAsync(() -> getReturnStr("message1")), this::getCombineReturnStr);

        // ============= CompletableFuture Then Combine Return - bkjeon async. [옵션] Test!!!!
        log.info("============= CompletableFuture Then Combine Return - {}", testInfoFuture.get());
    }

    public void isCompletableFutureReturnThenAcceptBoth(String message) {
        // 결과: bkjeon async. [옵션] Test!!!!
        CompletableFuture.supplyAsync(() -> getAsyncTestInfo(message))
            .thenAcceptBoth(CompletableFuture.supplyAsync(() -> getReturnStr("message1")), this::getUpdateInfo);
    }

    private String getAsyncTestInfo(String message) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
            log.error("InterruptedException", ie);
        }

        return "bkjeon async";
    }

    private String getAsyncTestInfo2(String message) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
            log.error("InterruptedException", ie);
        }

        return "bkjeon async2";
    }

    private void voidTest(String message) {
        log.info("Completed: " + message);
    }

    private String getReturnStr(String message) {
        return "Test!!!!";
    }

    private String getCombineReturnStr(String message1, String message2) {
        return message1 + ". [옵션] " + message2;
    }

    private void getUpdateInfo(String message1, String message2) {
        log.info(message1 + ". [옵션] " + message2);
    }

}
