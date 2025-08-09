package com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

@Schema(description = "광고 등록 API 요청 모델")
@Builder
public record CreateAdRequest(
    @Schema(description = "광고명")
    @NotEmpty(message = "광고명이 누락되었습니다.")
    String name,

    @Schema(description = "광고 참여시 적립 액수")
    @NotNull(message = "광고 참여시 적립 액수가 누락되었습니다.")
    @PositiveOrZero(message = "광고 참여시 적립 액수가 0보다 큰 값을 입력하여 주시길 바랍니다.")
    Long rewardAmount,

    @Schema(description = "광고 참여 가능 횟수")
    @NotNull(message = "광고 참여 가능 횟수가 누락되었습니다.")
    @PositiveOrZero(message = "광고 참여 가능 횟수가 0보다 큰 값을 입력하여 주시길 바랍니다.")
    Long participationLimit,

    @Schema(description = "광고 문구")
    @NotEmpty(message = "광고문구가 누락되었습니다.")
    String description,

    @Schema(description = "광고 이미지 URL")
    @NotEmpty(message = "광고 이미지 url 이 누락되었습니다.")
    String imageUrl,

    @Schema(description = "광고 노출 시작일 (yyyy-MM-dd HH:mm:ss)", defaultValue = "2025-03-01 00:00:00")
    @NotEmpty(message = "광고 노출 시작일이 누락되었습니다.")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
        message = "광고 노출 시작일 형식이 올바르지 않습니다. (yyyy-MM-dd HH:mm:ss)"
    )
    String startDate,

    @Schema(description = "광고 노출 종료일 (yyyy-MM-dd HH:mm:ss)", defaultValue = "2025-03-20 00:00:00")
    @NotEmpty(message = "광고 노출 종료일이 누락되었습니다.")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
        message = "광고 노출 종료일 형식이 올바르지 않습니다. (yyyy-MM-dd HH:mm:ss)"
    )
    String endDate
) {}