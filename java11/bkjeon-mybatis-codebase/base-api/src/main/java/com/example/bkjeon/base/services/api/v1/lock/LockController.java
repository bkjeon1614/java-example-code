package com.example.bkjeon.base.services.api.v1.lock;

import io.swagger.annotations.ApiOperation;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/lock")
@RequiredArgsConstructor
public class LockController {

    private final LockService lockService;

    @ApiOperation("Mutex 예제")
    @GetMapping("/mutex")
    public void isMutex() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5); // 동시에 5개의 스레드를 실행
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            executor.submit(() -> lockService.isMutex(threadId));
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(10, TimeUnit.SECONDS);
        assert finished;
    }

    @ApiOperation("Semaphore 예제")
    @GetMapping("/semaphore")
    public void isSemaphore() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(10); // 동시에 10개의 스레드를 실행

        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            executor.submit(() -> lockService.isSemaphore(threadId));
        }

        executor.shutdown();
        boolean finished = executor.awaitTermination(20, TimeUnit.SECONDS);
        assert finished;
    }

}
