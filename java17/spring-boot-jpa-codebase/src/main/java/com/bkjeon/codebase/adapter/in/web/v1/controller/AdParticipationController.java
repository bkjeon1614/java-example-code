package com.bkjeon.codebase.adapter.in.web.v1.controller;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdParticipationRequest;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.ReadAdParticipationRequest;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.CreateAdParticipationResponse;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.ReadAdParticipationResponse;
import com.bkjeon.codebase.adapter.in.web.v1.dto.common.reponse.ApiResponse;
import com.bkjeon.codebase.application.command.CreateAdParticipationCommand;
import com.bkjeon.codebase.application.command.ReadAdParticipationCommand;
import com.bkjeon.codebase.application.port.in.CreateAdParticipationUseCase;
import com.bkjeon.codebase.application.port.in.ReadAdParticipationUseCase;
import com.bkjeon.codebase.domain.mapper.AdParticipationMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매일모으기", description = "광고참여 관련 API")
@RestController
@RequestMapping("/v1/adParticipations")
@RequiredArgsConstructor
public class AdParticipationController {

    private final CreateAdParticipationUseCase createAdParticipationUseCase;
    private final ReadAdParticipationUseCase readAdParticipationUseCase;

    @Operation(summary = "광고 참여 API",
        description = "유저가 광고에 참여할 경우 호출되는 API 입니다. 참여 이력을 저장하고 포인트를 적립합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateAdParticipationResponse>> createAdParticipation(
        @Valid @RequestBody CreateAdParticipationRequest request) {
        return ResponseEntity.ok(
            ApiResponse.res(
                AdParticipationMapper.INSTANCE.toCreateAdParticipationResponse(
                    createAdParticipationUseCase.createAdParticipation(CreateAdParticipationCommand.of(request)))));
    }

    @Operation(summary = "광고 참여 이력 조회 API", description = "유저가 광고에 참여한 이력을 조회하는 API")
    @Parameter(name = "userId", description = "유저 ID", required = true)
    @Parameter(name = "startDate", description = "광고 참여 시작일 (yyyy-MM-dd HH:mm:ss)", required = true)
    @Parameter(name = "endDate", description = "광고 참여 종료일 (yyyy-MM-dd HH:mm:ss)", required = true)
    @Parameter(name = "page", description = "page", required = true)
    @Parameter(name = "size", description = "size", required = true)
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadAdParticipationResponse>>> getAdParticipationList(
        @Valid ReadAdParticipationRequest request) {
        return ResponseEntity.ok(
            ApiResponse.res(
                AdParticipationMapper.INSTANCE.toReadAdParticipationResponseList(
                    readAdParticipationUseCase.findAdParticipationList(ReadAdParticipationCommand.of(request)))));
    }

}
