package com.example.bkjeon.base.utils.file;

import com.example.bkjeon.base.common.enums.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * File Utils
 * @author Bong Keun - Jeon
 * @version 1.0
 */
@Slf4j
public class FileUtil {

    private FileUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void main(String[] args) {
        fileCopy("./file/copy/example1.txt", "./file/copy/example2.txt");
    }

    public static void fileCopy(String origPath, String newPath) {
        try {
            // 원본 파일을 읽는다
            FileInputStream fileInputStream = new FileInputStream(origPath);

            // 원하는 경로로 파일을 복사한다
            FileOutputStream fileOutputStream = new FileOutputStream(newPath);

            int data = 0;
            while ((data = fileInputStream.read()) != -1) {
                fileOutputStream.write(data);
            }
            fileInputStream.close();
            fileOutputStream.close();

            // 복사가 완료되면 원본파일 삭제할 경우에 해당 주석 해제
            // File delOrigFile = new File(origPath);
            // delOrigFile.delete();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("fileCopy ERROR {}", e.getMessage());
            }
        }
    }

}
