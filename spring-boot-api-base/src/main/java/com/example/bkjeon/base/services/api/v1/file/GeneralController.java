package com.example.bkjeon.base.services.api.v1.file;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("v1/file")
public class GeneralController {

    @ApiOperation("파일 업로드(MultiPartFile)")
    @PostMapping("general")
    public void generalFileUpLoad(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartHttpServletRequest.getFile("upLoadFile");

        System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getBytes());

    }

}
