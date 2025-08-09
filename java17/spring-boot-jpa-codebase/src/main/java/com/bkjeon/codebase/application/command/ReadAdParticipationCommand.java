package com.bkjeon.codebase.application.command;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.ReadAdParticipationRequest;
import com.bkjeon.codebase.domain.util.AdDateUtil;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadAdParticipationCommand {

    private Long userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer offset;
    private Integer size;

    public static ReadAdParticipationCommand of(ReadAdParticipationRequest request) {
        return ReadAdParticipationCommand.builder()
            .userId(request.userId())
            .startDate(AdDateUtil.stringToDateTime(request.startDate()))
            .endDate(AdDateUtil.stringToDateTime(request.endDate()))
            .offset((request.page() - 1) * request.size())
            .size(request.size())
            .build();
    }

}
