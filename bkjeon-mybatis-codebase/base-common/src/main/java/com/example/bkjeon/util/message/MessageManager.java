package com.example.bkjeon.util.message;

import com.example.bkjeon.util.common.Env;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

@Slf4j
final class MessageManager {

    private String defaultMessage;
    private Locale defaultLocale = Env.DEFAULT_LOCALE;
    private String defaultEncoding = Env.DEFAULT_ENCODING;
    private final Map<Locale, Map<String, String>> messages;
    private static final List<Locale> LOCALES;
    private static final MessageManager INSTANCE = new MessageManager();
    public static final String MESSAGE_FILE_EXTENSION = ".properties";

    static {
        LOCALES = new ArrayList<>();
        Collections.addAll(LOCALES, Locale.getAvailableLocales());
        Collections.sort(LOCALES, new Comparator<Locale>() {
            public int compare(Locale locale1, Locale locale2) {
                return locale1.toString().compareTo(locale2.toString());
            }
        });
    }

    /**
     * 생성자 (호출되지 않음)
     */
    private MessageManager() {
        messages = new HashMap<Locale, Map<String, String>>();
    }

    /**
     * {@link MessageManager} 인스턴스를 리턴한다
     *
     * @return {@link MessageManager} 인스턴스
     */
    static MessageManager getInstance() {
        return INSTANCE;
    }

    void clear() {
        messages.clear();
    }

    void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    String getDefaultMessage() {
        return defaultMessage;
    }

    void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    Locale getDefaultLocale() {
        return defaultLocale;
    }

    void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    String getDefaultEncoding() {
        return defaultEncoding;
    }

    /**
     * 메시지 파일을 이용하여 메시지를 생성한다
     *
     * @param applicationContext {@link ApplicationContext}
     * @param filenames 메시지 파일
     */
    synchronized void build(ApplicationContext applicationContext, String... filenames) { // NOPMD
        for (String filename : filenames) {
            for (Locale locale : LOCALES) {
                loadProperties(applicationContext, filename, locale);
            }
        }
    }

    /**
     * 메시지를 리턴한다
     *
     * @param key 메시지 키
     * @param args 메시지 파라미터
     * @return 메시지
     */
    String getMessage(String key, Object[] args) {
        return getMessage(defaultLocale, key, args);
    }

    /**
     * 로케일에 해당하는 메시지를 리턴한다
     *
     * @param locale 로케일
     * @param key 메시지 키
     * @return 메시지
     */
    private String getMessageOfKey(Locale locale, String key) {
        Map<String, String> messageProperties = messages.get(locale);

        if (messageProperties != null) {
            return messageProperties.get(key);
        }

        return null;
    }

    String getMessage(Locale locale, String key, Object[] args) {
        Locale lo = (locale == null) ? defaultLocale : locale;
        String message = getMessageOfKey(lo, key);

        if (message == null && StringUtils.isNotEmpty(lo.getCountry())) {
            Locale newLocale = LocaleUtils.toLocale(lo.getLanguage());
            message = getMessageOfKey(newLocale, key);
        }

        if (message != null) {
            return MessageFormat.format(message, args);
        }

        return defaultMessage;
    }

    private ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = this.getClass().getClassLoader();
        }

        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
        }

        return classLoader;
    }

    private List<InputStream> getInputStreams(ApplicationContext applicationContext, String filename) {
        List<InputStream> inputStreams = new ArrayList<InputStream>();

        try {
            if (applicationContext == null) {
                Enumeration<URL> resources = getClassLoader().getResources(filename);

                while (resources.hasMoreElements()) {
                    URL url = resources.nextElement();
                    inputStreams.add(url.openStream());
                }
            } else {
                Resource[] resources = applicationContext.getResources("classpath:" + filename);

                for (Resource resource : resources) {
                    if (resource.exists() && resource.isReadable()) {
                        inputStreams.add(resource.getInputStream());
                    }
                }
            }
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }

        return inputStreams;
    }

    private void loadProperties(ApplicationContext applicationContext, String filename, Locale locale) {
        String localeFilename = filename + "_" + locale + MESSAGE_FILE_EXTENSION;

        if (!loadProperty(applicationContext, localeFilename, locale)) { // NOPMD
            if (locale.equals(defaultLocale)) { // NOPMD
                String nonLocaleFilename = filename + MESSAGE_FILE_EXTENSION;

                if (!loadProperty(applicationContext, nonLocaleFilename, locale)) {
                    log.error("{} 또는 {} 파일을 찾을 수 없습니다", localeFilename, nonLocaleFilename);
                }
            }
        }
    }

    private boolean loadProperty(ApplicationContext applicationContext, String filename, Locale locale) {
        List<InputStream> inputStreams = getInputStreams(applicationContext, filename);

        if (inputStreams.isEmpty()) {
            return false;
        }

        Map<String, String> localeMessage = messages.get(locale);

        if (localeMessage == null) {
            localeMessage = new HashMap<String, String>();
            messages.put(locale, localeMessage);
        }

        try {
            for (InputStream inputStream : inputStreams) {
                Reader reader = new InputStreamReader(inputStream, defaultEncoding);
                Properties properties = new Properties();
                properties.load(reader);
                reader.close();

                Enumeration<?> names = properties.propertyNames();

                while (names.hasMoreElements()) {
                    String key = (String) names.nextElement();
                    String value = properties.getProperty(key);

                    if (value == null) {
                        continue;
                    }

                    localeMessage.put(key, value);
                }

                log.info("{}의 내용을 메시지에 추가하였습니다", filename);
                IOUtils.closeQuietly(inputStream);
            }

            return true;
        } catch (IOException e) {
            log.debug(e.getMessage(), e);
        }

        return false;
    }
}
