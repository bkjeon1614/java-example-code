package com.bkjeon.codebase.adapter.in.web.v1.controller;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdRequest;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.CreateAdResponse;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.ReadAdResponse;
import com.bkjeon.codebase.adapter.in.web.v1.dto.common.reponse.ApiResponse;
import com.bkjeon.codebase.application.command.CreateAdCommand;
import com.bkjeon.codebase.application.command.ReadAdCommand;
import com.bkjeon.codebase.application.port.in.CreateAdUseCase;
import com.bkjeon.codebase.application.port.in.ReadAdUseCase;
import com.bkjeon.codebase.domain.mapper.AdMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "매일모으기", description = "광고 관련 API")
@RestController
@RequestMapping("/v1/ads")
@RequiredArgsConstructor
public class AdController {

    private final CreateAdUseCase createAdUseCase;
    private final ReadAdUseCase readAdUseCase;

    @Operation(summary = "광고 등록 API", description = "광고를 시스템에 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponse<CreateAdResponse>> createAd(
        @Valid @RequestBody CreateAdRequest request) {
        return ResponseEntity.ok(
            ApiResponse.res(
                AdMapper.INSTANCE.toAdResponse(createAdUseCase.createAd(CreateAdCommand.of(request)))));
    }

    @Operation(summary = "광고 조회 API", description = "유저에게 노출될 광고 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ReadAdResponse>>> getAdList() {
        return ResponseEntity.ok(
            ApiResponse.res(
                AdMapper.INSTANCE.toReadAdResponseList(
                    readAdUseCase.findTopAvailableAdvertisementsByReward(ReadAdCommand.from(10)))));
    }

}
