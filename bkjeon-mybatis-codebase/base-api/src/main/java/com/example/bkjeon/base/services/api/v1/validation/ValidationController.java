package com.example.bkjeon.base.services.api.v1.validation;

import com.example.bkjeon.dto.validation.PostEnumAssertTrueValidDTO;
import com.example.bkjeon.dto.validation.PostEnumCustomValidDTO;
import com.example.bkjeon.dto.validation.PostValidDTO;
import com.example.bkjeon.base.validation.PostValidator;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.ApiResponseMessage;
import com.example.bkjeon.util.validation.ValidationUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation("POST 밸리데이션 체크(Enum Type 체크 포함 -> @AssertTrue 활용)")
    @PostMapping("postEnumAssertTrueValidCheck")
    public ApiResponseMessage postEnumAssertTrueValidCheck(
        @RequestBody @Valid final PostEnumAssertTrueValidDTO postEnumAssertTrueValidDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorListor = bindingResult.getAllErrors();
            for (ObjectError e: errorListor) {
                return new ApiResponseMessage(
                    ResponseResult.FAIL,
                    e.getDefaultMessage(),
                    postEnumAssertTrueValidDTO
                );
            }
        }

        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "postEnumValidDTO 객체 검증 성공",
            postEnumAssertTrueValidDTO
        );
    }

    @ApiOperation("POST 밸리데이션 체크(Enum Type 체크 포함 -> EnumType 관련 Annotation 및 Interface 활용)")
    @PostMapping("postEnumCustomValidCheck")
    public ApiResponseMessage postEnumCustomValidCheck(
        @RequestBody @Valid final PostEnumCustomValidDTO postEnumCustomValidDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError e: list) {
                return new ApiResponseMessage(
                    ResponseResult.FAIL,
                    e.getDefaultMessage(),
                    postEnumCustomValidDTO
                );
            }
        }

        return new ApiResponseMessage(
            ResponseResult.SUCCESS,
            "postEnumCustomValidDTO 객체 검증 성공",
            postEnumCustomValidDTO
        );
    }

}
