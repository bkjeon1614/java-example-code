package com.example.bkjeon.base.api.v1.example;

import com.example.bkjeon.base.api.v1.controller.security.jwt.dto.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    private String accessToken;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() throws Exception {
        LoginDTO loginDTO = LoginDTO.builder()
            .username("bkjeon")
            .password("wjsqhdrms")
            .build();
        String loginRequestJsonString = objectMapper.writeValueAsString(loginDTO);
        MvcResult mvcResult = mockMvc.perform(post("/v1/security/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(loginRequestJsonString))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        accessToken = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.token");
    }

    @Test
    @DisplayName("[GET] v1/security/user")
    void getMyUserInfo() throws Exception {
        mockMvc.perform(get("/v1/security/user")
            .header("ACCESS-TOKEN", accessToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("[GET] v1/security/user/bkjeon")
    void getUserInfo() throws Exception {
        mockMvc.perform(get("/v1/security/user/bkjeon")
            .header("ACCESS-TOKEN", accessToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
