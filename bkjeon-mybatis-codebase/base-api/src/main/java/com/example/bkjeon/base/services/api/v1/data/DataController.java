package com.example.bkjeon.base.services.api.v1.data;

import com.example.bkjeon.domain.data.DataUser;
import io.swagger.annotations.ApiOperation;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.DiffResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/data")
public class DataController {

    @ApiOperation("변경된 객체 감지")
    @GetMapping("objectCompare")
    public String isObjectCompare() {
        List<DataUser> before = Arrays.asList(
            new DataUser("A", 30),
            new DataUser("51021614", 20),
            new DataUser("5102", 10)
        );
        List<DataUser> after = Arrays.asList(
            new DataUser("A", 30),
            new DataUser("51021614", 10),
            new DataUser("51023", 5000)
        );

        DiffResult diffResult = before.get(2).diff(after.get(2));
        diffResult.getDiffs().forEach(diff -> log.info(">>>>>>>>>>>>>>>>>>>>>>>> {}", diff.toString()));
        return "OK";
    }

}
