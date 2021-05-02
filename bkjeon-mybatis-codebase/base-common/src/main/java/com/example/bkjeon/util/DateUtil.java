package com.example.bkjeon.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public final class DateUtil {
    public static final Integer SECONDS_IN_DAY = 86400;
    public static final Integer SECONDS_IN_HOUR = 3600;
    public static final Integer SECONDS_IN_MINUTE = 60;

    public static final Long MILLISECONDS_IN_DAY = 86400000L;
    public static final Long MILLISECONDS_IN_HOUR = 3600000L;
    public static final Long MILLISECONDS_IN_MINUTE = 60000L;

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 오늘 날짜를 format형태의 스트링값으로 구한다.
     *
     * @return format형태의 스트링값
     */
    public static String date() {
        return date(Formats.yyyyMMdd, mktime());
    }

    public static String date(String format) {
        return date(format, mktime());
    }

    public static String date(String format, Locale locale) {
        return date(format, mktime(), locale);
    }

    public static String date(String format, Date date) {
        return date(format, date.getTime());
    }

    public static String date(String format, Date date, Locale locale) {
        return date == null ? "" : date(format, date.getTime(), locale);
    }

    public static synchronized String date(String format, Long timeStamp) { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat.format(timeStamp);
    }

    public static synchronized String date(String format, Long timeStamp, Locale locale) { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, locale);
        return simpleDateFormat.format(timeStamp);
    }

    public static synchronized long mktime(Integer sec, Integer min, Integer hour, Integer mon, Integer date, Integer year) { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Calendar calendar = simpleDateFormat.getCalendar();
        calendar.set(year, mon - 1, date, hour, min, sec);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long mktime(int mon, int date, int year) {
        return mktime(0, 0, 0, mon, date, year);
    }

    public static long mktime(Date date) {
        return date.getTime();
    }

    /**
     * 오늘 날짜의 timestamp값을 구한다.
     *
     * @return logn형 timestamp값
     */
    public static long mktime() {
        return System.currentTimeMillis();
    }

    /**
     * 입력된 날짜 string과 format이 일치하는가 판별한다. 내부적으로 입력된 format에 맞추어 입력된 날짜를 이용해 다시 한번 date를 뽑아내어 둘이 동일한지를 판별한다.
     * (만약, 범위를 넘어서는 시간, 날짜등이 입력되어 다시 만드는 과정에서 날짜가 변경된다면, 둘은 다르다고 판별된다.)
     * <br/>
     * 다음의 경우 false가 반환되면, 그 외에는 true이다.
     * <ol>
     * <li>format 자체가 올바르지 못한 경우</li>
     * <li>format으로 입력된 날짜를 parsing하고 Date를 만든 후, 그 값을 이용하여 다시 날짜(String)을 생성한 다음 그것을 입력된 날짜(String)과 비교하여 다른 경우</li>
     * </ol>
     *
     * @param sDate
     * @param sFormat
     * @return
     */
    public static synchronized boolean isFormatMatched(String sDate, String sFormat) { // NOPMD
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat, Locale.getDefault());
            Date newDate = simpleDateFormat.parse(sDate);

            if (newDate == null) {
                return false;
            }

            String newDateStr = DateUtil.date(sFormat, newDate);

            if (!sDate.equals(newDateStr)) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static synchronized long mktime(String sDate, String sFormat) { // NOPMD
        if (StringUtils.isEmpty(sDate) || StringUtils.isEmpty(sFormat)) {
            return 0L;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sFormat, Locale.getDefault());

        try {
            simpleDateFormat.parseObject(sDate);
        } catch (ParseException e) {
            log.warn(e.getMessage(), e);
        }

        Calendar calendar = simpleDateFormat.getCalendar();
        return calendar.getTimeInMillis();
    }

    public static Map<String, Integer> timeStamp2Time(Long timeStamp) {
        Map<String, Integer> result = new HashMap<String, Integer>();
        Integer timeInInt = timeStamp.intValue() / 1000;

        Integer days = timeInInt / SECONDS_IN_DAY;
        timeInInt = timeInInt % SECONDS_IN_DAY;
        result.put("days", days);

        Integer hours = timeInInt / SECONDS_IN_HOUR;
        timeInInt = timeInInt % SECONDS_IN_HOUR;
        result.put("hours", hours);

        Integer minutes = timeInInt / SECONDS_IN_MINUTE;
        timeInInt = timeInInt % SECONDS_IN_MINUTE;
        result.put("minutes", minutes);

        Integer seconds = timeInInt;
        result.put("seconds", seconds);

        return result;
    }

    /**
     * 입력된 timestamp를 기준으로 nDay이후의 timestamp를 구한다.
     *
     * @param timeStamp
     * @param nDays
     * @return
     */
    public static long getAfterNDays(long timeStamp, int nDays) {
        // nDays는 0보다 커야 한다.
        return addTime(timeStamp, Math.abs(nDays), Calendar.DAY_OF_MONTH);
    }

    /**
     * 입력된 timestamp를 기준으로 nMinutes이후의 timestamp를 구한다.
     *
     * @param timeStamp
     * @param nMinutes
     * @return
     */
    public static long getAfterNMinutes(long timeStamp, int nMinutes) {
        // nMinutes는 0보다 커야 한다.
        return addTime(timeStamp, Math.abs(nMinutes), Calendar.MINUTE);
    }

    /**
     * 입력된 timestamp를 기준으로 nDay이전의 timestamp를 구한다.
     *
     * @param timeStamp
     * @param nDays
     * @return
     */
    public static long getBeforeNDays(long timeStamp, int nDays) {
        // nDays는 0보다 작아야 한다.
        return addTime(timeStamp, Math.abs(nDays) * -1, Calendar.DAY_OF_MONTH);
    }

    /**
     * 입력된 timestamp를 기준으로 nHour이후의 timestamp를 구한다.
     *
     * @param timeStamp
     * @param nHour
     * @return
     */
    public static long getAfterNHours(long timeStamp, int nHour) {
        // nHour는 0보다 커야 한다.
        return addTime(timeStamp, Math.abs(nHour), Calendar.HOUR);
    }

    public static long getAfterNHours(int nHours) {
        return getAfterNHours(DateUtil.mktime(), nHours);
    }

    /**
     * 입력된 timestamp를 기준으로 nHour이전의 timestamp를 구한다.
     *
     * @param timeStamp
     * @param nHour
     * @return
     */
    public static long getBeforeNHours(long timeStamp, int nHour) {
        // nHour는 0보다 작아야 한다.
        return addTime(timeStamp, Math.abs(nHour) * -1, Calendar.HOUR);
    }

    public static long getBeforeNHours(int nHours) {
        return getBeforeNHours(DateUtil.mktime(), nHours);
    }

    public static synchronized long addTime(long timeStamp, int count, int size) { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        Calendar calendar = simpleDateFormat.getCalendar();
        calendar.setTimeInMillis(timeStamp);
        calendar.add(size, count);

        return calendar.getTimeInMillis();
    }

    /**
     * 주어진 format으로된 string 날짜의 유효성 체크
     *
     * @param str
     * @param format
     * @return
     */
    public static synchronized boolean checkDate(String str, String format) { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        simpleDateFormat.setLenient(false); // false 로 설정해야 엄밀한 해석을 함.

        try {
            simpleDateFormat.parse(str);
            return true;
        } catch (ParseException e) {
            e.getMessage();
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }

        return false;
    }

    /**
     * @version $Rev$, $Date$
     */
    public static class Formats {
        public static final String YYYY_MM_DD_HH24_MI_SS = "yyyy-MM-dd HH:mm:ss";
        public static final String yyyyMMdd = "yyyyMMdd";
        public static final String yyyyMMddhhmmss = "yyyyMMddHHmmss";
        public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
        public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
        public static final String ymdH = "yyMMddHH";
        public static final String UNIX_TIME = "yyyy. MM. dd. (E) HH.mm.ss z";
        public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    }

    /**
     * 입력일자를 기준으로 특정필드(일, 주, 월, 년)의 값만큼 가감한 날짜를 리턴한다.<br>
     * 필드 정의 <br>
     * 일 : Calendar.DATE <br>
     * 주 : Calendar.WEEK_OF_YEAR <br>
     * 월 : Calendar.MONTH <br>
     * 년 : Calendar.YEAR <br>
     * <p>
     * 사용예 : DateUtils.getAddDate("2006-11-11", Calendar.MONTH, -3) => 2006년 11월 11일을 기준으로 3개월전 일자를 yyyy-MM-dd 형식으로 리턴받는다.
     *
     * @param date   기준일자
     * @param field  Calendar.Date,Calendar.MONTH등
     * @param amount 가감
     * @return date subtract a month
     */
    public static String getAddDate(String date, int field, int amount) {
        return getAddDate(date, field, amount, "yyyy-MM-dd");
    }

    /**
     * 입력일자를 기준으로 특정필드(일, 주, 월, 년)의 값만큼 가감한 날짜를 리턴한다.<br>
     * 필드 정의 <br>
     * 일 : Calendar.DATE <br>
     * 주 : Calendar.WEEK_OF_YEAR <br>
     * 월 : Calendar.MONTH <br>
     * 년 : Calendar.YEAR <br>
     * <p>
     * 사용예 : DateUtils.getAddDate("2006-11-11", Calendar.MONTH, -3, "yyyyMMdd") => 2006년 11월 11일을 기준으로 3개월전 일자를 format 리턴받는다.
     *
     * @param date   기준일자
     * @param field  Calendar.Date,Calendar.MONTH등
     * @param amount 가감
     * @param format 리턴 format
     * @return date subtract a month
     */
    public static synchronized String getAddDate(String date, int field, int amount, String format) { // NOPMD
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(toDate(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        calendar.add(field, amount);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 입력한 yyyy-MM-dd 형식의 문자열을 Date 타입으로 변환한다.
     */
    public static synchronized Date toDate(String strDate) throws Exception { // NOPMD
        String strDateV = strDate;

        if (strDate.length() == 8) {
            strDateV = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return simpleDateFormat.parse(strDateV);
    }

    /**
     * {@link #toDate(String)} 메소드를 실행하지만, checked exception 시그니처를 제거함.
     *
     * @param strDate yyyyMMdd, yyyy-MM-dd 형태 문자열
     * @return {@link Date} 타입으로 변환된 날짜
     * @throws IllegalArgumentException 변환 오류 발생 시
     * @see #toDate(String)
     */
    public static Date toDateQuietly(String strDate) {
        try {
            return toDate(strDate);
        } catch (Exception e) {
            throw new IllegalArgumentException("date conversion error. date=" + strDate, e);
        }
    }

    /**
     * 입력한 format 형식의 문자열을 Date 타입으로 변환한다.
     */
    public static Date toDate(String strDate, String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return simpleDateFormat.parse(strDate);
    }

    /**
     * {@link #toDate(String, String)} 메소드를 실행하지만, checked exception 시그니처를 제거함.
     *
     * @param strDate 날짜 문자열
     * @param format  날짜 포맷
     * @return {@link Date} 타입으로 변환된 날짜
     * @throws IllegalArgumentException 변환 오류 발생 시
     * @see #toDate(String, String)
     */
    public static Date toDateQuietly(String strDate, String format) {
        try {
            return toDate(strDate, format);
        } catch (Exception e) {
            throw new IllegalArgumentException("date conversion error. date=" + strDate + ", format=" + format, e);
        }
    }

    public static String convertFormat(String strDate, String inputFormat, String outputFormat) throws Exception {
        Date date = toDate(strDate, inputFormat);
        return date(outputFormat, date);
    }

    /**
     * 입력한 format 형식으로된 시작일, 종료일, 현재 날짜(시간) 문자열을 가지고 현재 날짜가 주어진 범위에 속하는지 여부를 리턴한다.
     */
    public static boolean checkPeriod(String start, String end, String now, String format) throws Exception {
        Date startDate = toDate(start, format);
        Date endDate = toDate(end, format);
        Date nowDate = toDate(now, format);

        return nowDate.compareTo(startDate) >= 0 && nowDate.compareTo(endDate) <= 0;
    }

    /**
     * yyyyMMdd 형식의 날짜를 Timestamp 로 반환한다.
     *
     * @param dateData yyyyMMdd 형식의 날짜
     * @return Timestamp 형식의 해당 날짜
     */
    public static Timestamp convertToTimestamp(String dateData) {
        if (StringUtils.isBlank(dateData)) {
            return null;
        }

        int dateObjLength = dateData.length();

        String yearString = "2002";
        String monthString = "01";
        String dayString = "01";

        if (dateObjLength >= 4) {
            yearString = dateData.substring(0, 4);
        }
        if (dateObjLength >= 6) {
            monthString = dateData.substring(4, 6);
        }
        if (dateObjLength >= 8) {
            dayString = dateData.substring(6, 8);
        }

        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString) - 1;
        int day = Integer.parseInt(dayString);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * yyyyMMdd 형식의 Timestamp 날짜를 yyyy-MM-dd 형식으로 반환한다.
     *
     * @param dateData Timestamp 형식의 날짜
     * @return String yyyy-MM-dd 형식의 Timestamp 날짜
     */
    public static String convertToString(Timestamp dateData) {
        return convertToString(dateData, "yyyy-MM-dd");
    }

    /**
     * yyyyMMdd 형식의 Timestamp 날짜를 pattern 에 따른 형식으로 반환한다.
     *
     * @param dateData Timestamp 형식의 날짜
     * @param pattern  SimpleDateFormat에 적용할 pattern
     * @return String yyyy-MM-dd 형식의 Timestamp 날짜
     */
    public static String convertToString(Timestamp dateData, String pattern) {
        return convertToString(dateData, pattern, Locale.KOREA);
    }

    /**
     * yyyyMMdd 형식의 Timestamp 날짜를 pattern 과 locale 에 따른 형식으로 반환한다.
     *
     * @param dateData Timestamp 형식의 날짜
     * @param pattern  SimpleDateFormat에 적용할 pattern
     * @param locale   국가별 LOCALE
     * @return String pattern 형식의 Timestamp 날짜
     */
    public static String convertToString(Timestamp dateData, String pattern, Locale locale) {
        if (dateData == null) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        // formatter.applyPattern( pattern );

        return simpleDateFormat.format(dateData);
    }

    /**
     * yyyyMMdd 형식의 날짜를 yyyy-MM-dd 형식으로 반환한다.
     *
     * @param dateData yyyyMMdd 형식의 날짜
     * @return String yyyy-MM-dd 형식의 해당 날짜
     */
    public static String convertFormat(String dateData) {
        if (StringUtils.isEmpty(dateData)) {
            return "";
        }

        return convertFormat(dateData, Formats.DEFAULT_FORMAT);
    }

    /**
     * yyyyMMdd 형식의 날짜를 yyyy-MM-dd 형식으로 반환한다.
     *
     * @param dateData yyyyMMdd 형식의 날짜
     * @param format   SimpleDateFormat에 적용할 pattern
     * @return String pattern 형식의 해당 날짜
     */
    public static String convertFormat(String dateData, String format) {
        if (StringUtils.isEmpty(dateData)) {
            return "";
        }

        return convertToString(convertToTimestamp(dateData), format);
    }

    /**
     * yyyyMMdd 형식의 날짜를 yyyy-MM-dd 형식으로 반환한다.
     *
     * @return Timestamp 현재 Timestamp 값
     */
    public static Timestamp getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 주어진 문자열로 날짜셋팅한 calendar class 구하기
     *
     * @param str yyyymmdd 형식의 날짜문자열
     * @return Calendar-인스턴스
     */
    public static Calendar getCalendar(String str) {
        int yy = Integer.parseInt(str.substring(0, 4));
        int mm = Integer.parseInt(str.substring(4, 6)) - 1;
        int dd = Integer.parseInt(str.substring(6, 8));

        Calendar calendar = Calendar.getInstance();
        calendar.set(yy, mm, dd);

        return calendar;
    }

    /**
     * 주어진 문자열로 날짜셋팅한 calendar class 구하기
     *
     * @param date Date 객체
     * @return Calendar 인스턴스
     */
    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 날짜문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함
     *
     * @param str  yyyyMMdd 형식의 날짜문자열
     * @param days 더할, 혹은 뺄 날수
     * @return yyyyyMMdd 형식의 8자리 날짜
     * @throws ParseException 날짜문자열 형식이 잘못되었을 경우
     */
    public static synchronized String addDays(String str, int days) throws ParseException { // NOPMD
        return addDays(str, days, "yyyyMMdd");
    }

    /**
     * 날짜문자열(format에 따른)에 날짜를 더한(혹은 뺀) 일자를 구함
     *
     * @param str    날짜 문자열
     * @param days   더할, 혹은 뺄 날수
     * @param format 날짜 문자열의 포맷
     * @return format에 따른 날짜문자열
     * @throws ParseException 형식이 잘못되었을 경우
     */
    public static synchronized String addDays(String str, int days, String format) throws ParseException { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date date = simpleDateFormat.parse(str);
        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
        return simpleDateFormat.format(date);
    }

    /**
     * date에 날짜를 더한(혹은 뺀) 일자를 구함
     *
     * @param date Date 객체
     * @param days 더할, 혹은 뺄 날수
     * @return yyyyyMMdd 형식의 8자리 날짜
     * @throws ParseException 형식이 잘못되었을 경우
     */
    public static synchronized String addDays(Date date, int days) throws ParseException { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
        return simpleDateFormat.format(date);
    }

    /**
     * 날짜문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함
     *
     * @param str  yyyyMMdd 형식의 날짜문자열
     * @param days 더할, 혹은 뺄 날수
     * @return yyyyyMMdd 형식의 8자리 날짜
     * @throws ParseException 형식이 잘못되었을 경우
     */
    public static synchronized Date addDays2Date(String str, int days) throws ParseException { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = simpleDateFormat.parse(str);
        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
        return date;
    }

    /**
     * 날짜 문자열(yyyymmdd)에 날짜를 더한(혹은 뺀) 일자를 구함
     *
     * @param date Date 객체
     * @param days 더할, 혹은 뺄 날수
     * @return date
     * @throws ParseException 형식이 잘못되었을 경우
     */
    public static Date addDays2Date(Date date, int days) throws ParseException {
        date.setTime(date.getTime() + days * 1000L * 60L * 60L * 24L);
        return date;
    }

    /**
     * 주어진 날짜가 속한 주의 월요일/일요일 날짜를 구해 스트링배열로 리턴한다
     *
     * @param str  yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월/일요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String[] getBothDayOfWeek(String str, int week) throws ParseException {
        return new String[]{getFirstDayOfWeek(str, week), getLastDayOfWeek(str, week)};
    }

    /**
     * 주어진 날짜가 속한 주의 월요일/일요일 날짜를 구해 스트링배열로 리턴한다
     *
     * @param date yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월/일요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String[] getBothDayOfWeek(Date date, int week) throws ParseException {
        return new String[]{getFirstDayOfWeek(date, week), getLastDayOfWeek(date, week)};
    }

    /**
     * 오늘이 속한 주의 월요일 날짜를 구한다
     *
     * @return 월요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String getFirstDayOfThisWeek() throws ParseException {
        return getFirstDayOfWeek(new Date(), 0);
    }

    /**
     * 주어진 날짜가 속한 주의 월요일 날짜를 구한다
     *
     * @param str  yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String getFirstDayOfWeek(String str, int week) throws ParseException {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0) {
            conStr = str;
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        } else {
            conStr = addDays(str, week * 7);
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }

        int gap = dayOfWeek == 1 ? 6 : dayOfWeek - 2;

        return addDays(conStr, -gap);
    }

    public static String getLastDayOfMonth(String str) throws ParseException {
        String nextMonth = DateUtil.convertFormat(DateUtil.addMonths(str, 1), "yyyyMM01");
        long timestamp = mktime(nextMonth, "yyyyMMdd") - mktime(str, "yyyyMMdd");
        int gap = (int) (timestamp / MILLISECONDS_IN_DAY) - 1;

        return addDays(str, gap);
    }

    /**
     * 주어진 날짜가 속한 주의 월요일 날짜를 구한다
     *
     * @param date Date객체
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String getFirstDayOfWeek(Date date, int week) throws ParseException {
        Date conDate = null;
        int dayOfWeek = 0;

        if (week == 0) {
            conDate = date;
            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
        } else {
            conDate = addDays2Date(date, week * 7);
            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
        }

        int gap = dayOfWeek == 1 ? 6 : dayOfWeek - 2;

        return addDays(conDate, -gap);
    }

    /**
     * 주어진 날짜가 속한 주의 일요일 날짜를 구한다
     *
     * @param str  yyyymmdd 형식의 날짜문자열
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String getLastDayOfWeek(String str, int week) throws ParseException {
        String conStr = null;
        int dayOfWeek = 0;

        if (week == 0) {
            conStr = str;
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        } else {
            conStr = addDays(str, week * 7);
            dayOfWeek = getCalendar(conStr).get(Calendar.DAY_OF_WEEK);
        }

        int gap = dayOfWeek == 1 ? 0 : 8 - dayOfWeek;

        return addDays(conStr, gap);
    }

    /**
     * 주어진 날짜가 속한 주의 일요일 날짜를 구한다
     *
     * @param date Date객체
     * @param week 주어진날짜가 속한 주에서의 차이, 1 : 다음주, -1 : 전주
     * @return 월요일
     * @throws ParseException 문자열파싱시 발생
     */
    public static String getLastDayOfWeek(Date date, int week) throws ParseException {
        Date conDate = null;
        int dayOfWeek = 0;

        if (week == 0) {
            conDate = date;
            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
        } else {
            conDate = addDays2Date(date, week * 7);
            dayOfWeek = getCalendar(conDate).get(Calendar.DAY_OF_WEEK);
        }

        int gap = dayOfWeek == 1 ? 0 : 8 - dayOfWeek;

        return addDays(conDate, gap);
    }

    /**
     * 주어진 날짜가 속한 달의 1일 날짜를 구한다
     *
     * @param str   yyyymmdd 형식의 날짜문자열
     * @param month 주어진날짜가 속한 달에서의 차이, 1:다음달, 0:이번달, -1:저번달
     * @return 1일
     * @throws ParseException 문자열파싱시 발생
     */
    public static synchronized String getFirstDayOfMonth(String str, int month) throws ParseException { // NOPMD
        Calendar calendar = getCalendar(str);
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 주어진 날짜가 속한 달의 마지막 날짜를 구한다
     *
     * @param str   yyyymmdd 형식의 날짜문자열
     * @param month 주어진날짜가 속한 달에서의 차이, 1:다음달, 0:이번달, -1:저번달
     * @return 마지막 날짜
     * @throws ParseException 문자열파싱시 발생
     */
    public static synchronized String getLastDayOfMonth(String str, int month) throws ParseException { // NOPMD
        Calendar calendar = getCalendar(str);
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 주어진 날짜가 속한 주가 월의 몇째주인지를 구한다
     *
     * @param str yyyymmdd 형식의 날짜문자열
     * @return 몇째주
     */
    public static int getWeek(String str) {
        return getCalendar(str).get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 주어진 날짜의 요일을 리턴한다
     *
     * @param str yyyymmdd 형식의 날짜문자열
     * @return 요일
     */
    public static int getDayOfWeek(String str) {
        return getCalendar(str).get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 주어진 날짜가 속한 주가 월의 몇째주인지를 구한다
     *
     * @param date Date 객체
     * @return 몇째주
     */
    public static int getWeek(Date date) {
        return getCalendar(date).get(Calendar.WEEK_OF_MONTH);
    }

    public static String addMonths(String date, int offset) {
        return addMonths(date, offset, "yyyyMMdd");
    }

    public static String addMonths(String date, int offset, String retFormat) {
        String formattedDate = isFormatMatched(date, "yyyyMMdd") ? date : date("yyyyMMdd");
        Calendar calendar = getCalendar(formattedDate);
        calendar.add(Calendar.MONTH, offset);

        return date(retFormat, calendar.getTime());
    }

    /**
     * 두 날짜사이의 일(day)수를 구한다.
     */
    public static long daysBetween(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime() + MILLISECONDS_IN_HOUR) / (MILLISECONDS_IN_HOUR * 24);
    }

    /**
     * 두 날짜사이의 일(day)수를 구한다.
     */
    public static synchronized long daysBetween(String date1, String date2, String format) throws ParseException { // NOPMD
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.getDefault());
        Date d1 = simpleDateFormat.parse(date1);
        Date d2 = simpleDateFormat.parse(date2);
        return (d2.getTime() - d1.getTime() + MILLISECONDS_IN_HOUR) / (MILLISECONDS_IN_HOUR * 24);
    }

    /**
     * 어제일자를 YYYYMMDD 형식으로 리턴한다
     */
    public static String yesterday() {
        String yestderday = null;

        try {
            String today = DateUtil.date(Formats.yyyyMMdd);
            yestderday = DateUtil.addDays(today, -1);
        } catch (Exception e) {
            return "";
        }

        return yestderday;
    }
}
