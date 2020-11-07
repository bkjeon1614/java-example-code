package com.example.bkjeon.base.board;

import com.example.bkjeon.feature.board.BoardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BoardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("메인 게시글 등록 테스트")
    void board_insert_test() throws Exception {
        BoardDTO boardDTO = BoardDTO.builder()
                .boardTitle("제목")
                .boardContents("내용입니다.")
                .userId("tester")
                .build();
        String userDtoJsonString = objectMapper.writeValueAsString(boardDTO);

        mockMvc.perform(post("/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJsonString));
    }

    @Test
    @DisplayName("답글 등록 테스트")
    void board_reply_insert_test() throws Exception {
        BoardDTO boardDTO = BoardDTO.builder()
                .boardTitle("[RE] 제목")
                .boardContents("답글 내용입니다.")
                .userId("tester2")
                .build();
        String userDtoJsonString = objectMapper.writeValueAsString(boardDTO);

        mockMvc.perform(post("/v1/boards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJsonString));
    }

}
