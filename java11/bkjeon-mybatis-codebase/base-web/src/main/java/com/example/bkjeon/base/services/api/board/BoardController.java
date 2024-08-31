package com.example.bkjeon.base.services.api.board;

import com.example.bkjeon.dto.board.BoardRequestDTO;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("boards")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation("게시글 리스트 조회")
    @GetMapping
    public ResponseEntity getBoardList(
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
    public ResponseEntity getBoard(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo
    ) {
        return boardService.getBoard(boardNo);
    }

    @ApiOperation("메인 게시글 등록")
    @PostMapping
    public ResponseEntity setBoard(
        @RequestBody @Valid BoardRequestDTO requestDTO
    ) {
        if (!boardService.setBoard(requestDTO)) {
            return new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.BAD_REQUEST.value(),
                    ResponseResult.FAIL.getText(),
                    requestDTO,
                    null
                ),
                HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                requestDTO,
                null
            ),
            HttpStatus.OK
        );
    }

    @ApiOperation("답글 등록")
    @PostMapping("{boardNo}/replies")
    public ResponseEntity setBoardReply(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo,
        @RequestBody @Valid BoardRequestDTO requestDTO
    ) {
        if (!boardService.setBoardReply(boardNo, requestDTO)) {
            return new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.BAD_REQUEST.value(),
                    ResponseResult.FAIL.getText(),
                    requestDTO,
                    null
                ),
                HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                requestDTO,
                null
            ),
            HttpStatus.OK
        );
    }

    @ApiOperation("게시글 수정")
    @PutMapping("{boardNo}")
    public ResponseEntity putBoard(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo,
        @RequestBody @Valid BoardRequestDTO requestDTO
    ) {
        if (!boardService.putBoard(boardNo, requestDTO)) {
            return new ResponseEntity(
                ApiResponse.res(
                    HttpStatus.BAD_REQUEST.value(),
                    ResponseResult.FAIL.getText(),
                    requestDTO,
                    null
                ),
                HttpStatus.BAD_REQUEST
            );
        }

        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                requestDTO,
                null
            ),
            HttpStatus.OK
        );
    }

}
