package com.example.bkjeon.entity.board;

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

}
