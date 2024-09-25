package com.example.bkjeon.base.services.api.v1.setting;

import com.example.bkjeon.annotation.LogExecutionTime;
import com.example.bkjeon.util.ThreadUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/annotation")
@RequiredArgsConstructor
public class AnnotationController {

    @ApiOperation("Annotation TEST API")
    @LogExecutionTime
    @GetMapping("examples")
    public void getCallMethod() throws InterruptedException {
        ThreadUtil.threadSleep(5000);
    }

}
