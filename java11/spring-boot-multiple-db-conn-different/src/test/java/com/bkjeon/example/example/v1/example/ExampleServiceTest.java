package com.bkjeon.example.example.v1.example;

import com.bkjeon.example.feature.example.ExampleParam;
import com.bkjeon.example.services.api.v1.example.ExampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExampleServiceTest {

    @Autowired
    private ExampleService exampleService;

    @Test
    public void getExampleList() {
        ExampleParam param = new ExampleParam();
        param.setName("TEST List");
        param.setAge(99);

        exampleService.selectExampleList(1, 10, param);
    }

    @Test
    public void getExampleTotalCount() {
        ExampleParam param = new ExampleParam();
        param.setName("TEST TotalCnt");
        param.setAge(99);

        exampleService.selectExampleListTotalCnt(param);
    }

    @Test
    public void setExample() {
        ExampleParam param = new ExampleParam();
        param.setName("TEST SAVE2");
        param.setAge(99);
        exampleService.insertExample(param);
    }

}
