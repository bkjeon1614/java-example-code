package com.example.bkjeon.base.services.api.v1.board.dto;

import com.example.bkjeon.entity.board.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDTO {

    private Long boardNo;
    private Long groupNo;
    private Integer sortSeq;
    private Integer boardLvl;
    private String boardTitle;
    private String boardContents;

    private String sysRegrId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sysRegDtime;

    public BoardResponseDTO(Board entity) {
        this.boardNo = entity.getBoardNo();
        this.groupNo = entity.getGroupNo();
        this.sortSeq = entity.getSortSeq();
        this.boardLvl = entity.getBoardLvl();
        this.boardTitle = entity.getBoardTitle();
        this.boardContents = entity.getBoardContents();
        this.sysRegrId = entity.getSysRegrId();
        this.sysRegDtime = entity.getSysRegDtime();
    }

}
