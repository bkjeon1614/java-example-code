package com.example.bkjeon.base.services.api.v1.file;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.example.bkjeon.entity.file.FileExportXlsxExample;
import com.example.bkjeon.util.file.ExcelUtil;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/file")
public class FileExportController {

    @ApiOperation("파일 업로드(파일 유무만 체크)")
    @PostMapping
    public void fileUpload(@RequestPart("uploadFile") MultipartFile uploadFile) {
        log.info("=============" + uploadFile.getOriginalFilename());
        log.info(uploadFile.toString());
    }

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
