package com.example.bkjeon.base.services.api.v1.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bkjeon.dto.board.BoardRequestDTO;
import com.example.bkjeon.dto.board.BoardResponseDTO;
import com.example.bkjeon.entity.board.Board;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.mapper.board.BoardMapper;
import com.example.bkjeon.model.response.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    // 게시글 리스트 조회
    @Transactional(readOnly = true)
    public ApiResponse getBoardList(Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<BoardResponseDTO> boardList = boardMapper.selectBoardList(size, offset).stream()
            .map(BoardResponseDTO::new)
            .collect(Collectors.toList());

        // TODO: Response Data 모델 추가 필요
        // int totalCnt = boardMapper.selectBoardListCnt();

        return ApiResponse.<List<BoardResponseDTO>>builder()
            .statusCode(HttpStatus.OK.value())
            .responseMessage(ResponseResult.SUCCESS.getText())
            .data(boardList)
            .build();
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public ApiResponse<BoardResponseDTO> getBoard(Long boardNo) {
        Board board = boardMapper.selectBoard(boardNo);

        return ApiResponse.<BoardResponseDTO>builder()
            .statusCode(HttpStatus.OK.value())
            .responseMessage(ResponseResult.SUCCESS.getText())
            .data(new BoardResponseDTO(board))
            .build();
    }

    // 메인 게시물 등록
    @Transactional
    public ApiResponse setBoard(BoardRequestDTO requestDTO) {
        boardMapper.insertBoard(requestDTO.toSaveBoardEntity());
        return ApiResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .responseMessage(ResponseResult.SUCCESS.getText())
            .param(requestDTO)
            .build();
    }

    // 서브 게시물 등록
    @Transactional
    public ApiResponse setBoardReply(Long boardNo, BoardRequestDTO requestDTO) {
        Integer sortSeq = null;
        Integer boardLvl = null;

        // CASE 1: 원글의 GROUP_NO, SORT_SEQ, BOARD_LVL 기준으로 답글의 저장될 데이터를 계산한다.
        Board board = boardMapper.selectBoard(boardNo);
        if (board == null) {
            // TODO: 비즈니스 익셉션 변경 필요
            return ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("원글이 존재하지 않습니다. boardNo: " + boardNo)
                .param(requestDTO)
                .build();
        }

        if (board.getBoardLvl() == 1 && board.getSortSeq() == 0) {
            // 원글 그룹에 기존에 답글이 있는지 유무 체크
            Long boardGroupCnt = boardMapper.selectBoardGroupCnt(board.getGroupNo());
            if (boardGroupCnt == 1) {
                // CASE 1: 원글의 답글 등록 (원글 그룹으로 조회했을시 개수가 1개일 경우)

                /**
                 * 원글의 GROUP_NO, SORT_SEQ, BOARD_LVL 기준으로 답글의 저장될 데이터를 계산한다.
                 * GROUP_NO: 원글의 GROUP_NO 값, SORT_SEQ: 원글 (SORT_SEQ + 1), BOARD_LVL: 원글 (BOARD_LVL + 1)
                 */
                sortSeq = board.getSortSeq() + 1;
                boardLvl = board.getBoardLvl() + 1;
            } else if (boardGroupCnt > 1) {
                // CASE 2: 원글에 답글을 한개 더 작성하는 경우 (원글 그룹으로 조회했을시 개수가 2개 이상일 경우)

                /**
                 * GROUP_NO가 같으면서 새로운 답글이 상위로 위치하기 위해서
                 * 기존 원글의 답글들의 SORT_SEQ를 전부 +1 한다.
                 */
                boardMapper.updateBoardGroupSort(board.getGroupNo());

                /**
                 *  GROUP_NO: 원글의 GROUP_NO 값, SORT_SEQ: 원글 (SORT_SEQ + 1), BOARD_LVL: 원글 (BOARD_LVL + 1)
                 */
                sortSeq = board.getSortSeq() + 1;
                boardLvl = board.getBoardLvl() + 1;
            }
        } else if (board.getBoardLvl() > 1) {
            // CASE 3: 답글에 답글을 작성하는 경우

            /**
             * GROUP_NO가 같으면서 새로운 답글이 상위로 위치하기 위해서
             * 기존의 답글들의 SORT_SEQ를 전부 +1 한다.
             */
            boardMapper.updateBoardReplyGroupSort(board.getGroupNo(), board.getSortSeq());

            /**
             * GROUP_NO: 원글의 GROUP_NO 값, SORT_SEQ: 원글 (SORT_SEQ + 1), BOARD_LVL: 원글 (BOARD_LVL + 1)
             */
            sortSeq = board.getSortSeq() + 1;
            boardLvl = board.getBoardLvl() + 1;
        }

        // 답글 저장
        boardMapper.insertBoardReply(requestDTO.toSaveBoardReplyEntity(board.getGroupNo(), sortSeq, boardLvl));

        return ApiResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .responseMessage(ResponseResult.SUCCESS.getText())
            .param(requestDTO)
            .build();
    }

    // 게시글 수정
    @Transactional
    public ApiResponse putBoard(Long boardNo, BoardRequestDTO requestDTO) {
        boardMapper.updateBoard(requestDTO.toUpdateEntity(boardNo));
        return ApiResponse.builder()
            .statusCode(HttpStatus.OK.value())
            .responseMessage(ResponseResult.SUCCESS.getText())
            .param(requestDTO)
            .build();
    }

}
