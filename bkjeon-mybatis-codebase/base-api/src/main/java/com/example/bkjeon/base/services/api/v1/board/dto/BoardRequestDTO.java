package com.example.bkjeon.base.services.api.v1.board.dto;

import com.example.bkjeon.feature.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardRequestDTO {

    @NotEmpty(message = "제목을 입력하여 주시길 바랍니다.")
    private String boardTitle;
    private String boardContents;

    @NotEmpty(message = "userId 값을 입력하여 주시길 바랍니다.")
    private String userId;

    @Builder
    public BoardRequestDTO(String boardTitle, String boardContents, String userId) {
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.userId = userId;
    }

    public Board toSaveBoardEntity() {
        return Board.builder()
                .sortSeq(0)
                .boardLvl(1)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .sysRegrId(userId)
                .sysRegDtime(LocalDateTime.now())
                .sysModrId(userId)
                .sysModDtime(LocalDateTime.now())
                .build();
    }

    public Board toSaveBoardReplyEntity(Long groupNo, Integer sortSeq, Integer boardLvl) {
        return Board.builder()
                .groupNo(groupNo)
                .sortSeq(sortSeq)
                .boardLvl(boardLvl)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .sysRegrId(userId)
                .sysRegDtime(LocalDateTime.now())
                .sysModrId(userId)
                .sysModDtime(LocalDateTime.now())
                .build();
    }

    public Board toUpdateEntity(Long boardNo) {
        return Board.builder()
                .boardNo(boardNo)
                .boardTitle(boardTitle)
                .boardContents(boardContents)
                .sysModrId(userId)
                .build();
    }

}
