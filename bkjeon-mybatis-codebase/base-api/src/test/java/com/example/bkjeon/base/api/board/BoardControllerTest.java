package com.example.bkjeon.base.api.board;

import com.example.bkjeon.dto.board.BoardRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class BoardControllerTest {

    private final static String VERSION_NAME = "v1";
    private final static String BASE_URI_PATH = "/" + VERSION_NAME + "/boards";

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
            .userId("tester")
            .build();
        String userDtoJsonString = objectMapper.writeValueAsString(boardRequestDTO);

        mockMvc.perform(post(BASE_URI_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJsonString));
    }

}
