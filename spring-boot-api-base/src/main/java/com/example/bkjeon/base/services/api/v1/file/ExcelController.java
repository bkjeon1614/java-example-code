package com.example.bkjeon.base.services.api.v1.file;

import com.example.bkjeon.base.utils.file.ExcelUtil;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("v1/file")
public class ExcelController {

    @GetMapping("excelDownLoad")
    public void exampleExcelDownLoad(HttpServletResponse response) {
        ExcelUtil.excelDownLoad(response);
    }

    @PostMapping("excelUpLoad")
    public void exampleExcelUpLoad(
        @ApiParam(value = "Excel 파일", name = "uploadFile", required = true) @RequestPart MultipartFile uploadFile
    ) {
        ExcelUtil.excelUpLoad(uploadFile);
    }

}