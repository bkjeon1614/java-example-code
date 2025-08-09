package com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Schema(description = "광고 참여 API 요청 모델")
@Builder
public record CreateAdParticipationRequest(
    @Schema(description = "유저 ID")
    @NotNull(message = "유저 ID 가 누락되었습니다.")
    @Positive(message = "잘못된 유저 ID 입니다.")
    Long userId,
    @Schema(description = "광고 ID")
    @NotNull(message = "광고 ID 가 누락되었습니다.")
    @Positive(message = "잘못된 광고 ID 입니다.")
    Long adId
) {}