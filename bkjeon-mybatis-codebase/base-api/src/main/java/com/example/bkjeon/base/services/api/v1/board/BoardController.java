package com.example.bkjeon.base.services.api.v1.board;

import com.example.bkjeon.common.enums.ResponseResult;
import com.example.bkjeon.common.model.ApiResponseMessage;
import com.example.bkjeon.feature.board.BoardDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("v1/boards")
public class BoardController {

    private final BoardService boardService;

    @ApiOperation("게시글 리스트 조회")
    @GetMapping
    public ApiResponseMessage getBoardList(
        @ApiParam(
            value = "page 번호를 설정할 수 있으며 설정 값은 1-N까지 입니다.",
            name = "page",
            defaultValue = "1",
            required = true
        ) @RequestParam int page,
        @ApiParam(
            value = "페이지 별 레코드 갯수를 설정 할 수 있습니다.",
            name = "size",
            defaultValue = "10",
            required = true
        ) @RequestParam int size
    ) {
        return boardService.getBoardList(page, size);
    }

    @ApiOperation("게시글 상세 조회")
    @GetMapping("{boardNo}")
    public ApiResponseMessage getBoard(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo
    ) {
        return boardService.getBoard(boardNo);
    }

    @ApiOperation("메인 게시글 등록")
    @PostMapping
    public ApiResponseMessage setBoard(
        final @RequestBody @Valid BoardDTO boardDTO
    ) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "게시글 등록이 완료되었습니다.",
            null
        );
        result.setParams(boardDTO);

        if (!boardService.setBoard(boardDTO)) {
            result.setResult(ResponseResult.FAIL);
            result.setMessage("게시글 등록에 실패하였습니다.");
        }
        return result;
    }

    @ApiOperation("답글 등록")
    @PostMapping("{boardNo}/replies")
    public ApiResponseMessage setBoardReply(
        @ApiParam(value = "boardNo", name = "boardNo", required = true) @PathVariable Long boardNo,
        final @RequestBody @Valid BoardDTO boardDTO
    ) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "답글 등록이 완료되었습니다.",
            null
        );
        result.setParams(boardDTO);

        if (!boardService.setBoardReply(boardNo, boardDTO)) {
            result.setResult(ResponseResult.FAIL);
            result.setMessage("게시글 등록에 실패하였습니다.");
        }
        return result;
    }

}
