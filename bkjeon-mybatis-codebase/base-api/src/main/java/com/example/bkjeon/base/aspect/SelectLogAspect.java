package com.example.bkjeon.base.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class SelectLogAspect {

    @Pointcut("execution(* com.example.bkjeon.base.services.api.v1.crud.CrudService.getCallMethod(..))")
    public void getCallMethod(){}

    @Pointcut("execution(* com.example.bkjeon.base.services.api.v1.crud.CrudService.setCallMethod(..))")
    public void setCallMethod(){}

    @Pointcut("execution(* com.example.bkjeon.base.services.api.v1.crud.CrudService.putCallMethod(..))")
    public void putCallMethod(){}

    @Pointcut("execution(* com.example.bkjeon.base.services.api.v1.crud.CrudService.patchCallMethod(..))")
    public void patchCallMethod(){}

    @Pointcut("execution(* com.example.bkjeon.base.services.api.v1.crud.CrudService.delCallMethod(..))")
    public void delCallMethod(){}

    @Around("getCallMethod() || setCallMethod() || putCallMethod() || patchCallMethod() || delCallMethod()")
    public Object outputCrudServiceLogging(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;

        try {
            Signature signature = proceedingJoinPoint.getSignature();

            long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();

            log.info("------------ SelectLogAspect Request Service Method: {}", signature.toShortString());
            log.info("------------ SelectLogAspect Request Service Method Execution Time: {}", (end - start));
        } catch (Throwable throwable) {
            log.error("------------ SelectLogAspect outputCrudServiceLogging(Aspect) ERROR !! {}", throwable.getMessage());
        }

        return result;
    }

}
