package com.example.bkjeon.base.services.api.v1.actuator.controller;

import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/tags")
@RequiredArgsConstructor
public class TagController {

    // private final MyQueueManagerWithTags myQueueManagerWithTags;

    // extraTags 는 추가적으로 Tag 가 필요시에 사용하면되고 형태는 key, value, key, value ... 이다
    @Counted(value = "my.counted", extraTags = { "type", "value1", "type2", "value2" })
    @GetMapping("push")
    public String push() {
        // myQueueManagerWithTags.push();
        return HttpStatus.OK.toString();
    }

    @Counted(value = "my.counted", extraTags = { "type3", "value3", "type4", "value4" })
    @GetMapping("pop")
    public String pop() {
        // myQueueManagerWithTags.pop();
        return HttpStatus.OK.toString();
    }

}
