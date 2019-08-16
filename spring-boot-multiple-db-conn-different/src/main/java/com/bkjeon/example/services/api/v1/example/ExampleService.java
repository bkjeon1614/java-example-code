package com.bkjeon.example.services.api.v1.example;

import com.bkjeon.example.feature.example.Example;
import com.bkjeon.example.feature.example.ExampleMapper;
import com.bkjeon.example.feature.example.ExampleParam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ExampleService {

    private ExampleMapper exampleMapper;

    @Transactional(readOnly = true)
    public List<Example> selectExampleList(int page, int size, ExampleParam param) {
        Integer offset = (page - 1) * size;
        return exampleMapper.selectExampleList(size, offset, param);
    }

    @Transactional(readOnly = true)
    public int selectExampleListTotalCnt(ExampleParam param) {
        return exampleMapper.selectExampleListTotalCnt(param);
    }

    @Transactional(readOnly = true)
    public Example selectExampleInfo(Integer id) {
        return exampleMapper.selectExampleInfo(id);
    }

    @Transactional
    public void insertExample(ExampleParam param) {
        Example example = Example.builder()
            .name(param.getName())
            .age(param.getAge())
            .build();
        exampleMapper.insertExample(example);
    }

    @Transactional
    public void updateExample(Integer id, ExampleParam param) {
        Example example = Example.builder()
                .id(id)
                .name(param.getName())
                .age(param.getAge())
                .build();
        exampleMapper.updateExample(example);
    }
    @Transactional
    public void updateExampleName(Integer id, String name) {
        Example example = Example.builder()
                .id(id)
                .name(name)
                .build();
        exampleMapper.updateExampleName(example);
    }

    @Transactional
    public void deleteExample(Integer id) {
        Example example = Example.builder()
                .id(id)
                .build();
        exampleMapper.deleteExample(example);
    }

}
