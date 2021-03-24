package com.example.bkjeon.base.services.api.v1.board;

import com.example.bkjeon.base.services.api.v1.board.dto.BoardRequestDTO;
import com.example.bkjeon.base.services.api.v1.board.dto.BoardResponseDTO;
import com.example.bkjeon.common.enums.ResponseResult;
import com.example.bkjeon.common.model.ApiResponseMessage;
import com.example.bkjeon.feature.board.Board;
import com.example.bkjeon.feature.board.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    // 게시글 리스트 조회
    @Transactional(readOnly = true)
    public ApiResponseMessage getBoardList(Integer page, Integer size) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "게시글 조회가 완료되었습니다."
        );

        try {
            Integer offset = (page - 1) * size;
            List<BoardResponseDTO> boardList = boardMapper.selectBoardList(size, offset).stream()
                    .map(BoardResponseDTO::new)
                    .collect(Collectors.toList());
            result.setTotalCnt(boardList.size());
            result.setContents(boardList);
        } catch (Exception e) {
            log.error("getBoardList ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
            return result;
        }

        return result;
    }

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public ApiResponseMessage getBoard(Long boardNo) {
        ApiResponseMessage result = new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "게시글 상세 조회가 완료되었습니다."
        );

        try {
            Board board = boardMapper.selectBoard(boardNo);
            result.setContents(new BoardResponseDTO(board));
        } catch (Exception e) {
            log.error("getBoard ERROR {}", e.getMessage());
            result.setResult(ResponseResult.FAIL);
            result.setMessage(e.getMessage());
            return result;
        }

        return result;
    }

    // 메인 게시물 등록
    @Transactional
    public boolean setBoard(BoardRequestDTO requestDTO) {
        try {
            boardMapper.insertBoard(requestDTO.toSaveBoardEntity());
        } catch (Exception e) {
            log.error("setBoard ERROR {}", e.getMessage());
            return false;
        }

        return true;
    }

    // 서브 게시물 등록
    @Transactional
    public boolean setBoardReply(Long boardNo, BoardRequestDTO requestDTO) {
        try {
            Integer sortSeq = null;
            Integer boardLvl = null;

            // CASE 1: 원글의 GROUP_NO, SORT_SEQ, BOARD_LVL 기준으로 답글의 저장될 데이터를 계산한다.
            Board board = boardMapper.selectBoard(boardNo);
            if (board == null) {
                log.warn("원글이 존재하지 않습니다. boardNo: " + board.getBoardNo());
                return false;
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
        } catch (Exception e) {
            log.error("setBoardReply ERROR {}", e.getMessage());
            return false;
        }

        return true;
    }

    // 게시글 수정
    @Transactional
    public boolean putBoard(Long boardNo, BoardRequestDTO requestDTO) {
        try {
            boardMapper.updateBoard(requestDTO.toUpdateEntity(boardNo));
        } catch (Exception e) {
            log.error("putBoard ERROR {}", e.getMessage());
            return false;
        }

        return true;
    }

}
