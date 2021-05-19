package com.example.bkjeon.base.services.api.v1.log;

import com.example.bkjeon.dto.common.log.CommonESLogSaveRequestDTO;
import com.example.bkjeon.enums.ResponseResult;
import com.example.bkjeon.model.ApiResponseMessage;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ApiResponseMessage setLog(
        @RequestBody @Valid final CommonESLogSaveRequestDTO commonESLogSaveRequestDTO,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> list = bindingResult.getAllErrors();
            for (ObjectError e : list) {
                return new ApiResponseMessage(
                    ResponseResult.FAIL,
                    e.getDefaultMessage(),
                    null
                );
            }
        }

        return commonLogService.setESLog(commonESLogSaveRequestDTO);
    }

}