package com.example.bkjeon.base.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class CommonLogAspect {

//    // @AfterReturning(pointcut = "execution(* com.example.bkjeon.base.services.api.example.ExampleService.*(..) && args(example))", returning = "result")
//    @AfterReturning(pointcut = "execution(* kr.co.oliveyoung.oms.services.api.example.ExampleService.*(..))", returning = "result")
//    public void doSomethingAfterReturning(JoinPoint joinPoint, Object result) {}

//    @AfterThrowing(pointcut = "execution(* com.example.bkjeon.base.services.api.example.ExampleService.*(..))", throwing = "exception")
//    public void afterThrowingMethod(JoinPoint joinPoint, Exception exception) throws Exception {
//        log.info("afterThrowingMethod() called: " + exception.getMessage());
//    }

}
