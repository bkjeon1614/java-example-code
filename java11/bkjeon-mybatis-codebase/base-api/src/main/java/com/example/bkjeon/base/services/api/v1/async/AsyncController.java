package com.example.bkjeon.base.services.api.v1.async;

import io.swagger.annotations.ApiOperation;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/async")
public class AsyncController {

    private final AsyncService asyncService;

    @ApiOperation("@Async 테스트")
    @GetMapping("/future")
    public void isAsyncFuture() throws InterruptedException, ExecutionException {
        /**
         * future.get() 은 블로킹을 통해 요청 결과가 올 때까지 기다리는 역할을 한다.
         * 그러므로 비동기 블로킹 방식이 되어버려 성능이 좋지 않다. 보통 Future 는 사용하지 않는다.
         * [결과]
         * ============= Feature Task Start - 1
         * ============= isAsyncAnnotationReturn : bkjeon-1
         * ============= Feature Task Start - 2
         * ============= isAsyncAnnotationReturn : bkjeon-2
         * ============= Feature Task Start - 3
         * ============= isAsyncAnnotationReturn : bkjeon-3
         * ============= Feature Task Start - 4
         * ============= isAsyncAnnotationReturn : bkjeon-4
         * ============= Feature Task Start - 5
         * ============= isAsyncAnnotationReturn : bkjeon-5
         */
        for (int i = 1; i <= 5; i++) {
            Future<String> future = asyncService.isFeatureReturn(i + "");
            log.info("============= isAsyncAnnotationReturn : {}", future.get());
        }
    }

    @ApiOperation("@Async 테스트")
    @GetMapping("/listenableFuture")
    public void isAsyncListenableFuture() throws InterruptedException, ExecutionException {
        /**
         * ListenableFuture 은 콜백을 통해 논블로킹 방식으로 작업을 처리할 수 있다.
         * addCallback() 메소드의 첫 번째 파라미터는 작업 완료 콜백 메소드, 두 번째 파라미터는 작업 실패 콜백 메소드를 정의하면 된다.
         * 스레드 풀의 core 스레드를 3개로 설정했으므로 "Task Start" 메시지가 처음에 3개 찍힘
         * [결과]
         * ============= Feature Task Start - 1
         * ============= Feature Task Start - 2
         * ============= Feature Task Start - 3
         * bkjeon-2
         * bkjeon-1
         * bkjeon-3
         * ============= Feature Task Start - 4
         * ============= Feature Task Start - 5
         * bkjeon-5
         * bkjeon-4
         */
        for (int i = 1; i <= 5; i++) {
            ListenableFuture<String> listenableFuture = asyncService.isListenableFutureReturn(i + "");
            listenableFuture.addCallback(System.out::println, error -> System.out.println(error.getMessage()));
        }
    }

    @ApiOperation("@Async 테스트 (completableFuture)")
    @GetMapping("/completableFuture")
    public void isAsyncCompletableFuture() throws ExecutionException, InterruptedException {
        asyncService.isCompletableFutureReturn("test");
    }

    @ApiOperation("@Async 테스트 [연산순서] (completableFuture thenApply()")
    @GetMapping("/completableFutureThenApply")
    public void isAsyncCompletableFutureThenApply() throws ExecutionException, InterruptedException {
        asyncService.isCompletableFutureReturnThenApply("test");
    }

    @ApiOperation("@Async 테스트 [연산순서] (completableFuture thenAccept()")
    @GetMapping("/completableFutureThenAccept")
    public void isAsyncCompletableFutureThenAccept() throws ExecutionException, InterruptedException {
        asyncService.isCompletableFutureReturnThenAccept("test thenAccept()");
    }

    @ApiOperation("@Async 테스트 [연산순서] (completableFuture thenAccept()")
    @GetMapping("/completableFutureThenRun")
    public void isAsyncCompletableFutureThenRun() {
        asyncService.isCompletableFutureReturnThenRun("test thenRun()");
    }

    @ApiOperation("@Async 테스트 [결합] (completableFuture thenCompose()")
    @GetMapping("/completableFutureThenCompose")
    public void isAsyncCompletableFutureThenCompose() throws ExecutionException, InterruptedException {
        asyncService.isCompletableFutureReturnThenCompose("test thenCompose()");
    }

    @ApiOperation("@Async 테스트 [결합] (completableFuture thenCombine()")
    @GetMapping("/completableFutureThenCombine")
    public void isAsyncCompletableFutureThenCombine() throws ExecutionException, InterruptedException {
        asyncService.isCompletableFutureReturnThenCombine("test thenCombine()");
    }

    @ApiOperation("@Async 테스트 [결합] (completableFuture thenAcceptBoth()")
    @GetMapping("/completableFutureThenAcceptBoth")
    public void isAsyncCompletableFutureThenAcceptBoth() {
        asyncService.isCompletableFutureReturnThenAcceptBoth("test thenAcceptBoth()");
    }

}
