package com.example.bkjeon.base.board.api.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.oliveyoung.pda.dto.board.BoardRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("메인 게시글 등록 테스트")
    void board_insert_test() throws Exception {
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
            .boardTitle("제목")
            .boardContents("내용입니다")
            .userId("tseter")
            .build();
        String userDtoJsonString = objectMapper.writeValueAsString(boardRequestDTO);

        mockMvc.perform(post("/v1/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJsonString));
    }

    @Test
    @DisplayName("답글 등록 테스트")
    void board_reply_insert_test() throws Exception {
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
            .boardTitle("[RE] 제목")
            .boardContents("답글 내용입니다.")
            .userId("olive")
            .build();
        String userDtoJsonString = objectMapper.writeValueAsString(boardRequestDTO);

        mockMvc.perform(post("/v1/boards/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJsonString));
    }

}
