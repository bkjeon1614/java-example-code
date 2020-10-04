package com.example.bkjeon.base.board;

import com.example.bkjeon.base.services.api.v1.board.BoardService;
import com.example.bkjeon.feature.board.BoardDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardControllerTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void 메인_게시글_등록_테스트() {
        BoardDTO boardDTO = new BoardDTO(
            "제목",
            "내용입니다.",
            "tester"
        );
        boardService.setBoard(boardDTO);
    }

    @Test
    public void 답글_등록_테스트() {
        BoardDTO boardDTO = new BoardDTO(
            "[RE] 제목",
            "답글 내용입니다.",
            "tester"
        );
        boardService.setBoardReply(
            1L,
            boardDTO
        );
    }

}
