package com.example.bkjeon.base.services.api.v1.actuator.service.counter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomCounterService {

    private final MeterRegistry meterRegistry;

    /**
     * 카운트 저장
     * @param metricName  metric 명
     * @param tagName   tag 명
     * @param valueName tag value 값 저장 (Ex: {매장/기획전 번호}_{yyMMddHHmmss})
     * @param delLimit 최근 시간 오름 차순 으로 이전에 먼저 등록된 데이터 를 제거할 값의 개수
     */
    public void increment(String metricName, String tagName, String valueName, int delLimit,
        int maintainLimit) {
        List<Meter> meterList = meterRegistry.getMeters().stream()
            .filter(meter -> metricName.equals(meter.getId().getName()))
            .collect(Collectors.toList());
        List<Tag> tagList = meterList.stream()
            .flatMap(meter -> meter.getId().getTags().stream())
            .filter(tag -> valueName.split("_")[0].equals(tag.getValue().split("_")[0]))
            .sorted(Comparator.comparing(Tag::getValue))
            .collect(Collectors.toList());
        List<Tag> removeTagList = tagList.stream()
            .limit(delLimit)
            .collect(Collectors.toList());

        if (tagList.size() > maintainLimit) {
            for (Tag tag: removeTagList) {
                remove(metricName, tag.getKey(), tag.getValue());
            }
        }

        Counter.builder(metricName)
            .tag(tagName, valueName)
            .register(meterRegistry).increment();
    }

    /**
     * 특정 tag value 의 카운트 조회
     * @param metricName  metric 명
     * @param tagName   tag 명
     * @param valueName tag value 값 저장 (Ex: {매장/기획전 번호}_{yyMMddHHmmss})
     */
    public Double getSize(String metricName, String tagName, String valueName) {
        return Counter.builder(metricName)
            .tag(tagName, valueName)
            .register(meterRegistry).count();
    }

    /**
     * 특정 tag value 의 최근 시간 내림 차순 으로 총 n 개의 카운트 값 을 sum 하여 가져옴
     * @param metricName  metric 명
     * @param tagName   tag 명
     * @param valueName tag value 값 저장 (Ex: {매장/기획전 번호}_{yyMMddHHmmss})
     * @param limit 카운트 를 집계할 최근 시간 내림 차순 으로 sum 할 개수
     */
    public Double getAllValueSumSize(String metricName, String tagName, String valueName, int limit) {
        List<Meter> meterList = meterRegistry.getMeters().stream()
            .filter(meter -> metricName.equals(meter.getId().getName()))
            .collect(Collectors.toList());

        return meterList.stream()
            .flatMap(meter -> meter.getId().getTags().stream())
            .filter(tag -> valueName.split("_")[0].equals(tag.getValue().split("_")[0]))
            .sorted(Comparator.comparing(Tag::getValue).reversed())
            .limit(limit)
            .mapToDouble(tag -> Counter.builder(metricName).tag(tagName, tag.getValue())
                .register(meterRegistry).count())
            .sum();
    }

    /**
     * 특정 tag value 를 삭제
     * @param metricName  metric 명
     * @param tagName   tag 명
     * @param valueName tag value 값 저장 (Ex: {매장/기획전 번호}_{yyMMddHHmmss})
     */
    private void remove(String metricName, String tagName, String valueName) {
        meterRegistry.remove(Counter.builder(metricName)
            .tag(tagName, valueName)
            .register(meterRegistry));
    }

}