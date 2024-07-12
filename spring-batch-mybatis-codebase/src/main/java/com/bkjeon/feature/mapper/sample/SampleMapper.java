package com.bkjeon.feature.mapper.sample;

import com.bkjeon.feature.entity.sample.Sample;
import java.util.List;

public interface SampleMapper {

    List<Sample> selectSampleList();
    void insertSample(Sample sample);

}
