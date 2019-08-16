package com.bkjeon.example.services.api.v1.example;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.bkjeon.example.common.enums.ResponseResult;
import com.bkjeon.example.common.model.ApiResponseMessage;
import com.bkjeon.example.feature.example.ExampleParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("v1/examples")
public class ExampleController {

    private ExampleService exampleService;

    @ApiOperation("Example Get API")
    @GetMapping
    public ApiResponseMessage getCallMethod(
        @ApiParam(value = "page 번호를 설정할 수 있으며 설정 값은 1-N까지 입니다.", name = "page", defaultValue = "1", required = true) @RequestParam int page,
        @ApiParam(value = "페이지 별 레코드 갯수를 설정 할 수 있습니다.", name = "size", defaultValue = "10", required = true) @RequestParam int size,
        ExampleParam param
    ) {
        ApiResponseMessage result = new ApiResponseMessage(ResponseResult.SUCCESS, null, null);
        result.setTotalCnt(exampleService.selectExampleListTotalCnt(param));
        result.setContents(exampleService.selectExampleList(page, size, param));
        result.setParams(param);

        return result;
    }

    @ApiOperation("Example Info Get API")
    @GetMapping("{id}")
    public ApiResponseMessage getDetailCallMethod(
        @PathVariable("id") Integer id
    ) {
        ApiResponseMessage result = new ApiResponseMessage(ResponseResult.SUCCESS, null, null);
        result.setContents(exampleService.selectExampleInfo(id));

        return result;
    }

    @ApiOperation("Example Post API")
    @PostMapping
    public ApiResponseMessage setCallMethod(
        @ApiParam(
            value ="name: 이름 \n\n" +
                "age: 나이 "
        )
        @RequestBody ExampleParam param
    ) {
        exampleService.insertExample(param);
        return new ApiResponseMessage(ResponseResult.SUCCESS, "등록이 완료되었습니다.", null);
    }

    @ApiOperation("Example Put API")
    @PutMapping("{id}")
    public ApiResponseMessage putCallMethod(
        @PathVariable("id") Integer id,
        @ApiParam(
            value ="name: 이름 \n\n" +
                "age: 나이 "
        )
        @RequestBody ExampleParam param
    ) {
        exampleService.updateExample(id, param);
        return new ApiResponseMessage(ResponseResult.SUCCESS, "변경이 완료되었습니다.", null);
    }

    @ApiOperation("Example Patch API")
    @PatchMapping("{id}/name")
    public ApiResponseMessage patchCallMethod(
        @PathVariable("id") Integer id,
        @ApiParam(value = "example name", name = "name", required = true) @RequestParam String name
    ) {
        exampleService.updateExampleName(id, name);
        return new ApiResponseMessage(ResponseResult.SUCCESS, "name 변경이 완료되었습니다.", null);
    }

    @ApiOperation("Example Del API")
    @DeleteMapping("{id}")
    public ApiResponseMessage delCallMethod(
        @PathVariable("id") Integer id
    ) {
        exampleService.deleteExample(id);
        return new ApiResponseMessage(ResponseResult.SUCCESS, "name 삭제가 완료되었습니다.", null);
    }

}