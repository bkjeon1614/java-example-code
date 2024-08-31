package com.example.bkjeon.base.api.v1.example;

import com.example.bkjeon.base.api.v1.controller.example.dto.PostsSaveRequestDTO;
import com.example.bkjeon.base.api.v1.controller.example.dto.PostsUpdateRequestDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {

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
    @DisplayName("[GET] v1/posts/1")
    void postFindById() throws Exception {
        mockMvc.perform(get("/v1/posts/1")
            .header("ACCESS-TOKEN", accessToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("[POST] v1/posts")
    void postSave() throws Exception {
        PostsSaveRequestDTO postsSaveRequestDTO = PostsSaveRequestDTO.builder()
            .title("제목")
            .content("내용")
            .build();
        String requestJsonString = objectMapper.writeValueAsString(postsSaveRequestDTO);

        mockMvc.perform(post("/v1/posts")
            .header("ACCESS-TOKEN", accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJsonString))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("[PUT] v1/posts/1")
    void postUpdate() throws Exception {
        PostsUpdateRequestDTO postsUpdateRequestDTO = PostsUpdateRequestDTO.builder()
            .title("제목 [수정]")
            .content("내용 [수정]")
            .build();
        String requestJsonString = objectMapper.writeValueAsString(postsUpdateRequestDTO);

        mockMvc.perform(put("/v1/posts/1")
            .header("ACCESS-TOKEN", accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJsonString))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("[DELETE] v1/posts/3")
    void postDelete() throws Exception {
        mockMvc.perform(delete("/v1/posts/3")
            .header("ACCESS-TOKEN", accessToken)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

}
