package com.example.bkjeon.feature.board;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO {

    @NotEmpty(message = "제목을 입력하여 주시길 바랍니다.")
    private String boardTitle;
    private String boardContents;

    @NotEmpty(message = "userId 값을 입력하여 주시길 바랍니다.")
    private String userId;

}
