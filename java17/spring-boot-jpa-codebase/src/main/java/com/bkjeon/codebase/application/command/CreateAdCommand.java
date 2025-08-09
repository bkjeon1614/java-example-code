package com.bkjeon.codebase.application.command;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdRequest;
import com.bkjeon.codebase.domain.util.AdDateUtil;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAdCommand {

    private String name;
    private long rewardAmount;
    private long participationLimit;
    private String description;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static CreateAdCommand of(CreateAdRequest request) {
        return CreateAdCommand.builder()
            .name(request.name())
            .rewardAmount(request.rewardAmount())
            .participationLimit(request.participationLimit())
            .description(request.description())
            .imageUrl(request.imageUrl())
            .startDate(AdDateUtil.stringToDateTime(request.startDate()))
            .endDate(AdDateUtil.stringToDateTime(request.endDate()))
            .build();
    }

}
