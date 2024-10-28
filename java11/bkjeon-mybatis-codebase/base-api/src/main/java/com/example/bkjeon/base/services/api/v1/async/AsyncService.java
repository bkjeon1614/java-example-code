package com.example.bkjeon.base.services.api.v1.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
        CompletableFuture<String> testInfoFuture = CompletableFuture
            .supplyAsync(() -> getAsyncTestInfo(message));
        log.info("============= CompletableFuture Return - {}", testInfoFuture.get());
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

    public void isCompletableFutureReturnAllOf(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> testInfoFuture = CompletableFuture.supplyAsync(() -> {
            log.info("future1: " + Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-5
            return getAsyncTestInfo("message1");
        });

        CompletableFuture<String> testInfoFuture2 = CompletableFuture.supplyAsync(() -> {
            log.info("future1: " + Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-9
            return getReturnStr("message2");
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> "testInfoFuture2"));

        CompletableFuture<String> testInfoFuture3 = CompletableFuture.supplyAsync(() -> {
            log.info("future1: " + Thread.currentThread().getName()); // ForkJoinPool.commonPool-worker-19
            return getReturnStr2("message3");
        }).thenCompose(s -> CompletableFuture.supplyAsync(() -> "testInfoFuture3"));;

        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(testInfoFuture, testInfoFuture2, testInfoFuture3);
        combinedFuture.get();

        if (testInfoFuture.isDone() && testInfoFuture2.isDone() && testInfoFuture3.isDone()) {
            String combined = Stream.of(testInfoFuture, testInfoFuture2, testInfoFuture3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
            log.info(combined);
        }
    }

    public void isCompletableFutureReturnHandle(String message) throws ExecutionException, InterruptedException {
        CompletableFuture<String> testInfoFuture = CompletableFuture.supplyAsync(() -> {
            if (message == null) {
                throw new IllegalArgumentException("The message must not be null!");
            }
            return getAsyncTestInfo(message);
        }).handle((s, t) -> { // s: Future 실행 완료 후 결괏값, t: Future 실행 중 발생한 예외
            /**
             * 연산이 성공적으로 완료된 경우
             * s = bkjeon async
             * t = null
             *
             * 연산이 정상적으로 완료되지 않고 예외가 발생한 경우
             * s = null
             * t = java.util.concurrent.CompletionException: java.lang.IllegalArgumentException: The message must not
             * be null!
             */
            return t == null ? s : "Default value";
        });

        log.info("============= CompletableFuture Handle Return - {}", testInfoFuture.get());
    }

    public void isCompletableFutureReturnCompleteExceptionally(String message) {
        String testMessage = null;
        CompletableFuture<String> testInfoFuture = new CompletableFuture<>();
        if (testMessage == null) {
            testInfoFuture.completeExceptionally(new IllegalArgumentException("The testMessage must not be null!"));
        }

        if (testMessage != null) {
            testInfoFuture.complete(getAsyncTestInfo(testMessage));
        }

        try {
            testInfoFuture.get();
        } catch (ExecutionException | InterruptedException ee) {
            Throwable cause = ee.getCause();
            log.error("Exception occurred: {}", cause.getMessage());
        }
    }

    public void isCompletableFutureReturnCompleteTimeoutGet(String message) {
        CompletableFuture<String> testInfoFuture = CompletableFuture.supplyAsync(() -> getAsyncTestInfo("TEST"));

        try {
            testInfoFuture.get(2000, TimeUnit.MILLISECONDS);
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            log.error("Timeout Exception: " + e);
        }
    }

    public void isCompletableFutureReturnCompleteOrTimeout(String message) {
        CompletableFuture<String> testInfoFuture = CompletableFuture.supplyAsync(() -> getAsyncTestInfo("TEST"))
            .orTimeout(2, TimeUnit.SECONDS);

        try {
            testInfoFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Timeout Exception: " + e);
        }
    }

    public void isCompletableFutureReturnCompleteCompleteOnTimeout(String message) {
        CompletableFuture<String> testInfoFuture = CompletableFuture.supplyAsync(() -> getAsyncTestInfo("TEST"))
            .completeOnTimeout("default value", 2, TimeUnit.SECONDS);
        String result;

        try {
            result = testInfoFuture.get();
            log.info(">>>>>>>>>>>>>>>>> result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            log.error("Timeout Exception: " + e);
        }
    }

    private String getAsyncTestInfo(String message) {
        try {
            Thread.sleep(4000);
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

    private String getReturnStr2(String message) {
        return "Test2222!!!!";
    }

    private String getCombineReturnStr(String message1, String message2) {
        return message1 + ". [옵션] " + message2;
    }

    private void getUpdateInfo(String message1, String message2) {
        log.info(message1 + ". [옵션] " + message2);
    }

}
