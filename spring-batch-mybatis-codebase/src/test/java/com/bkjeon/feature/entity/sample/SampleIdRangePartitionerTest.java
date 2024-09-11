package com.bkjeon.feature.entity.sample;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkjeon.feature.mapper.sample.SampleMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.ExecutionContext;

@ExtendWith(MockitoExtension.class)
public class SampleIdRangePartitionerTest {

    private static SampleIdRangePartitioner partitioner;

    @Mock
    private SampleMapper sampleMapper;

    @Test
    void gridSize에_맞게_id가_분할된다() throws Exception {
        // given
        // (1) findMinId(), findMaxId() 메서드가 호출되면 각각 1L, 10L을 반환하도록 설정
        Mockito.lenient()
            .when(sampleMapper.findMinId())
            .thenReturn(1L);

        Mockito.lenient()
            .when(sampleMapper.findMaxId())
            .thenReturn(10L);

        // (2) SampleIdRangePartitioner 인스턴스 생성
        partitioner = new SampleIdRangePartitioner(sampleMapper);

        // when
        // (3) gridSize가 5일 때 partition() 메서드 호출 (5개의 파티션으로 분할하면 각 파티션당 2개씩 할당)
        Map<String, ExecutionContext> executionContextMap = partitioner.partition(5);

        // then
        // (4) 첫번째 파티션에 등록된 minId, maxId를 검증 (예상결과: minId=1, maxId=2)
        ExecutionContext partition1 = executionContextMap.get("partition0");
        assertThat(partition1.getLong("minId")).isEqualTo(1L);
        assertThat(partition1.getLong("maxId")).isEqualTo(2L);

        // (5) 마지막 파티션에 등록된 minId, maxId를 검증 (예상결과: minId=9, maxId=10)
        ExecutionContext partition5 = executionContextMap.get("partition4");
        assertThat(partition5.getLong("minId")).isEqualTo(9L);
        assertThat(partition5.getLong("maxId")).isEqualTo(10L);
    }

}
