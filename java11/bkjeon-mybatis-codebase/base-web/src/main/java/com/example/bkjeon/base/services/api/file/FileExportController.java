package com.example.bkjeon.base.services.api.file;

import com.example.bkjeon.entity.file.FileExportXlsxExample;
import com.example.bkjeon.util.file.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("v1/file")
public class FileExportController {

    @ApiOperation("xlsx Export")
    @GetMapping
    public void fileExportXlsx(HttpServletResponse response) {
        List<FileExportXlsxExample> fileExportXlsxExampleList = new ArrayList<>();
        FileExportXlsxExample fileExportXlsxExample1 = FileExportXlsxExample.builder()
                .productId("1")
                .productName("TEST NAME1")
                .build();
        FileExportXlsxExample fileExportXlsxExample2 = FileExportXlsxExample.builder()
                .productId("2")
                .productName("TEST NAME2")
                .build();
        fileExportXlsxExampleList.add(fileExportXlsxExample1);
        fileExportXlsxExampleList.add(fileExportXlsxExample2);

        try {
            if (!CollectionUtils.isEmpty(fileExportXlsxExampleList)) {
                ExcelUtil.convertToExcel(
                    fileExportXlsxExampleList,
                    FileExportXlsxExample.class,
                    response
                );
            }
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>> fileExportXlsx Error !!: {}", e);
        }
    }

}
