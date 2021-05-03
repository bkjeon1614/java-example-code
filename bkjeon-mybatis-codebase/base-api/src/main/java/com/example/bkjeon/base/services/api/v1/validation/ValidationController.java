package com.example.bkjeon.base.services.api.v1.validation;

import com.example.bkjeon.dto.validation.PostValidDTO;
import com.example.bkjeon.base.validation.PostValidator;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.ApiResponseMessage;
import com.example.bkjeon.util.validation.ValidationUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "v1/validation", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class ValidationController {

    private final PostValidator postValidator;

    @ApiOperation("@Valid를 사용한 밸리데이션 체크")
    @PostMapping("postValidCheck")
    public ApiResponseMessage postValidCheck(final @Valid @RequestBody PostValidDTO postValidDTO) {
        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "PostValidDTO 객체 검증 성공",
            postValidDTO
        );
    }

    @ApiOperation("Reflection을 사용한 밸리데이션 체크")
    @PostMapping("postRefValidCheck")
    public ApiResponseMessage postRefValidCheck(final @RequestBody PostValidDTO postValidDTO, BindingResult errors) {
        postValidator.validate(postValidDTO, errors);
        if (errors.hasErrors()) {
            return new ApiResponseMessage(ResponseResult.FAIL, ValidationUtil.getFirstErrorMessage(errors), postValidDTO);
        }

        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "PostValidDTO 객체 검증 성공",
            postValidDTO
        );
    }

}
