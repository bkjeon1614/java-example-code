package com.example.bkjeon.base.services.api.v1.validation;

import com.example.bkjeon.feature.validation.PostValidDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/validation")
public class ValidationController {

    @ApiOperation("@Valid를 사용한 밸리데이션 체크")
    @PostMapping("postValidCheck")
    public ResponseEntity<String> postValidCheck(@Valid @RequestBody PostValidDTO postValidDTO) {
        return ResponseEntity.ok().body("PostValidDTO 객체 검증 성공");
    }

}
