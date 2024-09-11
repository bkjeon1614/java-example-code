package com.example.bkjeon.base.api.v1.feign;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

import com.example.bkjeon.base.api.helper.IntergrationTest;
import com.example.bkjeon.base.feign.user.FeignUserClient;
import com.example.bkjeon.base.feign.user.entity.FeignUser;
import com.example.bkjeon.base.feign.user.model.FeignUserRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class FeignExampleControllerTest {

    @Autowired
    private FeignUserClient feignUserClient;

    @IntergrationTest
    @DisplayName("[Intergration] Feign 을 통한 User 목록 조회")
    void board_insert_test() {
        // given
        FeignUserRequest feignUserRequest = FeignUserRequest.builder().testCode("bkjeon").build();

        // when
        List<FeignUser> feignUserList = feignUserClient.getUserList(feignUserRequest);

        // then
        assertThat(feignUserList).isNotNull();
    }

}
