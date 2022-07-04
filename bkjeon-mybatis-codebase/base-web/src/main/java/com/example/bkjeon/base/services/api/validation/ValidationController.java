package com.example.bkjeon.base.services.api.validation;

import com.example.bkjeon.dto.validation.PostEnumAssertTrueValidDTO;
import com.example.bkjeon.dto.validation.PostEnumCustomValidDTO;
import com.example.bkjeon.dto.validation.PostValidDTO;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/validation")
public class ValidationController {

    @ApiOperation("@Valid를 사용한 밸리데이션 체크")
    @PostMapping("postValidCheck")
    public ResponseEntity postValidCheck(@Valid @RequestBody PostValidDTO postValidDTO) {
        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                postValidDTO,
                null
            ),
            HttpStatus.OK
        );
    }

    @ApiOperation("Reflection을 사용한 밸리데이션 체크")
    @PostMapping("postRefValidCheck")
    public ResponseEntity postRefValidCheck(
        @RequestBody PostValidDTO postValidDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorListor = bindingResult.getAllErrors();
            for (ObjectError e: errorListor) {
                return new ResponseEntity(
                    ApiResponse.res(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getDefaultMessage(),
                        postValidDTO,
                        null
                    ),
                    HttpStatus.BAD_REQUEST
                );
            }
        }

        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                postValidDTO,
                null
            ),
            HttpStatus.OK
        );
    }

    @ApiOperation("POST 밸리데이션 체크(Enum Type 체크 포함 -> @AssertTrue 활용)")
    @PostMapping("postEnumAssertTrueValidCheck")
    public ResponseEntity postEnumAssertTrueValidCheck(
        @RequestBody @Valid PostEnumAssertTrueValidDTO postEnumAssertTrueValidDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorListor = bindingResult.getAllErrors();
            for (ObjectError e: errorListor) {
                return new ResponseEntity(
                    ApiResponse.res(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getDefaultMessage(),
                        postEnumAssertTrueValidDTO,
                        null
                    ),
                    HttpStatus.BAD_REQUEST
                );
            }
        }

        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                postEnumAssertTrueValidDTO,
                null
            ),
            HttpStatus.OK
        );
    }

    @ApiOperation("POST 밸리데이션 체크(Enum Type 체크 포함 -> EnumType 관련 Annotation 및 Interface 활용)")
    @PostMapping("postEnumCustomValidCheck")
    public ResponseEntity postEnumCustomValidCheck(
        @RequestBody @Valid PostEnumCustomValidDTO postEnumCustomValidDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError e: list) {
                return new ResponseEntity(
                    ApiResponse.res(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getDefaultMessage(),
                        postEnumCustomValidDTO,
                        null
                    ),
                    HttpStatus.BAD_REQUEST
                );
            }
        }

        return new ResponseEntity(
            ApiResponse.res(
                HttpStatus.OK.value(),
                ResponseResult.SUCCESS.getText(),
                postEnumCustomValidDTO,
                null
            ),
            HttpStatus.OK
        );
    }

}
