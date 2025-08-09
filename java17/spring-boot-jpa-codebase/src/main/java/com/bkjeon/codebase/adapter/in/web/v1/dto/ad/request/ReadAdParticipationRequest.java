package com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Hidden
@Schema(description = "광고 참여 조회 API 요청 모델")
@Builder
public record ReadAdParticipationRequest(
    @Schema(description = "유저 ID")
    @NotNull(message = "유저 ID 가 누락되었습니다.")
    @Positive(message = "잘못된 유저 ID 입니다.")
    Long userId,
    @Schema(description = "광고 참여 시작일 (yyyy-MM-dd HH:mm:ss)")
    @NotEmpty(message = "광고 참여 시작일이 누락되었습니다.")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
        message = "광고 참여 시작일 형식이 올바르지 않습니다. (yyyy-MM-dd HH:mm:ss)"
    )
    String startDate,

    @Schema(description = "광고 참여 종료일 (yyyy-MM-dd HH:mm:ss)")
    @NotEmpty(message = "광고 참여 종료일이 누락되었습니다.")
    @Pattern(
        regexp = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
        message = "광고 참여 종료일 형식이 올바르지 않습니다. (yyyy-MM-dd HH:mm:ss)"
    )
    String endDate,
    @Schema(description = "page")
    @NotNull(message = "page 가 누락되었습니다.")
    @Positive(message = "0보다 큰 page 를 입력하여 주시길 바랍니다.")
    Integer page,
    @Schema(description = "size (1 ~ 50 사이의 값)")
    @NotNull(message = "size 가 누락되었습니다.")
    @Positive(message = "1 ~ 50 이하의 size 를 입력하여 주시길 바랍니다.")
    @Max(value = 50, message = "50 이하의 size 를 입력하여 주시길 바랍니다.")
    Integer size
) {}