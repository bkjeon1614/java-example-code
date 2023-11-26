package com.example.bkjeon.base.services.api.v1.actuator;

import com.example.bkjeon.base.actuator.counter.ApplicationRequestManager;
import com.example.bkjeon.base.actuator.counter.ApplicationRequestWithoutMicrometer;
import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/counter")
@RequiredArgsConstructor
public class CounterController {

    private final ApplicationRequestManager applicationRequestManager;
    private final ApplicationRequestWithoutMicrometer applicationRequestWithoutMicrometer;

    @GetMapping("req")
    public String req() {
        applicationRequestManager.increase();
        applicationRequestWithoutMicrometer.increase();
        return "ok";
    }

    @Counted("my.counted.counter")
    @GetMapping("req2")
    public String req2() {
        return "OK";
    }

}
