package com.bkjeon.feature.entity.sample;

import com.bkjeon.feature.mapper.sample.SampleMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

/**
 * Partitioner 는 각 Worker Step들에게 어떤 Step Executions 변수를 가지게 할지를 결정하고, 그에 따라 생성할 Worker Step 수를 결정
 * 해당 Partitioner 는 데이터의 시작 PK 값과 끝 PK 값을 조회해 파티션별로 분할해서 할당하여 처리
 */
@Slf4j
@RequiredArgsConstructor
public class SampleIdRangePartitioner implements Partitioner {

    private final SampleMapper sampleMapper;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        long min = sampleMapper.findMinId();
        long max = sampleMapper.findMaxId();
        long targetSize = (max - min) / gridSize + 1;

        Map<String, ExecutionContext> result = new HashMap<>();
        long number = 0;
        long start = min;
        long end = start + targetSize - 1;

        while (start <= max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= max) {
                end = max;
            }

            value.putLong("minId", start); // 각 파티션마다 사용될 minId
            value.putLong("maxId", end); // 각 파티션마다 사용될 maxId
            start += targetSize;
            end += targetSize;
            number++;
        }

        return result;
    }

}
