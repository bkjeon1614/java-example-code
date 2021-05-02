package com.example.bkjeon.mapper.board;

import com.example.bkjeon.entity.board.Board;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BoardMapper {

    void insertBoard(Board board);
    void insertBoardReply(Board board);
    List<Board> selectBoardList(@Param("size") Integer size, @Param("offset") Integer offset);
    int selectBoardListCnt();
    Board selectBoard(@Param("boardNo") Long boardNo);
    Long selectBoardGroupCnt(@Param("groupNo") Long groupNo);
    void updateBoardGroupSort(@Param("groupNo") Long groupNo);
    void updateBoardReplyGroupSort(@Param("groupNo") Long groupNo, @Param("sortSeq") Integer sortSeq);
    void updateBoard(Board board);

}
