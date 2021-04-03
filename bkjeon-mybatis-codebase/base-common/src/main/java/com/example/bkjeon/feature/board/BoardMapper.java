package com.example.bkjeon.feature.board;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {

    void insertBoard(Board board);
    void insertBoardReply(Board board);
    List<Board> selectBoardList(@Param("size") Integer size, @Param("offset") Integer offset);
    Board selectBoard(@Param("boardNo") Long boardNo);
    Long selectBoardGroupCnt(@Param("groupNo") Long groupNo);
    void updateBoardGroupSort(@Param("groupNo") Long groupNo);
    void updateBoardReplyGroupSort(@Param("groupNo") Long groupNo, @Param("sortSeq") Integer sortSeq);
    void updateBoard(Board board);

}
