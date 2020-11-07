package com.example.bkjeon.feature.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private Long boardNo;
    private Long groupNo;
    private Integer sortSeq;
    private Integer boardLvl;
    private String boardTitle;
    private String boardContents;

    private String sysRegrId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sysRegDtime;

    private String sysModrId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sysModDtime;

    /*
    @Builder
    public Board(
        Integer sortSeq,
        Integer boardLvl,
        String boardTitle,
        String boardContents,
        String sysRegrId,
        LocalDateTime sysRegDtime,
        String sysModrId,
        LocalDateTime sysModDtime
    ) {
        this.sortSeq = sortSeq;
        this.boardLvl = boardLvl;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.sysRegrId = sysRegrId;
        this.sysRegDtime = sysRegDtime;
        this.sysModrId = sysModrId;
        this.sysModDtime = sysModDtime;
    }

    @Builder
    public Board(
        Long groupNo,
        Integer sortSeq,
        Integer boardLvl,
        String boardTitle,
        String boardContents,
        String sysRegrId,
        LocalDateTime sysRegDtime,
        String sysModrId,
        LocalDateTime sysModDtime
    ) {
        this.groupNo = groupNo;
        this.sortSeq = sortSeq;
        this.boardLvl = boardLvl;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.sysRegrId = sysRegrId;
        this.sysRegDtime = sysRegDtime;
        this.sysModrId = sysModrId;
        this.sysModDtime = sysModDtime;
    }

    @Builder
    public Board(
        Long boardNo,
        String boardTitle,
        String boardContents,
        String sysModrId
    ) {
        this.boardNo = boardNo;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.sysModrId = sysModrId;
    }
     */

}
