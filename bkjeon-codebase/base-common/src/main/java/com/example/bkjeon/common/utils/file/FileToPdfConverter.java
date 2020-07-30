package com.example.bkjeon.common.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @title Apache OpenOffice
 * @author Bkjeon
 * @version v1.0
 * @history
 *     |- Version History
 *        - v1.0: PDF Parser 생성을 위한 JODConverter 및 OpenOffice 적용 완료(pdf)
 * @description
 *  1. Gradle Dependency 추가
 *     - [build.gradle]
 *       dependencies {
 *           ...
 *               // 윈도우용
 *               compile group: 'org.jodconverter', name: 'jodconverter-core', version: '4.0.0-RELEASE'
 *               // 리눅스용
 *               compile group: 'com.artofsolving', name: 'jodconverter', version: '2.2.0'
 *           ...
 *       }
 *  2. Open Office 설치 (v4.0.0)
 *     - Windows
 *       - .exe 파일 설치하면 완료
 *     - Linux(redhat)
 *       - wget https://sourceforge.net/projects/openofficeorg.mirror/files/4.0.0/binaries/ko/Apache_OpenOffice_4.0.0_Linux_x86-64_install-rpm_ko.tar.gz/download -O Apache_OpenOffice_4.0.0_Linux_x86-64_install-rpm_ko.tar.gz
 *       - sudo yum remove openoffice* libreoffice*
 *       - tar -xvf Apache_OpenOffice_4.0.0*
 *       - cd ko
 *       - sudo vi /etc/yum.conf
 *         ```
 *              [yum.conf]
 *              ## Add exclude row
 *              [main]
 *              exclude=openoffice.org-ure* libreoffice-ure*
 *         ```
 *       - sudo rpm -Uvh RPMS/*.rpm RPMS/desktop-integration/openoffice4.0-redhat-*.rpm
 *       - soffice -headless -accept="socket,host=127.0.0.1,port=8100;urp;tcpNoDelay=1" -nofirststartwizard --convert-to &
 *  3. 문제해결
 *     - 만약 한글이 ??로 깨져서 보일 때 -> font issue일 가능성이 농후
 *       - http://cdn.naver.com/naver/NanumFont/fontfiles/NanumFont_TTF_ALL.zip 다운
 *         [이동해야할 file List]
 *         - NanumBrush.ttf
 *         - NanumGothic.ttf
 *         - NanumGothicBold.ttf
 *         - NanumGothicExtraBold.ttf
 *         - NanumMyeongjo.ttf
 *         - NanumMyeongjoBold.ttf
 *         - NanumMyeongjoExtraBold.ttf
 *         - NanumPen.ttf
 *       - openoffice 설치 경로인 /opt/openoffice4/share/fonts/truetype 에 .ttf 폰트파일 이동
 *       - sudo fc-cache -r 폰트적용
 *     - 위의 내용이 적용되었어도 같은 현상이 발생하는 경우
 *       - 위의 font 이슈를 다시 적용하고 fc-cache 명령을 다시 실행
 *       - soffice 프로세스가 죽어있는 경우 한글이 깨질 수 있음 -> 다시 실행
 */
@Slf4j
public class FileToPdfConverter {

    private FileToPdfConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean setFileToPdfParse(String origFilePath, String parseFilePath) throws OfficeException, IOException {
        boolean result = true;

        if (origFilePath.isEmpty() || parseFilePath.isEmpty()) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> setFileToPdfParse Parameter Empty ERROR !!");
            result = false;
        }

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> encoding: " + System.getProperty("file.encoding"));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>> origFilePath: " + getFileEncoding(origFilePath));

        File origFile = new File(origFilePath);
        File parseFile = new File(parseFilePath);

        DefaultOfficeManagerBuilder builder = new DefaultOfficeManagerBuilder();
        builder.setPortNumber(8100);
        builder.setOfficeHome(new File("/opt/openoffice4"));
        builder.setTaskExecutionTimeout(600000L);   // 10 minutes
        builder.setMaxTasksPerProcess(2);
        OfficeManager officeManager = builder.build();

        try {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> [PDF Parser Util] Start !!");
            officeManager.start();
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            converter.convert(origFile, parseFile);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> [PDF Parser Util] End !!");
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(">>>>>>>>>>>>>>>>>>>>>>>>> setFileToPdfParse ERROR !! {}", e.getMessage());
            }
        } finally {
            officeManager.stop();
            origFile.delete();
        }

        return result;
    }

    // Windows Test
    public static void main(String[] args) throws Exception {
        OfficeManager officeManager = new DefaultOfficeManagerBuilder().build();

        String origPath = "./file/temp/test.xls";
        String newPath = "./file/temp/test.pdf";
        File origFile = new File(origPath);
        File newFile = new File(newPath);

        try {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> encoding: " + System.getProperty("file.encoding"));
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> origFilePath: " + getFileEncoding(origPath));

            officeManager.start();
            OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
            converter.convert(origFile, newFile);

            officeManager.stop();
            origFile.delete();
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> origFile Delete !!");
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(">>>>>>>>>>>>>>>>>>>>>>>>> FileToPdfParser ERROR !! {}", e.getMessage());
            }
        }
    }

    // encoding return
    private static String getFileEncoding(String filePath) {
        String fileEncodingStr = "EUC-KR";

        try {
            FileInputStream fis = new FileInputStream(filePath);
            byte[] BOM = new byte[4];
            fis.read(BOM, 0, 4);

            if ((BOM[0] & 0xFF) == 0xEF && (BOM[1] & 0xFF) == 0xBB && (BOM[2] & 0xFF) == 0xBF) {
                fileEncodingStr = "UTF-8";
            } else if ((BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF) {
                fileEncodingStr = "UTF-16BE";
            } else if ((BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE) {
                fileEncodingStr = "UTF-16LE";
            } else if (
                    (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00
                            && (BOM[0] & 0xFF) == 0xFE && (BOM[1] & 0xFF) == 0xFF
            ) {
                fileEncodingStr = "UTF-32BE";
            } else if (
                    (BOM[0] & 0xFF) == 0xFF && (BOM[1] & 0xFF) == 0xFE
                            && (BOM[0] & 0xFF) == 0x00 && (BOM[1] & 0xFF) == 0x00
            ) {
                fileEncodingStr = "UTF-32LE";
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("fileEncodingChk ERROR !! {}", e.getMessage());
            }
        }

        return fileEncodingStr;
    }

}