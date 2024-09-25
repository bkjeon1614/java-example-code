package com.example.bkjeon.base.aspect;

import com.example.bkjeon.base.services.api.v1.actuator.service.counter.CustomCounterService;
import com.example.bkjeon.enums.actuator.MetricType;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 실시간 카운트 집계
 */
@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class CounterAspect {

    private final CustomCounterService customCounterService;

    @Pointcut("@annotation(com.example.bkjeon.annotation.CustomCounter)")
    private void isCustomCounter(){}

    @Before("isCustomCounter()")
    public void isCustomIncrCount() {
        try {
            LocalDateTime nowDateTime = new LocalDateTime();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();

            String valueName = request.getServletPath().split("/")[4]
                + "_"
                + nowDateTime.toString("yyMMddHHmmss");
            customCounterService.increment(
                MetricType.CUSTOM_METRIC_INFO.getMetricName(),
                MetricType.CUSTOM_METRIC_INFO.getTagName(),
                valueName,
                2,
                10
            );

            log.debug("Value Size: {}", customCounterService.getSize(
                MetricType.CUSTOM_METRIC_INFO.getMetricName(),
                MetricType.CUSTOM_METRIC_INFO.getTagName(), valueName));

            log.debug("Value Sum Size: {}", customCounterService.getAllValueSumSize(
                MetricType.CUSTOM_METRIC_INFO.getMetricName(),
                MetricType.CUSTOM_METRIC_INFO.getTagName(), valueName, 2));
        } catch (Throwable throwable) {
            log.error("isDshopSetCount(Aspect) ERROR !! {}", (Object) throwable.getStackTrace());
        }
    }

}