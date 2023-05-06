package com.example.bkjeon.base.api.v1.validation;

import com.example.bkjeon.dto.validation.PostEnumCustomValidDTO;
import com.example.bkjeon.dto.validation.PostValidDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class ValidationControllerTest {

    private final static String VERSION_NAME = "v1";
    private final static String BASE_URI_PATH = "/" + VERSION_NAME + "/validation";

    // Test URI List
    private final static String VALID_CHECK_URI = BASE_URI_PATH + "/" + "postValidCheck";
    private final static String ENUM_VALID_CHK_URI = BASE_URI_PATH + "/" + "postEnumCustomValidCheck";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /*
    @Test
    @DisplayName("[POST] " + VALID_CHECK_URI)
    void postValidCheck() throws Exception {
        PostValidDTO postValidDTO = new PostValidDTO();
        String jsonString = objectMapper.writeValueAsString(postValidDTO);

        mockMvc.perform(post(VALID_CHECK_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));
    }

    @Test
    @DisplayName("[POST] " + ENUM_VALID_CHK_URI)
    void postEnumCustomValidCheck() throws Exception {
        PostEnumCustomValidDTO postEnumCustomValidDTO = PostEnumCustomValidDTO.builder()
            .exampleName("예제명")
            .exampleEnumAssertTrueType("create")
            .build();
        String userDtoJsonString = objectMapper.writeValueAsString(postEnumCustomValidDTO);

        mockMvc.perform(post(ENUM_VALID_CHK_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userDtoJsonString))
                .andExpect(status().isOk())
                .andDo(print());
    }
     */

}
