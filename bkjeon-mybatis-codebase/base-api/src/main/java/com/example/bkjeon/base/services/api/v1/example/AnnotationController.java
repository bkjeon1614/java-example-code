package com.example.bkjeon.base.services.api.v1.example;

import com.example.bkjeon.annotation.LogExecutionTime;
import com.example.bkjeon.util.ThreadUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/annotation")
@AllArgsConstructor
public class AnnotationController {

    @LogExecutionTime
    @ApiOperation("Annotation TEST API")
    @GetMapping("examples")
    public void getCallMethod() throws InterruptedException {
        ThreadUtil.threadSleep(5000);
        // 082840000
    }

}
