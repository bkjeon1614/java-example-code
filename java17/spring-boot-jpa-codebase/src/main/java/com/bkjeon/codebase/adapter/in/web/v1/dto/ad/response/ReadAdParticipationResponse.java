package com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "광고 참여 조회 API 응답 모델")
public record ReadAdParticipationResponse(
    @Schema(description = "광고 참여 시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime participationTime,
    @Schema(description = "참여한 유저 ID")
    Long userId,
    @Schema(description = "참여한 광고 ID")
    Long adId,
    @Schema(description = "광고명")
    String adName,
    @Schema(description = "적립액수")
    Long rewardAmount
) {}