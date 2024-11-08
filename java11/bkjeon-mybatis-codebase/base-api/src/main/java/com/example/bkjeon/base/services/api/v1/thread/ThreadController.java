package com.example.bkjeon.base.services.api.v1.thread;

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
