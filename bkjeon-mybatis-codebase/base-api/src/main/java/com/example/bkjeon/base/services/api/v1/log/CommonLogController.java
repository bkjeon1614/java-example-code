package com.example.bkjeon.base.services.api.v1.log;

import com.example.bkjeon.dto.common.log.CommonESLogSaveRequestDTO;
import com.example.bkjeon.model.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v1/logs")
@RestController
@RequiredArgsConstructor
public class CommonLogController {

    private final CommonLogService commonLogService;

    @ApiOperation("로그 저장 API")
    @PostMapping
    public ResponseEntity setLog(
        @RequestBody @Valid final CommonESLogSaveRequestDTO commonESLogSaveRequestDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError e : list) {
                return new ResponseEntity(
                    ApiResponse.res(
                        HttpStatus.BAD_REQUEST.value(),
                        e.getDefaultMessage(),
                        commonESLogSaveRequestDTO,
                        null
                    ),
                    HttpStatus.BAD_REQUEST
                );
            }
        }

        return commonLogService.setESLog(commonESLogSaveRequestDTO);
    }

}