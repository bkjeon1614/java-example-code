package com.example.bkjeon.base.services.api.v1.thread.controller;

import com.example.bkjeon.base.services.api.v1.thread.repository.ItemRepository;
import com.example.bkjeon.base.services.api.v1.thread.service.ThreadLocalService;
import com.example.bkjeon.base.services.api.v1.thread.service.ThreadLocalServiceTest;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/thread")
public class ThreadController {

    private final ItemRepository itemRepository;
    private final ThreadLocalServiceTest threadLocalServiceTest;
    private final ThreadLocalService threadLocalService;

    @ApiOperation("ThreadLocal 사용 테스트")
    @GetMapping("/isThreadLocal")
    public void isThreadLocal() {
        log.info("main start");
        Runnable userA = () -> {
            threadLocalService.logic("userA");
        };
        Runnable userB = () -> {
            threadLocalService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start(); //A실행

        // sleep(2000); //동시성 문제 발생X
        sleep(100); //동시성 문제 발생O

        threadB.start(); //B실행
        sleep(3000); //쓰레드B가 끝날 때까지 메인 쓰레드 종료 대기
        log.info("main exit");
    }

    @ApiOperation("ThreadLocal 테스트(동시성 문제 발생 테스트)")
    @GetMapping("/isThreadLocalTest")
    public void isThreadLocalTest() {
        log.info("main start");
        Runnable userA = () -> {
            threadLocalServiceTest.logic("userA");
        };
        Runnable userB = () -> {
            threadLocalServiceTest.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");
        threadA.start(); //A실행

        // sleep(2000); //동시성 문제 발생X
        sleep(100); //동시성 문제 발생O

        threadB.start(); //B실행
        sleep(3000); //쓰레드B가 끝날 때까지 메인 쓰레드 종료 대기
        log.info("main exit");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("ForkJoinPool 테스트")
    @GetMapping("/isForkJoinPool")
    public void isForkJoinPool() throws InterruptedException {
        long beforeTime = System.currentTimeMillis();
        /*
        int priceA = itemRepository.getPriceByStoreName("storeA");
        int priceB = itemRepository.getPriceByStoreName("storeB");
        int priceC = itemRepository.getPriceByStoreName("storeC");
         */

        /*
        CompletableFuture<Void> priceA = CompletableFuture.supplyAsync(() -> {
                log.info("ThreadName = {}", Thread.currentThread().getName());
                return itemRepository.getPriceByStoreName("storeA");
            })
            .thenAccept(price -> log.info("price = {}", price));

        CompletableFuture<Void> priceB = CompletableFuture.supplyAsync(() -> {
                log.info("ThreadName = {}", Thread.currentThread().getName());
                return itemRepository.getPriceByStoreName("storeB");
            })
            .thenAccept(price -> log.info("price = {}", price));

        CompletableFuture<Void> priceC = CompletableFuture.supplyAsync(() -> {
                log.info("ThreadName = {}", Thread.currentThread().getName());
                return itemRepository.getPriceByStoreName("storeC");
            })
            .thenAccept(price -> log.info("price = {}", price));
        CompletableFuture.allOf(priceA, priceB, priceC).join();
         */

        /*
        log.info("price = {}", priceA);
        log.info("price = {}", priceB);
        log.info("price = {}", priceC);
         */

        /*
        List<String> stores = List.of("storeA", "storeB", "storeC");
        stores.parallelStream()
            .forEach(store -> {
                log.info("Thread Name = {}", Thread.currentThread().getName());
                log.info("price = {}", itemRepository.getPriceByStoreName(store));
            });

        Thread.sleep(1000);

        List<Integer> prices = List.of(100, 500, 3000);
        prices.forEach(price -> log.info("price = {}", price));
         */

        List<String> stores = List.of("storeA", "storeB", "storeC");
        List<CompletableFuture<Integer>> futures = stores.stream()
            .map(store -> CompletableFuture.supplyAsync(() -> itemRepository.getPriceByStoreName(store)))
            .collect(Collectors.toList());

        Thread.sleep(1000);

        futures.stream()
            .map(CompletableFuture::join)
            .forEach(price -> log.info("price = {}", price));

        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        log.info(">>>>>>>>>>>>>>>>> 실행시간: {}", secDiffTime);
    }

}
