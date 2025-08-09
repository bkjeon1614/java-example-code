package com.bkjeon.codebase.adapter.in.web.v1.controller;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdRequest;
import com.bkjeon.codebase.application.command.CreateAdCommand;
import com.bkjeon.codebase.application.command.ReadAdCommand;
import com.bkjeon.codebase.application.port.in.CreateAdUseCase;
import com.bkjeon.codebase.application.port.in.ReadAdUseCase;
import com.bkjeon.codebase.domain.model.Ad;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("unit")
@WebMvcTest(AdController.class)
class AdControllerUnitTest {

    private static final String REQUEST_URL_PREFIX = "/v1/ads";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateAdUseCase createAdUseCase;

    @MockBean
    private ReadAdUseCase readAdUseCase;

    @Test
    @DisplayName("광고 등록 API 테스트")
    void createAdTest() throws Exception {
        // given
        String name = "하이퍼 세일 이벤트";
        long rewardAmount = 100;
        long participationLimit = 5;
        String description = "특별 할인 혜택을 지금 만나보세요!";
        String imageUrl = "https://example.com/ad1.jpg";
        String startDate = "2025-03-01 00:00:00";
        String endDate = "2025-03-31 23:59:59";

        Ad mockResponse = Ad.builder()
            .name(name)
            .rewardAmount(rewardAmount)
            .participationLimit(participationLimit)
            .description(description)
            .imageUrl(imageUrl)
            .startDate(AdDateUtil.stringToDateTime(startDate))
            .endDate(AdDateUtil.stringToDateTime(endDate))
            .build();

        CreateAdRequest request = CreateAdRequest.builder()
            .name(name)
            .rewardAmount(rewardAmount)
            .participationLimit(participationLimit)
            .description(description)
            .imageUrl(imageUrl)
            .startDate(startDate)
            .endDate(endDate)
            .build();

        // when
        when(createAdUseCase.createAd(any(CreateAdCommand.class))).thenReturn(mockResponse);

        // then
        mockMvc.perform(post(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.name").value(name))
            .andExpect(jsonPath("$.data.rewardAmount").value(rewardAmount))
            .andExpect(jsonPath("$.data.participationLimit").value(participationLimit))
            .andExpect(jsonPath("$.data.description").value(description))
            .andExpect(jsonPath("$.data.imageUrl").value(imageUrl))
            .andExpect(jsonPath("$.data.startDate").value(startDate))
            .andExpect(jsonPath("$.data.endDate").value(endDate))
            .andDo(print());
    }

    @Test
    @DisplayName("광고 조회 API 테스트")
    void getAdListTest() throws Exception {
        // given
        long id = 7;
        String name = "크리스마스 기념 이벤트";
        String description = "산타의 선물! 특별한 할인!";
        String imageUrl = "https://example.com/ad7.jpg";
        long rewardAmount = 400;

        List<Ad> mockResponse = List.of(Ad.builder().id(id).name(name).description(description).imageUrl(imageUrl).rewardAmount(rewardAmount).build());

        // when
        when(readAdUseCase.findTopAvailableAdvertisementsByReward(any(ReadAdCommand.class))).thenReturn(mockResponse);

        // then
        mockMvc.perform(get(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].id").value(id))
            .andExpect(jsonPath("$.data[0].name").value(name))
            .andExpect(jsonPath("$.data[0].description").value(description))
            .andExpect(jsonPath("$.data[0].imageUrl").value(imageUrl))
            .andExpect(jsonPath("$.data[0].rewardAmount").value(rewardAmount))
            .andDo(print());
    }

}
