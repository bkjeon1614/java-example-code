package com.example.bkjeon.base.api.v1.board;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import com.example.bkjeon.base.api.helper.AcceptanceTest;
import com.example.bkjeon.base.api.helper.UnitTest;
import com.example.bkjeon.base.services.api.v1.board.BoardService;
import com.example.bkjeon.dto.board.BoardRequestDTO;
import com.example.bkjeon.dto.board.BoardResponseDTO;
import com.example.bkjeon.model.response.ApiResponse;

import io.restassured.RestAssured;
import io.restassured.http.Header;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class BoardControllerTest {

    private final static String VERSION_NAME = "api/v1";
    private final static String BASE_URI_PATH = "/" + VERSION_NAME + "/boards";

    @LocalServerPort
    private int port;

    Header header = new Header("key", "val");

    @Autowired
    private BoardService boardService;

    @AcceptanceTest
    @DisplayName("[Acceptance] 메인 게시글 등록 테스트")
    void board_insert_test() {
        BoardRequestDTO boardRequestDTO = BoardRequestDTO.builder()
            .boardTitle("제목 (테스트)")
            .boardContents("내용입니다 (테스트)")
            .userId("admin")
            .build();

        RestAssured
            .given().port(port).log().all()
                .header(header)
                .body(boardRequestDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
                .post(BASE_URI_PATH)
            .then().log().all()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("statusCode", Matchers.equalTo(200));
    }

    @AcceptanceTest
    @DisplayName("[Acceptance] 게시글 리스트 조회 테스트")
    void get_board_list() {
        // given (param)
        Map<String, Integer> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 10);

        RestAssured
            .given().port(port).log().all()
                .header(header)
                .queryParams(params)
            .when()
                .get(BASE_URI_PATH)
            .then().log().all()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("statusCode", Matchers.equalTo(200));
    }

    @AcceptanceTest
    @DisplayName("[Acceptance] 게시글 상세 조회 테스트")
    void get_board_detail() {
        RestAssured
            .given().port(port).log().all()
                .header(header)
            .when()
                .get(BASE_URI_PATH + "/1")
            .then().log().all()
                .assertThat().statusCode(HttpStatus.OK.value())
                .body("statusCode", Matchers.equalTo(200));
    }

    @UnitTest
    @DisplayName("[Unit] 게시글 상세 조회")
    void get_service_board_detail() {
        // given
        Long boardNo = 1L;

        // when
        ApiResponse<BoardResponseDTO> board = boardService.getBoard(boardNo);

        // then
        assertThat(board.getData()).isNotNull();
    }

}
