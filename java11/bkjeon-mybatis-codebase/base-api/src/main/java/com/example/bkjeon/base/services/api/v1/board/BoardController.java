package com.example.bkjeon.base.services.api.v1.board;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.dto.board.BoardRequestDTO;
import com.example.bkjeon.dto.board.BoardResponseDTO;
import com.example.bkjeon.model.response.ApiResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/boards")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation("게시글 리스트 조회")
    @GetMapping
    public ApiResponse getBoardList(
        @ApiParam(
            value = "page 번호를 설정할 수 있으며 설정 값은 1-N까지 입니다.",
            name = "page",
            defaultValue = "1",
            required = true
        ) @RequestParam Integer page,
        @ApiParam(
            value = "페이지 별 레코드 갯수를 설정 할 수 있습니다.",
            name = "size",
            defaultValue = "10",
            required = true
        ) @RequestParam Integer size
    ) {
        return boardService.getBoardList(page, size);
    }

    @ApiOperation("게시글 상세 조회")
    @GetMapping("{boardNo}")
    public ApiResponse<BoardResponseDTO> getBoard(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo
    ) {
        return boardService.getBoard(boardNo);
    }

    @ApiOperation("메인 게시글 등록")
    @PostMapping
    public ApiResponse setBoard(
        @RequestBody @Valid final BoardRequestDTO requestDTO
    ) {
        return boardService.setBoard(requestDTO);
    }

    @ApiOperation("답글 등록")
    @PostMapping("{boardNo}/replies")
    public ApiResponse setBoardReply(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo,
        @RequestBody @Valid final BoardRequestDTO requestDTO
    ) {
        return boardService.setBoardReply(boardNo, requestDTO);
    }

    @ApiOperation("게시글 수정")
    @PutMapping("{boardNo}")
    public ApiResponse putBoard(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo,
        @RequestBody @Valid final BoardRequestDTO requestDTO
    ) {
        return boardService.putBoard(boardNo, requestDTO);
    }

}
