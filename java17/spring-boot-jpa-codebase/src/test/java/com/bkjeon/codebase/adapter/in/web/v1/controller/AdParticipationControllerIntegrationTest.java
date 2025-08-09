package com.bkjeon.codebase.adapter.in.web.v1.controller;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.request.CreateAdParticipationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("integration")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AdParticipationControllerIntegrationTest {

    private static final String REQUEST_URL_PREFIX = "/v1/adParticipations";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("광고 참여 API 테스트")
    void createAdParticipationTest() throws Exception {
        // given
        long userId = 1L;
        long adId = 3L;
        String participationTimePrefix = LocalDateTime.now().toLocalDate().toString();

        CreateAdParticipationRequest request = CreateAdParticipationRequest.builder()
            .userId(userId)
            .adId(adId)
            .build();

        // when & then
        mockMvc.perform(post(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.userId").value(userId))
            .andExpect(jsonPath("$.data.adId").value(adId))
            .andExpect(jsonPath("$.data.participationTime").value(containsString(participationTimePrefix)))
            .andExpect(jsonPath("$.data.rewardAmount").value(300))
            .andDo(print());
    }

    @Test
    @DisplayName("광고 참여 이력 조회 API 테스트")
    void getAdParticipationListTest() throws Exception {
        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("userId", "1");
        params.add("startDate", "2025-03-01 00:00:19");
        params.add("endDate", "2025-03-05 00:00:19");
        params.add("page", "1");
        params.add("size", "50");

        // when & then
        mockMvc.perform(get(REQUEST_URL_PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .params(params))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data[0].participationTime").value("2025-03-01 00:06:00"))
            .andExpect(jsonPath("$.data[0].userId").value(1))
            .andExpect(jsonPath("$.data[0].adId").value(4))
            .andExpect(jsonPath("$.data[0].adName").value("가을맞이 할인"))
            .andExpect(jsonPath("$.data[0].rewardAmount").value(250))
            .andDo(print());
    }


}
