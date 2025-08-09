package com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "광고 조회 API 응답 모델")
public record ReadAdResponse(
    @Schema(description = "광고 ID")
    Long id,
    @Schema(description = "광고명")
    String name,
    @Schema(description = "광고 문구")
    String description,
    @Schema(description = "광고 이미지 URL")
    String imageUrl,
    @Schema(description = "광고 참여시 적립 액수")
    Long rewardAmount
) {}