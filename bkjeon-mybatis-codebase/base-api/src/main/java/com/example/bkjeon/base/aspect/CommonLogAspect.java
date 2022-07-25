package com.example.bkjeon.base.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.bkjeon.entity.common.log.CommonLog;
import com.example.bkjeon.mapper.common.log.CommonLogMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class CommonLogAspect {

    @Autowired
    private CommonLogMapper commonLogMapper;

    /**
     * Global Output System Log (Service)
     * @param proceedingJoinPoint
     */
    @Around("execution(* com.example.bkjeon.base.services.api.*.*.*Service.*(..))")
    public void outputCommonServiceLogging(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Signature signature = proceedingJoinPoint.getSignature();

            // 로그 출력
            log.info("------------ CommonLogAspect Request Service Method: {}", signature.toShortString());

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

            if (proceedingJoinPoint != null && !request.getMethod().equals("GET")) {
                // 로그 저장
                Object[] args = proceedingJoinPoint.getArgs();
                StringBuffer sb = new StringBuffer();
                for (int i=0; i < args.length; i++) {
                    if (args[i] != null) {
                        sb.append(args[i].toString());
                    }
                }

                CommonLog commonLog = CommonLog.builder()
                    .callUrl(request.getRequestURL().toString())
                    .callMthdSpVal(request.getMethod())
                    .callParaVal(sb.toString())
                    .svcCallNm("system")
                    .svcMthdNm(signature.toShortString())
                    .execTme((System.currentTimeMillis() - System.currentTimeMillis()))
                    .logDesc("Global Logging")
                    .sysRegrId("system")
                    .sysModrId("system")
                    .build();
                commonLogMapper.insertLog(commonLog);
            }
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
    /*
    @Around("execution(* com.example.bkjeon.base.services.api.*.*.*Controller.*(..))")
    public void setCommonControllerLogging(ProceedingJoinPoint proceedingJoinPoint) {
        try {
            Signature signature = proceedingJoinPoint.getSignature();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

            if (proceedingJoinPoint != null && !request.getMethod().equals("GET")) {
                // 로그 저장
                Object[] args = proceedingJoinPoint.getArgs();
                StringBuffer sb = new StringBuffer();
                for (int i=0; i < args.length; i++) {
                    if (args[i] != null) {
                        sb.append(args[i].toString());
                    }
                }

                CommonLog commonLog = CommonLog.builder()
                    .callUrl(request.getRequestURL().toString())
                    .callMthdSpVal(request.getMethod())
                    .callParaVal(sb.toString())
                    .svcCallNm("bkjeon")
                    .svcMthdNm(signature.toShortString())
                    .execTme((System.currentTimeMillis() - System.currentTimeMillis()))
                    .logDesc("Global Logging")
                    .sysRegrId("system")
                    .sysModrId("system")
                    .build();
                commonLogMapper.insertLog(commonLog);
            }
        } catch (Throwable t) {
            log.error("CommonLogAspect setCommonControllerLogging(Aspect) ERROR !! {}", t);
        }
    }
     */

}
