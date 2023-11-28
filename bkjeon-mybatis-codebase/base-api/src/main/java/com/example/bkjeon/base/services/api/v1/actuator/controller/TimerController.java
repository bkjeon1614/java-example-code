package com.example.bkjeon.base.services.api.v1.actuator.controller;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/timers")
@RequiredArgsConstructor
public class TimerController {

    private final Timer timer;
    private final MeterRegistry meterRegistry;

    @GetMapping("timer1")
    public String getTimer1() {
        timer.record(() -> {
            // 시간을 재고 싶은 로직을 넣는다.
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        return HttpStatus.OK.getReasonPhrase();
    }

    @GetMapping("timer2")
    public String getTimer2() throws InterruptedException {
        Timer.Sample sample = Timer.start(meterRegistry);

        // logic
        Thread.sleep(4000);

        sample.stop(meterRegistry.timer("my.timer2"));

        return HttpStatus.OK.getReasonPhrase();
    }

    @Timed("my.timer3")
    @GetMapping("timer3/{sleepSecond}")
    public String getTimer3(@PathVariable("sleepSecond") int sleepSecond) throws InterruptedException {
        Thread.sleep(sleepSecond * 1000);
        return HttpStatus.OK.getReasonPhrase();
    }

}
