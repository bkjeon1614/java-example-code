package com.example.bkjeon.util.message;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;

public class MessageUtil {

    private static final MessageManager MANAGER = MessageManager.getInstance();

    private MessageUtil() {
        throw new UnsupportedOperationException();
    }

    public static String getTraceLog(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    /**
     * key 에 해당되는 message를 리턴합니다.
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        return getMessage(key, null);
    }

    /**
     * key 에 해당되는 message를 args와 bind 하여 리턴합니다.
     * @param key
     * @return
     */
    public static String getMessage(String key, Object[] args) {
        return MANAGER.getMessage(key, args);
    }

    /**
     * Locale 에 따른 message를 리턴합니다.
     * @param locale
     * @param key
     * @return
     */
    public static String getMessage(Locale locale, String key) {
        return getMessage(locale, key, null);
    }

    /**
     * Locale 에 따른 message를 args와 bind 하여 리턴합니다.
     * @param locale
     * @param key
     * @return
     */
    public static String getMessage(Locale locale, String key, Object[] args) {
        return MANAGER.getMessage(locale, key, args);
    }

}
