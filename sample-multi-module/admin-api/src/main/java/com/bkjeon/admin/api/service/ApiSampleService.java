package com.bkjeon.admin.api.service;

import com.bkjeon.admin.entity.Sample;
import com.bkjeon.admin.service.SampleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiSampleService {

    @Autowired
    private SampleService sampleService;

    public List<Sample> getSamples() {
        return sampleService.getSamples();
    }

    public Sample getSample(Long sampleId) {
        return sampleService.getSample(sampleId);
    }

    public Sample setSample(Sample command) {
        return sampleService.setSample(command);
    }

    public Sample putSample(Long sampleId, Sample command) {
        Sample sample = sampleService.getSample(sampleId);
        sample.setName(command.getName());

        return sampleService.putSample(sampleId, sample);
    }

    public void delSample(Long sampleId) {
        sampleService.delSample(sampleId);
    }

}
