package com.bkjeon.admin.service;

import com.bkjeon.admin.entity.Sample;
import com.bkjeon.admin.repository.SampleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    @Transactional(readOnly = true)
    public List<Sample> getSamples() {
        return sampleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sample getSample(Long sampleId) {
        return sampleRepository.findById(sampleId).get();
    }

    @Transactional
    public Sample setSample(Sample command) {
        return sampleRepository.save(command);
    }

    @Transactional
    public Sample putSample(Long sampleId, Sample sample) {
        sample = sampleRepository.findById(sampleId).get();
        return sampleRepository.saveAndFlush(sample);
    }

    @Transactional
    public void delSample(Long sampleId) {
        sampleRepository.deleteById(sampleId);
    }

}
