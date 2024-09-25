package com.bkjeon.feature.mapstruct;

import com.bkjeon.feature.entity.sample.Sample;
import com.bkjeon.feature.entity.sample.SampleOut;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SampleMapstruct {

    SampleMapstruct INSTANCE = Mappers.getMapper(SampleMapstruct.class);

    SampleOut toSampleOut(Sample sample);

}
