package com.example.bkjeon.util.file;

import com.example.bkjeon.model.file.ToExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ExcelUtil {

    private ExcelUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Excel 파일 다운로드 (2007 이후 버전 xlsx 파일)
     * @author Bong Keun - Jeon
     * @version 1.0
     */
    public static <T> void convertToExcel(
        List<T> list,
        Class<T> clazz,
        HttpServletResponse response
    ) {
        try {
            SXSSFWorkbook workBook = new SXSSFWorkbook();

            response.setStatus(HttpStatus.OK.value());
            response.setContentType("Application/octet-stream; charset=utf8");
            response.setHeader("Content-Disposition",
                    "filename=\"" + System.currentTimeMillis() + ".xlsx\"");
            OutputStream os = response.getOutputStream();

            // 시트 생성
            SXSSFSheet sheet = workBook.createSheet("data");

            T first = clazz.cast(list.get(0));

            // 헤더 생성
            int rowIndex = 0;
            int cellIndex = 0;
            Row headerRow = sheet.createRow(rowIndex);
            Cell headerCell;
            CellStyle headerCellStyle = workBook.createCellStyle();
            headerCellStyle.setWrapText(true);

            for (Field field : first.getClass().getDeclaredFields()) {
                ToExcel annotation = field.getAnnotation(ToExcel.class);
                if (Objects.nonNull(annotation)) {
                    headerCell = headerRow.createCell(cellIndex);
                    headerCell.setCellValue(annotation.value());
                    headerCell.setCellStyle(headerCellStyle);

                    sheet.setColumnWidth(0,5 * 256);
                    sheet.setColumnWidth(1,40 * 256);
                    sheet.setColumnWidth(2,13 * 256);
                    sheet.setColumnWidth(3,9 * 256);
                    sheet.setColumnWidth(4,9 * 256);
                    sheet.setColumnWidth(5,9 * 256);
                    sheet.setColumnWidth(6,15 * 256);
                    sheet.setColumnWidth(7,15 * 256);
                    sheet.setColumnWidth(8,15 * 256);
                    sheet.setColumnWidth(9,15 * 256);
                    sheet.setColumnWidth(10,15 * 256);
                    sheet.setColumnWidth(11,15 * 256);
                    sheet.setColumnWidth(12,13 * 256);
                    sheet.setColumnWidth(13,10 * 256);
                    cellIndex += 1;
                }
            }

            Row bodyRow;
            Cell bodyCell;
            CellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setWrapText(false);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            for (T t : list) {
                T obj = clazz.cast(t);
                cellIndex = 0;
                rowIndex += 1;
                bodyRow = sheet.createRow(rowIndex);
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    ToExcel annotation = field.getAnnotation(ToExcel.class);
                    if (Objects.nonNull(annotation)) {
                        String name = field.getName();
                        Method getter = new PropertyDescriptor(name, clazz).getReadMethod();
                        bodyCell = bodyRow.createCell(cellIndex);
                        bodyCell.setCellValue(
                                Optional.ofNullable(getter.invoke(t)).orElse("-").toString());
                        bodyCell.setCellStyle(cellStyle);
                        cellIndex += 1;
                    }
                }
            }

            workBook.write(os);
            workBook.close();
        } catch (IllegalAccessException iae) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> convertToExcel IllegalAccessException Error!: {}", iae);
        } catch (IntrospectionException ie) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> convertToExcel IntrospectionException Error!: {}", ie);
        } catch (InvocationTargetException ite) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> convertToExcel InvocationTargetException Error!: {}", ite);
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> convertToExcel Error !! : {}", e);
        }
    }

    /**
     * Excel 파일 다운로드 (97-2003 버전인 xls 파일)
     * @author Bong Keun - Jeon
     * @version 2.0
     */
    public static void xlsDownLoad(HttpServletResponse response) {
        try {
            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet("asset");
            Row row;
            Cell cell;
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
            log.error(">>>>>>>>>>>>>>>>>>>>>>> excelDownLoad: {}", e);
        }
    }

    /**
     * Excel 파일 업로드 (97-2003 버전인 xls 파일)
     * @author Bong Keun - Jeon
     * @version 2.0
     */
    public static void xlsUpLoad(MultipartFile uploadFile) {
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

                log.info("No: {}", (int) Double.parseDouble(row.getCell(0).toString()));
                log.info("Title: {}", Optional.ofNullable(row.getCell(1)).map(String::valueOf).orElse(""));
                log.info("Writer: {}", Optional.ofNullable(row.getCell(2)).map(String::valueOf).orElse(""));
                log.info("RegDate: {}", Optional.ofNullable(row.getCell(3)).map(String::valueOf).orElse(LocalDateTime.now().toString()));
            }
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>>>>>>>>>>>> excelUpLoad: {}", e);
        }
    }

}
