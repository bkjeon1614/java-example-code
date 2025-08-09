package com.bkjeon.codebase.adapter.in.web.v1.controller;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdParticipationRequest;
import com.bkjeon.codebase.application.command.CreateAdParticipationCommand;
import com.bkjeon.codebase.application.command.ReadAdParticipationCommand;
import com.bkjeon.codebase.application.port.in.CreateAdParticipationUseCase;
import com.bkjeon.codebase.application.port.in.ReadAdParticipationUseCase;
import com.bkjeon.codebase.domain.model.AdParticipation;
import com.bkjeon.codebase.domain.util.AdDateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit")
@WebMvcTest(AdParticipationController.class)
class AdParticipationControllerUnitTest {

    private static final String REQUEST_URL_PREFIX = "/v1/adParticipations";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAdParticipationUseCase createAdParticipationUseCase;

    @MockBean
    private ReadAdParticipationUseCase readAdParticipationUseCase;

    @Test
    @DisplayName("광고 참여 API 테스트")
    void createAdParticipationTest() throws Exception {
        // given
        long userId = 1;
        long adId = 3;
        LocalDateTime participationTime = LocalDateTime.now();
        long rewardAmount = 300;

        AdParticipation mockResponse = AdParticipation.builder()
            .userId(userId)
            .adId(adId)
            .participationTime(participationTime)
            .rewardAmount(rewardAmount)
            .build();

        CreateAdParticipationRequest request = CreateAdParticipationRequest.builder()
            .userId(userId)
            .adId(adId)
            .build();

        // when
        when(createAdParticipationUseCase.createAdParticipation(any(CreateAdParticipationCommand.class)))
            .thenReturn(mockResponse);

        // then
        mockMvc.perform(post(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.userId").value(userId))
            .andExpect(jsonPath("$.data.adId").value(adId))
            .andExpect(jsonPath("$.data.participationTime")
                .value(AdDateUtil.localDateTimeTostring(participationTime)))
            .andExpect(jsonPath("$.data.rewardAmount").value(300))
            .andDo(print());
    }

    @Test
    @DisplayName("광고 참여 이력 조회 API 테스트")
    void getAdParticipationListTest() throws Exception {
        // given
        String participationTime = "2025-03-01 00:06:00";
        long userId = 1;
        long adId = 4;
        String adName = "가을맞이 할인";
        long rewardAmount = 250;
        String startDate = "2025-03-01 00:00:19";
        String endDate = "2025-03-05 00:00:19";
        int page = 1;
        int size = 50;

        List<AdParticipation> mockResponse = List.of(
            AdParticipation.builder()
                .participationTime(AdDateUtil.stringToDateTime(participationTime))
                .userId(userId)
                .adId(adId)
                .adName(adName)
                .rewardAmount(rewardAmount)
                .build()
        );

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId", String.valueOf(userId));
        params.add("startDate", startDate);
        params.add("endDate", endDate);
        params.add("page", String.valueOf(page));
        params.add("size", String.valueOf(size));

        // when
        when(readAdParticipationUseCase.findAdParticipationList(any(ReadAdParticipationCommand.class)))
            .thenReturn(mockResponse);

        // then
        mockMvc.perform(get(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].participationTime").value(participationTime))
            .andExpect(jsonPath("$.data[0].userId").value(userId))
            .andExpect(jsonPath("$.data[0].adId").value(adId))
            .andExpect(jsonPath("$.data[0].adName").value(adName))
            .andExpect(jsonPath("$.data[0].rewardAmount").value(rewardAmount))
            .andDo(print());
    }

}
