package com.example.bkjeon.base.utils.file;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

public class ExcelUtil {

    private ExcelUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Excel 파일 만들기 (97-2003 버전인 xls 파일)
     * @author Bong Keun - Jeon
     * @version 1.0
     */
    public static void excelDownLoad(HttpServletResponse response) {
        try {
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("asset");
            Row row = null;
            Cell cell = null;
            int rowNo = 0;

            // 헤더 스타일 관련
            CellStyle headStyle = wb.createCellStyle();

            // 헤더 항목 경계선 지정
            headStyle.setBorderTop(BorderStyle.THIN);
            headStyle.setBorderBottom(BorderStyle.THIN);
            headStyle.setBorderLeft(BorderStyle.THIN);
            headStyle.setBorderRight(BorderStyle.THIN);

            // 헤더 항목 배경색 지정
            headStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
            headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 데이터 정렬
            headStyle.setAlignment(HorizontalAlignment.CENTER);

            // 데이터용 경계 스타일 테두리만 지정
            CellStyle bodyStyle = wb.createCellStyle();
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);

            // 헤더 세팅
            row = sheet.createRow(rowNo++);

            cell = row.createCell(0);
            cell.setCellStyle(headStyle);
            cell.setCellValue("No");

            cell = row.createCell(1);
            cell.setCellStyle(headStyle);
            cell.setCellValue("Title");

            cell = row.createCell(2);
            cell.setCellStyle(headStyle);
            cell.setCellValue("Writer");

            cell = row.createCell(3);
            cell.setCellStyle(headStyle);
            cell.setCellValue("RegDate");

            // 데이터 세팅 (Loop)
            row = sheet.createRow(rowNo++);

            cell = row.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(1);

            cell = row.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue("제목");

            cell = row.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue("전봉근");

            cell = row.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue("2019-12-21");

            // 컨텐츠 타입과 파일명 지정
            response.setContentType("ms-vnd/excel");
            response.setHeader("Content-Disposition", "attachment;filename=example.xls");

            // 엑셀 출력
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            //
        }
    }

    public static void excelUpLoad(MultipartFile uploadFile) {
        try {
            String[] fileNameArr = uploadFile.getOriginalFilename().split("\\.");
            if (!fileNameArr[1].equals(".xls")) {
                // ms office 2007 이전 버전 또는 올바른 확장자 파일을 업로드 하세요.
            }

            Workbook workbook = new HSSFWorkbook(uploadFile.getInputStream());
            for (Row row: workbook.getSheetAt(0)) {
                if (row.getRowNum() < 1) {
                    continue;
                }

                if (row.getCell(0) == null) {
                    break;
                }

                System.out.println("No: " + (int) Double.parseDouble(row.getCell(0).toString()));
                System.out.println("Title: " + Optional.ofNullable(row.getCell(1)).map(String::valueOf).orElse(""));
                System.out.println("Writer: " + Optional.ofNullable(row.getCell(2)).map(String::valueOf).orElse(""));
                System.out.println("RegDate: " + Optional.ofNullable(row.getCell(3)).map(String::valueOf).orElse(LocalDateTime.now().toString()));
            }
        } catch (Exception e) {
            //
        }
    }

}
