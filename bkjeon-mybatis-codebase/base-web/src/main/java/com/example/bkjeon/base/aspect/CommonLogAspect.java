package com.example.bkjeon.base.aspect;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
@Aspect
public class CommonLogAspect {

    /**
     * Global Output System Log (Service)
     * @param joinPoint
     */
    @Before("execution(* com.example.bkjeon.base.services.api.*.*.*Service.*(..))")
    public void outputCommonServiceLogging(JoinPoint joinPoint) {
        try {
            Signature signature = joinPoint.getSignature();
            // 로그 출력
            log.info("------------ CommonLogAspect Request Service Method: {}", signature.toShortString());
        } catch (Exception e) {
            log.error(
                "------------ CommonLogAspect outputCommonServiceLogging(Aspect) ERROR !! {}",
                e.getMessage()
            );
        }
    }

    /**
     * Global Logging (Controller)
     * @param proceedingJoinPoint
     * @return
     */
    @Around("execution(* com.example.bkjeon.base.services.api.*.*.*Controller.*(..))")
    public Object setCommonControllerLogging(ProceedingJoinPoint proceedingJoinPoint) {
        Object result = null;

        try {
            Signature signature = proceedingJoinPoint.getSignature();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

            long start = System.currentTimeMillis();
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();

            if (request.getMethod().equals("POST")) {
                // 로그 저장
                Object[] args = proceedingJoinPoint.getArgs();
                StringBuffer sb = new StringBuffer();
                for (int i=0; i < args.length; i++) {
                    if (args[i] != null) {
                        sb.append(args[i].toString());
                    }
                }

                /*
                CommonESLogSaveRequestDTO commonESLogSaveRequestDTO = CommonESLogSaveRequestDTO
                    .builder()
                    .logType("create")
                    .callUrl(request.getRequestURL().toString())
                    .callMthdSpVal(request.getMethod())
                    .callParaVal(sb.toString())
                    .svcCallNm("pda")
                    .svcMthdNm(signature.toShortString())
                    .execTme((end - start))
                    .logDesc("Global Logging")
                    .loginId("system")
                    .build();
                 */
            }
        } catch (Throwable throwable) {
            log.error("CommonLogAspect setCommonControllerLogging(Aspect) ERROR !! {}", throwable.getMessage());
        }

        return result;
    }

}
