package com.example.bkjeon.feature.board;

import java.util.List;

public interface BoardMapper {

    void insertBoard(Board board);
    void insertBoardReply(Board board);
    List<Board> selectBoardList(Integer size, Integer offset);
    Board selectBoard(Long boardNo);
    Long selectBoardGroupCnt(Long groupNo);
    void updateBoardGroupSort(Long groupNo);
    void updateBoardReplyGroupSort(Long groupNo, Long sortSeq);

}