package com.example.bkjeon.dto.board;

import com.example.bkjeon.entity.board.Board;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDTO {

    @Schema(description = "게시물 번호")
    private Long boardNo;

    @Schema(description = "게시물 그룹 번호")
    private Long groupNo;

    @Schema(description = "게시물 정렬 번호")
    private Integer sortSeq;

    @Schema(description = "게시물 뎁스 번호")
    private Integer boardLvl;

    @Schema(description = "게시물 제목")
    private String boardTitle;

    @Schema(description = "게시물 내용")
    private String boardContents;

    @Schema(description = "게시물 등록자")
    private String sysRegrId;

    @Schema(description = "게시물 등록일자")
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
