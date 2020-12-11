package com.example.bkjeon.base.services.api.v1.validation;

import com.example.bkjeon.base.services.api.v1.validation.dto.PostValidDTO;
import com.example.bkjeon.common.enums.ResponseResult;
import com.example.bkjeon.common.model.ApiResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/validation", produces = "application/json; charset=utf8")
public class ValidationController {

    @ApiOperation("@Valid를 사용한 밸리데이션 체크")
    @PostMapping("postValidCheck")
    public ApiResponseMessage postValidCheck(final @Valid @RequestBody PostValidDTO postValidDTO) {
        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "PostValidDTO 객체 검증 성공",
            postValidDTO
        );
    }

}
