package com.bkjeon.feature.mapper.sample;

import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.entity.sample.SampleOut;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapper {

    List<Sample> selectSampleIdsList(List<Long> idList);
    List<Sample> selectSampleList();
    void insertSample(SampleOut sample);
    void insertSampleOutList(List<SampleOut> sampleList);

}
