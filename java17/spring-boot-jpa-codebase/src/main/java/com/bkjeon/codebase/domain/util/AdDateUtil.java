package com.bkjeon.codebase.domain.util;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class AdDateUtil {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AdDateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * String To DateTime
     * @param dateStr 변환할 날짜 문자열
     * @return 변환된 LocalDateTime 객체 (또는 null)
     */
    public static LocalDateTime stringToDateTime(String dateStr) {
        return dateStr != null ? LocalDateTime.parse(dateStr, FORMATTER) : null;
    }

    /**
     * DateTime To String
     * @param dateTime 변환할 DateTime 객체
     * @return 변환된 날짜 String 객체 (또는 null)
     */
    public static String localDateTimeTostring(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(FORMATTER) : null;
    }

}