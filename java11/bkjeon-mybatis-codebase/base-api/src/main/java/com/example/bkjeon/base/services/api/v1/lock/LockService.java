package com.example.bkjeon.base.services.api.v1.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LockService {

    private final Lock lock = new ReentrantLock();
    private final Semaphore semaphore = new Semaphore(3); // 동시에 3개의 스레드만 접근 가능

    public void isMutex(int threadId) {
        // 자원 진입 시도
        log.info("Thread {} 자원 진입 시도", threadId);
        lock.lock();
        try {
            // 자원 진입!
            log.info("Thread {} 자원 진입 완료!", threadId);
            // 자원에 대한 작업을 수행하는 동안 지연을 추가하여 로그 출력 관찰
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // 자원 사용 완료!
            log.info("Thread {} 자원 사용 완료!", threadId);
            lock.unlock();
        }
    }

    public void isSemaphore(int threadId) {
        try {
            // 자원 진입 시도
            log.info("Thread {} 자원 진입 시도", threadId);

            // 허가증 획득 시도
            semaphore.acquire();

            // 자원 진입!
            log.info("Thread {} 자원 진입 완료!", threadId);

            // 자원에 대한 작업을 수행하는 동안 일부 지연을 추가
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            log.info("Thread {} 자원 사용 완료!", threadId);
            semaphore.release();
        }
    }

}
