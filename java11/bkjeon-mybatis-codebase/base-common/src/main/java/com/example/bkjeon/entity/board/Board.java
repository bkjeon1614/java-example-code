package com.example.bkjeon.entity.board;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

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

    @Schema(description = "게시물 수정자")
    private String sysModrId;

    @Schema(description = "게시물 수정일자")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime sysModDtime;

}
