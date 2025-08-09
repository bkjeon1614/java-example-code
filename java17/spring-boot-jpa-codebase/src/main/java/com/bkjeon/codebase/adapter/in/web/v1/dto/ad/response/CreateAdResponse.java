package com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "광고 등록 API 응답 모델")
@Builder
public record CreateAdResponse(
    @Schema(description = "광고 ID")
    Long id,
    @Schema(description = "광고명")
    String name,
    @Schema(description = "광고 참여시 적립 액수")
    Long rewardAmount,
    @Schema(description = "광고 참여 가능 횟수")
    Long participationLimit,
    @Schema(description = "광고 문구")
    String description,
    @Schema(description = "광고 이미지 URL")
    String imageUrl,
    @Schema(description = "광고 노출 시작일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime startDate,
    @Schema(description = "광고 노출 종료일")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime endDate
) {}